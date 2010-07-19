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

package org.ardverk.daap.chunks.impl;

import org.ardverk.daap.chunks.UIntChunk;

/**
 * The size of the Song in bytes.
 * 
 * @author Roger Kapsi
 */
public class SongSize extends UIntChunk {

    /**
     * Creates a new SongSize with 0-length You can change this value with
     * {@see #setValue(int)}.
     */
    public SongSize() {
        this(0);
    }

    /**
     * Creates a new SongSize with the assigned size. You can change this value
     * with {@see #setValue(int)}.
     * 
     * @param <tt>size</tt> the size of this song in bytes.
     */
    public SongSize(long size) {
        super("assz", "daap.songsize", size);
    }
}
