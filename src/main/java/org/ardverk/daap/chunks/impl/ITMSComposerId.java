/*
* Digital Audio Access Protocol (DAAP)
* Copyright (C) 2004-2010 Roger Kapsi
*
* This program is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation; either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/

package org.ardverk.daap.chunks.impl;

import org.ardverk.daap.chunks.UIntChunk;

/**
* Unknown purpose.
*
* @author  Roger Kapsi
* @since iTunes 4.5
*/
public class ITMSComposerId extends UIntChunk {

/** Creates a new instance of ITMSComposerId */
public ITMSComposerId() {
this(0);
}

public ITMSComposerId(long composerId) {
super("aeCI", "com.apple.itunes.itms-composerid", composerId);
}
}