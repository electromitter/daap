
package de.kapsi.net.daap.chunks.impl;

import de.kapsi.net.daap.chunks.IntChunk;

/**
 * Unknown purpose. Used by {@link de.kapsi.net.daap.ServerInfoResponseImpl ServerInfoResponseImpl}
 */
public class TimeoutInterval extends IntChunk {
    
    public TimeoutInterval() {
        this(0);
    }
    
    public TimeoutInterval(int interval) {
        super("mstm", "dmap.timeoutinterval", interval);
    }
}