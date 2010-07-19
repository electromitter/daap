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

import org.ardverk.daap.chunks.SByteChunk;

/**
 * The relative loudness of the Song to the main volume adjuster. You can
 * increase or decrease the loundness by +/- 100%.
 * 
 * @author Roger Kapsi
 */
public class SongRelativeVolume extends SByteChunk {

    /** Decrease the volume by 100% */
    public static final int MIN_VALUE = -100;

    /** Do not increase or decrease the sound volume */
    public static final int NONE = 0;

    /** Increase the volume by 100% */
    public static final int MAX_VALUE = 100;

    public SongRelativeVolume() {
        this(0);
    }

    /**
     * @param <tt>volume</tt> the relative volume
     */
    public SongRelativeVolume(int volume) {
        super("asrv", "daap.songrelativevolume", volume);
    }
}
