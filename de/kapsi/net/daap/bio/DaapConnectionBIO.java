/* 
 * Digital Audio Access Protocol (DAAP)
 * Copyright (C) 2004 Roger Kapsi, info at kapsi dot de
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package de.kapsi.net.daap.bio;

import java.io.*;
import java.net.*;
import java.util.*;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpParser;
import org.apache.commons.httpclient.HttpStatus;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.kapsi.net.daap.DaapUtil;
import de.kapsi.net.daap.DaapServer;
import de.kapsi.net.daap.DaapRequest;
import de.kapsi.net.daap.DaapResponse;
import de.kapsi.net.daap.DaapConnection;
import de.kapsi.net.daap.DaapSession;
import de.kapsi.net.daap.DaapResponseFactory;
import de.kapsi.net.daap.DaapRequestProcessor;
import de.kapsi.net.daap.DaapResponseWriter;
import de.kapsi.net.daap.DaapStreamException;

/**
 * This class is a cover for an incoming connection and is based 
 * on the classical BIO (Blocking I/O) pattern. An connection
 * can either be a general DAAP connection or an Audio request.
 *
 * @author  Roger Kapsi
 */
public class DaapConnectionBIO implements DaapConnection, Runnable {
    
    private static final Log LOG = LogFactory.getLog(DaapConnectionBIO.class);
    
    private static final DaapResponseFactory FACTORY = new DaapResponseFactoryBIO();
    private static final DaapRequestProcessor PROCESSOR = new DaapRequestProcessor(FACTORY);
    
    private DaapServerBIO server;
    
    private Socket socket;
    
    private InputStream in;
    private OutputStream out;
    
    private DaapSession session;
    private DaapResponseWriter writer;
    
    private int type = DaapConnection.UNDEF;
    private int protocolVersion = DaapUtil.UNDEF_VALUE;
    
    public DaapConnectionBIO(DaapServerBIO server, Socket socket) throws IOException {
        
        this.server = server;
        this.socket = socket;
        
        writer = new DaapResponseWriter();
       
        in = new BufferedInputStream(socket.getInputStream());
        out = socket.getOutputStream();
    }
    
    public DaapServer getServer() {
        return server;
    }
    
    
    public boolean isUndef() {
        return (type == DaapConnection.UNDEF);
    }
    
    /**
     * Returns true if this connection is an Audio stream
     */
    public boolean isAudioStream() {
        return (type == DaapConnection.AUDIO);
    }
    
    /**
     *
     */
    public boolean isNormal() {
        return (type == DaapConnection.NORMAL);
    }
    
    /**
     *
     */
    public int getProtocolVersion() {
        return protocolVersion;
    }
    
    private boolean read() throws IOException {
        
        DaapRequest request = readRequest();
            
        if (!isAudioStream()) {

            if (isUndef()) {
                
                if (request.isSongRequest()) {
                    type = DaapConnection.AUDIO;
                    
                    // AudioStreams have a session-id and we must check the id
                    Integer sid = new Integer(request.getSessionId());
                    if (server.isSessionIdValid(sid) == false) {
                        throw new IOException("Unknown Session-ID: " + sid);
                    }
                    
                    // Get the associated "normal" connection...
                    DaapConnection connection = server.getConnection(sid);
                    if (connection == null) {
                        throw new IOException("No connection associated with this Session-ID: " + sid);
                    }
                    
                    // ...and use its protocolVersion for this Audio Stream
                    // because Audio Streams do not provide the version in 
                    // the request header (we could use the User-Agent header
                    // but that breaks compatibility to non iTunes hosts and
                    // they would have to fake their request header.
                    protocolVersion = connection.getProtocolVersion();
                        
                } else if (request.isServerInfoRequest()) {
                    type = DaapConnection.NORMAL;
                    protocolVersion = DaapUtil.getProtocolVersion(request);
                    
                } else {
                    
                    // disconnect as the first request must be
                    // either a song or server-info request!
                    throw new IOException("Illegal first request: " + request);
                }
                
                if (protocolVersion < DaapUtil.VERSION_2) {
                    throw new IOException("Unsupported Protocol Version: " + protocolVersion);
                }
                
                // add connection to the connection pool
                if ( ! server.addConnection(this) ) {
                    throw new IOException("Server refused this connection");
                }
            }

            DaapResponse response = PROCESSOR.process(request);
            if (response != null) {
                writer.add(response);
            }
            
            return true;
        }
        
        throw new IOException("Cannot read requests from audio stream");
    }
    
    private boolean write() throws IOException {

        boolean ret = true;

            if (writer.write()) {

                if (isAudioStream()) {
                    ret = false;
                }
            }

        return ret;
    }
    
    public void run() {
        
        try {
            
            do {
                read();
            } while(write());
           
        } catch (DaapStreamException err) {
            
            // LOG.info(err);
            // This exception can be ignored as it's thrown
            // whenever the user presses the pause, fast-forward
            // and so on button
            
        } catch (SocketException err) {
            
            // LOG.info(err);
            // This exception can be ignored as it's thrown
            // whenever the user disconnects...
            
        } catch (IOException err) {
            LOG.error(err);
            
        } finally {
            close();
        }
    }
    
    public synchronized void update() throws IOException {
        
        if (isNormal()) {
            DaapSession session = getSession(false);

            // Do not trigger new updates if an update for this connection
            // is already running, it will autumatically update to the
            // lates revision of the library!

            if (session != null && !session.hasAttribute("UPDATE_LOCK")) {

                Integer sessionId = session.getSessionId();
                Integer delta = (Integer)session.getAttribute("DELTA");
                Integer revisionNumber 
                    = (Integer)session.getAttribute("REVISION-NUMBER");

                if (delta != null && revisionNumber != null) {

                    DaapRequest request =
                        new DaapRequest(this, sessionId.intValue(),
                            revisionNumber.intValue(), delta.intValue());

                    DaapResponse response = PROCESSOR.process(request);
                    response.write();
                }
            }
        }
    }
    
    public void close() {
        
        if (session != null)
            session.invalidate();
        
        if (writer != null)
            writer.clear();
        
        try {
            if (in != null)
                in.close();
        } catch (IOException err) {
            LOG.error("Error while closing connection", err);
        }
        
        try {
            if (out != null)
                out.close();
        } catch (IOException err) {
            LOG.error("Error while closing connection", err);
        }
        
        try {
            if (socket != null)
                socket.close();
        } catch (IOException err) {
            LOG.error("Error while closing connection", err);
        }
        
        
        server.removeConnection(this);
    }
    
    public DaapSession getSession(boolean create) {
        
        if (session == null && create) {
            Integer sessionId = server.createSessionId();
            session = new DaapSession(sessionId);
        }
        
        return session;
    }
    
    public InputStream getInputStream() {
        return in;
    }
    
    public OutputStream getOutputStream() {
        return out;
    }
    
    private DaapRequest readRequest() throws IOException {
        
        String line = null;
        
        do {
            line = HttpParser.readLine(in);
        } while(line != null && line.length() == 0);
        
        if(line == null) {
            throw new IOException("Request is null: " + this);
        }
        
        DaapRequest request = new DaapRequest(this, line);
        Header[] headers = HttpParser.parseHeaders(in);
        request.addHeaders(headers);
        
        return request;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer("DaapConnection [");
        
        buffer.append("Host: ").append(socket.getInetAddress()).append(":")
        .append(socket.getPort());
        
        buffer.append(", audioStream: ").append(isAudioStream());
        buffer.append(", hasSession: ").append(getSession(false)!=null);
        
        return buffer.append("]").toString();
    }
}
