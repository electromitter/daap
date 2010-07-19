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
 * The length of the Song in milliseconds.
 * 
 * @author Roger Kapsi
 */
public class SongTime extends UIntChunk {

    /**
     * Creates a new SongTime with 0 length. You can change this value with
     * {@see #setValue(int)}.
     */
    public SongTime() {
        this(0);
    }

    /**
     * Creates a new SongTime with the assigned time. You can change this value
     * with {@see #setValue(int)}.
     * 
     * @param <tt>time</tt> the length of this song in milliseconds.
     */
    public SongTime(long time) {
        super("astm", "daap.songtime", time);
    }
}
