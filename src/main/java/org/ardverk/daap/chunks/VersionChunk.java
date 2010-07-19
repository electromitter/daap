/*
 * Digital Audio Access Protocol (DAAP) Library
 * Copyright (C) 2004-2010 Roger Kapsi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ardverk.daap.chunks;

import org.ardverk.daap.DaapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements a Version chunk. A Version chunk is a 32bit int where
 * the two upper 2 bytes are the major version, the 3rd byte minor and the last
 * byte is the micro version. <code>0x00020000 = 2.0.0</code>
 * 
 * @author Roger Kapsi
 */
public abstract class VersionChunk extends AbstractChunk {

    private static final Logger LOG = LoggerFactory
            .getLogger(VersionChunk.class);

    public static final long MIN_VALUE = 0l;
    public static final long MAX_VALUE = 0xFFFFFFFFl;

    protected int version = 0;

    public VersionChunk(int type, String name, long value) {
        super(type, name);
        setValue(value);
    }

    public VersionChunk(String type, String name, long value) {
        super(type, name);
        setValue(value);
    }

    protected VersionChunk(String type, String name, int majorVersion,
            int minorVersion, int microVersion) {
        this(type, name, DaapUtil.toVersion(majorVersion, minorVersion,
                microVersion));
    }

    public void setValue(long version) {
        this.version = (int) checkVersionRange(version);
    }

    public long getValue() {
        return version & MAX_VALUE;
    }

    public void setMajorVersion(int majorVersion) {
        long version = getValue() & 0x0000FFFFl;
        version |= (majorVersion & 0xFFFF) << 16;
        setValue(version);
    }

    public void setMinorVersion(int minorVersion) {
        long version = getValue() & 0xFFFF00FFl;
        version |= (minorVersion & 0xFF) << 8;
        setValue(version);
    }

    public void setMicroVersion(int microVersion) {
        long version = getValue() & 0xFFFFFF00l;
        version |= (microVersion & 0xFF);
        setValue(version);
    }

    public int getMajorVersion() {
        return (int) ((getValue() >> 16) & 0xFFFF);
    }

    public int getMinorVersion() {
        return (int) ((getValue() >> 8) & 0xFF);
    }

    public int getMicroVersion() {
        return (int) (getValue() & 0xFF);
    }

    /**
     * Checks if #MIN_VALUE <= value <= #MAX_VALUE and if not an
     * IllegalArgumentException is thrown.
     */
    public static long checkVersionRange(long value)
            throws IllegalArgumentException {
        if (value < MIN_VALUE || value > MAX_VALUE) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Value is outside of Version range: " + value);
            }
        }
        return value;
    }

    /**
     * Returns {@see #VERSION_TYPE}
     */
    public int getType() {
        return Chunk.VERSION_TYPE;
    }

    public String toString(int indent) {
        return indent(indent) + name + "(" + getContentCodeString()
                + "; version)=" + getMajorVersion() + "." + getMinorVersion()
                + "." + getMicroVersion();
    }

    public static final int getMajorVersion(int version) {
        return (version & 0xFFFF0000) >> 16;
    }

    public static final int getMinorVersion(int version) {
        return (version & 0xFF00) >> 8;
    }

    public static final int getMicroVersion(int version) {
        return version & 0xFF;
    }
}
