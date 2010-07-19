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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An unsigned short
 */
public abstract class UShortChunk extends AbstractChunk implements ShortChunk {

    private static final Logger LOG = LoggerFactory
            .getLogger(UShortChunk.class);

    public static final int MIN_VALUE = 0;
    public static final int MAX_VALUE = 0xFFFF;

    protected int value = 0;

    public UShortChunk(int type, String name, int value) {
        super(type, name);
        setValue(value);
    }

    public UShortChunk(String type, String name, int value) {
        super(type, name);
        setValue(value);
    }

    public void setValue(int value) {
        this.value = checkUShortRange(value);
    }

    public int getValue() {
        return value;
    }

    /**
     * Checks if #MIN_VALUE <= value <= #MAX_VALUE and if not an
     * IllegalArgumentException is thrown.
     */
    public static int checkUShortRange(int value)
            throws IllegalArgumentException {
        if (value < MIN_VALUE || value > MAX_VALUE) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Value is outside of unsigned short range: " + value);
            }
        }
        return value;
    }

    /**
     * Returns {@see #U_SHORT_TYPE}
     */
    public int getType() {
        return Chunk.U_SHORT_TYPE;
    }

    public String toString(int indent) {
        return indent(indent) + name + "(" + getContentCodeString()
                + "; ushort)=" + getValue();
    }
}
