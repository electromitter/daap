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
 * The sample rate of the Song in kHz.
 * 
 * @author Roger Kapsi
 */
public class SongSampleRate extends UIntChunk {

    public static final int KHZ_44100 = 44100;

    /**
     * Creates a new SongSampleRate with 0 kHz You can change this value with
     * {@see #setValue(int)}.
     */
    public SongSampleRate() {
        this(KHZ_44100);
    }

    /**
     * Creates a new SongSampleRate with the assigned sample rate. You can
     * change this value with {@see #setValue(int)}.
     * 
     * @param <tt>rate</tt> the rate of this song in kHz.
     */
    public SongSampleRate(long rate) {
        super("assr", "daap.songsamplerate", rate);
    }
}
