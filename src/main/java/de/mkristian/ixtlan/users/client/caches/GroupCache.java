/*
 * ixtlan_gettext - helper to use fast_gettext with datamapper/ixtlan
 * Copyright (C) 2012 Christian Meier
 *
 * This file is part of ixtlan_gettext.
 *
 * ixtlan_gettext is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * ixtlan_gettext is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with ixtlan_gettext.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.mkristian.ixtlan.users.client.caches;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.fusesource.restygwt.client.JsonEncoderDecoder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;

import de.mkristian.gwt.rails.caches.AbstractPreemptiveCache;
import de.mkristian.gwt.rails.caches.BrowserOrMemoryStore;
import de.mkristian.gwt.rails.caches.Store;
import de.mkristian.gwt.rails.events.ModelEventHandler;
import de.mkristian.ixtlan.users.client.events.GroupEvent;
import de.mkristian.ixtlan.users.client.models.Group;

@Singleton
public class GroupCache extends AbstractPreemptiveCache<Group>{

    static interface GroupCoder extends JsonEncoderDecoder<Group>{
    }
    static GroupCoder coder = GWT.create( GroupCoder.class );

    private static Store<Group> store(){
        return new BrowserOrMemoryStore<Group>( coder, "groups" );
    }
    
    @Inject
    GroupCache( EventBus eventBus, GroupRemoteModel remote ) {
        super( eventBus, store(), remote );
    }

    @Override
    protected Type<ModelEventHandler<Group>> eventType() {
        return GroupEvent.TYPE;
    }
}
