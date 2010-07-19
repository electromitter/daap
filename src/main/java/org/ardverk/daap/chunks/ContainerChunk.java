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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * A container contains a series of other chunks.
 * 
 * @author Roger Kapsi
 */
public class ContainerChunk extends AbstractChunk implements Iterable<Chunk> {

    protected Collection<Chunk> collection;

    /**
     * Creates a new ContainerChunk with an <tt>ArrayList</tt> as the underlying
     * collection.
     */
    protected ContainerChunk(String type, String name) {
        this(type, name, new ArrayList<Chunk>());
    }

    /**
     * Note: you should use always a List as the underlying collection.
     */
    protected ContainerChunk(String type, String name,
            Collection<Chunk> collection) {
        super(type, name);
        this.collection = collection;
    }

    /**
     * Adds <tt>chunk</tt> to this container
     */
    public void add(Chunk chunk) {
        if (chunk == null) {
            throw new IllegalArgumentException();
        }
        collection.add(chunk);
    }

    public Iterator<Chunk> iterator() {
        return Collections.unmodifiableCollection(collection).iterator();
    }

    /**
     * Returns the number of childs
     */
    public int size() {
        return collection.size();
    }

    /**
     * Returns {@see Chunk.CONTAINER_TYPE}
     */
    public int getType() {
        return Chunk.CONTAINER_TYPE;
    }

    public String toString(int indent) {
        StringBuilder buffer = new StringBuilder(indent(indent));
        buffer.append(name).append("(").append(getContentCodeString()).append(
                "; container)\n");

        Iterator<Chunk> it = iterator();
        for (int i = 0; it.hasNext(); i++) {
            Chunk chunk = it.next();
            buffer./* append(i).append(": "). */append(indent(indent + 4)
                    + chunk.toString());
            if (it.hasNext()) {
                buffer.append("\n");
            }
        }
        return buffer.toString();
    }
}
