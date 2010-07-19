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

import org.ardverk.daap.chunks.DateChunk;

/**
 * The date when the Song was added. Date is the difference between the current
 * time and midnight, January 1, 1970 UTC in <u>seconds</u>!
 * 
 * <code>int date = (int)(System.currentTimeMillis()/1000);</code>
 * 
 * @author Roger Kapsi
 */
public class SongDateAdded extends DateChunk {

    public SongDateAdded() {
        this(0l);
    }

    public SongDateAdded(long seconds) {
        super("asda", "daap.songdateadded", seconds);
    }
}
