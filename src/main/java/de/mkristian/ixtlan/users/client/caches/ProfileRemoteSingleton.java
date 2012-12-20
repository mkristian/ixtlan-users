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

import org.fusesource.restygwt.client.Method;

import com.google.gwt.event.shared.EventBus;

import de.mkristian.gwt.rails.RemoteNotifier;
import de.mkristian.gwt.rails.caches.RemoteSingletonAdapter;
import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEvent.Action;
import de.mkristian.ixtlan.users.client.events.ProfileEvent;
import de.mkristian.ixtlan.users.client.models.Profile;
import de.mkristian.ixtlan.users.client.restservices.ProfileRestService;

@Singleton
public class ProfileRemoteSingleton
            extends RemoteSingletonAdapter<Profile> {

    private final ProfileRestService restService;
    
    @Inject
    protected ProfileRemoteSingleton(RemoteNotifier notifier, 
            EventBus eventBus, 
            ProfileRestService restService) {
        super( eventBus, notifier );
        this.restService = restService;
    }

    @Override
    protected ModelEvent<Profile> newEvent(Method method, Profile model, Action action) {
        return new ProfileEvent(method, model, action);
    }

    @Override
    protected ModelEvent<Profile> newEvent(Method method, Throwable e) {
        return new ProfileEvent(method, e);
    }

    @Override
    public void retrieve() {
        notifier.loading();
        restService.show( newRetrieveCallback() );
    }

    @Override
    public void update(Profile model) {
        notifier.saving();
        restService.update( model, newUpdateCallback() );
    }
}