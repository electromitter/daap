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

import org.ardverk.daap.chunks.UShortChunk;

/**
 * The year when the Song was released.
 * 
 * @author Roger Kapsi
 */
public class SongYear extends UShortChunk {

    /**
     * Creates a new SongYear and initializes it with 0 You can change this
     * value with {@see #setValue(int)}.
     */
    public SongYear() {
        this(0);
    }

    /**
     * Creates a new SongYear and initializes it with the assigned year. You can
     * change this value with {@see #setValue(int)}.
     * 
     * @param <code>year</code> the year
     */
    public SongYear(int year) {
        super("asyr", "daap.songyear", year);
    }
}
