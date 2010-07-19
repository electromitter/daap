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

import java.io.UnsupportedEncodingException;

import org.ardverk.daap.DaapUtil;

/**
 * This class implements a String chunk. DAAP Strings are encoded in UTF-8.
 * 
 * @author Roger Kapsi
 */
public abstract class StringChunk extends AbstractChunk {

    protected String value;

    public StringChunk(int type, String name, String value) {
        super(type, name);
        setValue(value);
    }

    public StringChunk(String type, String name, String value) {
        super(type, name);
        setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public byte[] getBytes() {
        String value = this.value;

        if (value == null || value.length() == 0) {
            return new byte[0];
        } else {
            try {
                return value.getBytes(DaapUtil.UTF_8);
            } catch (UnsupportedEncodingException err) {
                // Should never happen
                throw new RuntimeException(err);
            }
        }
    }

    /**
     * Returns {@see #STRING_TYPE}
     */
    public int getType() {
        return Chunk.STRING_TYPE;
    }

    public String toString(int indent) {
        return indent(indent) + name + "(" + getContentCodeString()
                + "; string)=" + getValue();
    }
}
