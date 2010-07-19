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

/**
 * A signed int
 */
public abstract class SIntChunk extends AbstractChunk implements IntChunk {

    public static final int MIN_VALUE = Integer.MIN_VALUE;
    public static final int MAX_VALUE = Integer.MAX_VALUE;

    protected int value = 0;

    public SIntChunk(int type, String name, int value) {
        super(type, name);
        setValue(value);
    }

    public SIntChunk(String type, String name, int value) {
        super(type, name);
        setValue(value);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Returns {@see #INT_TYPE}
     */
    public int getType() {
        return Chunk.INT_TYPE;
    }

    public String toString(int indent) {
        return indent(indent) + name + "(" + getContentCodeString() + "; int)="
                + getValue();
    }
}
