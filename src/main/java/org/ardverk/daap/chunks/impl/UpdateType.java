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

import org.ardverk.daap.chunks.UByteChunk;

/**
 * This Chunk is used to indicate if a response is either an update or a full
 * response. The first request is always followed by a full response (e.g. the
 * list of all Songs in the Library) and thenceforward it's awlays an update (a
 * diff between client's and server's Library).
 * 
 * @author Roger Kapsi
 */
public class UpdateType extends UByteChunk {

    /**
     * Creates a new UpdateType
     */
    public UpdateType() {
        this(0);
    }

    public UpdateType(int updatetype) {
        super("muty", "dmap.updatetype", updatetype);
    }
}
