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

package org.ardverk.daap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ardverk.daap.chunks.impl.HasChildContainers;
import org.ardverk.daap.chunks.impl.ParentContainerId;

/**
 * A Folder is a Playlist of Playlists
 * 
 * @since iTunes 5.0
 * @author Roger Kapsi
 */
public class Folder extends Playlist {

    /** */
    private final ParentContainerId parentContainerId = new ParentContainerId();

    // @since iTunes 5.0
    private final HasChildContainers hasChildContainers = new HasChildContainers(true);

    /** */
    private List<Playlist> playlists = null;

    protected Folder(Playlist playlist, Transaction txn) {
        super(playlist, txn);
        parentContainerId.setValue(getItemId());
        playlists = ((Folder) playlist).playlists;
        init();
    }

    public Folder(String name) {
        super(name);
        parentContainerId.setValue(getItemId());
        init();
    }

    private void init() {
        addChunk(hasChildContainers);
    }

    public void addSong(Transaction txn, Song song) {
        throw new UnsupportedOperationException(
                "Songs cannot be added to Folders");
    }

    public void removeSong(Transaction txn, Song song) {
        throw new UnsupportedOperationException(
                "Songs cannot be removed from Folders");
    }

    public boolean containsSong(Song song) {
        return false;
    }

    public int getSongCount() {
        return 0;
    }

    public List<Song> getSongs() {
        return Collections.emptyList();
    }

    public void addPlaylist(Transaction txn, final Playlist playlist) {
        if (playlist instanceof Folder) {
            throw new IllegalArgumentException("Recursion is not supported");
        }

        if (txn != null) {
            txn.addTxn(this, new Txn() {
                public void commit(Transaction txn) {
                    addPlaylistP(txn, playlist);
                }
            });
            txn.attach(playlist);
        } else {
            addPlaylistP(txn, playlist);
        }
    }

    private void addPlaylistP(Transaction txn, Playlist playlist) {
        if (playlists == null) {
            playlists = new ArrayList<Playlist>();
        }

        if (!containsPlaylist(playlist) && playlists.add(playlist)) {
            playlist.addChunk(parentContainerId);
        }
    }

    public void removePlaylist(Transaction txn, final Playlist playlist) {
        if (playlist instanceof Folder) {
            return;
        }

        if (txn != null) {
            txn.addTxn(this, new Txn() {
                public void commit(Transaction txn) {
                    removePlaylistP(txn, playlist);
                }
            });
            txn.attach(playlist);
        } else {
            removePlaylistP(txn, playlist);
        }
    }

    private void removePlaylistP(Transaction txn, Playlist playlist) {
        if (playlists == null) {
            return;
        }

        if (playlists.remove(playlist)) {
            playlist.removeChunk(parentContainerId);

            if (playlists.isEmpty()) {
                playlists = null;
            }
        }
    }

    public int getPlaylistCount() {
        return getPlaylists().size();
    }

    public List<Playlist> getPlaylists() {
        if (playlists != null) {
            return Collections.unmodifiableList(playlists);
        } else {
            return Collections.emptyList();
        }
    }

    public boolean containsPlaylist(Playlist playlist) {
        return getPlaylists().contains(playlist);
    }
}
