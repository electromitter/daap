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

package org.ardverk.daap;

/**
* This interface enables you to create customized Threads
* from outside of DaapServer. The DaapServer will call
* this interface whenever a new Thread needs to be created.
* <p><b>Important:</b> Just create the Thread, setup some
* properties like setDaemon() or setPriority() but
* <b><u><i>do not</i></u></b> start the Thread!!!
*
* @author  Roger Kapsi
*/
public interface DaapThreadFactory {

public Thread createDaapThread(Runnable runner, String name);
}