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

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.fusesource.restygwt.client.Method;

import com.google.gwt.event.shared.EventBus;

import de.mkristian.gwt.rails.RemoteNotifier;
import de.mkristian.gwt.rails.caches.RemoteAdapter;
import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEvent.Action;
import de.mkristian.ixtlan.users.client.events.DomainEvent;
import de.mkristian.ixtlan.users.client.models.Domain;
import de.mkristian.ixtlan.users.client.restservices.DomainsRestService;

@Singleton
public class DomainRemote extends RemoteAdapter<Domain> {

    private final DomainsRestService restService;
    
    @Inject
    protected DomainRemote( RemoteNotifier notifier, 
            EventBus eventBus, 
            DomainsRestService restService ) {
        super( eventBus, notifier );
        this.restService = restService;
    }

    @Override
    public Domain newModel() {
        return new Domain();
    }

    @Override
    protected ModelEvent<Domain> newEvent(Method method, List<Domain> models, Action action) {
        return new DomainEvent(method, models, action);
    }

    @Override
    protected ModelEvent<Domain> newEvent(Method method, Domain model, Action action) {
        return new DomainEvent(method, model, action);
    }

    @Override
    protected ModelEvent<Domain> newEvent(Method method, Throwable e) {
        return new DomainEvent(method, e);
    }

    @Override
    public void retrieveAll() {
        notifier.loading();
        restService.index( newRetrieveAllCallback() );
    }

    @Override
    public void retrieve( int id ) {
        notifier.loading();
        restService.show( id, newRetrieveCallback() );
    }

    @Override
    public void create( Domain model ) {
        notifier.saving();
        restService.create( model, newCreateCallback() );
    }

    @Override
    public void update( Domain model ) {
        notifier.saving();
        restService.update( model, newUpdateCallback() );
    }
    
    @Override
    public void delete( Domain model ) {
        notifier.deleting();
        restService.destroy( model, newDeleteCallback( model ) );
    }
}