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

package de.mkristian.ixtlan.users.client.presenters;


import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import de.mkristian.gwt.rails.RemoteNotifier;
import de.mkristian.gwt.rails.presenters.CRUDPresenterImpl;
import de.mkristian.ixtlan.users.client.UsersErrorHandler;
import de.mkristian.ixtlan.users.client.caches.ApplicationCache;
import de.mkristian.ixtlan.users.client.caches.ApplicationRemote;
import de.mkristian.ixtlan.users.client.models.Application;
import de.mkristian.ixtlan.users.client.models.Group;
import de.mkristian.ixtlan.users.client.restservices.ApplicationsRestService;
import de.mkristian.ixtlan.users.client.views.ApplicationListView;
import de.mkristian.ixtlan.users.client.views.ApplicationView;

@Singleton
public class ApplicationPresenter extends CRUDPresenterImpl<Application> {

    private Group current;
    private final ApplicationsRestService service;
    private final RemoteNotifier notifier;
    
    @Inject
    public ApplicationPresenter( UsersErrorHandler errors,
            ApplicationView view,
            ApplicationListView listView,
            ApplicationCache cache,
            ApplicationRemote remote,
            ApplicationsRestService service,
            RemoteNotifier notifier ){
        super( errors, view, listView, cache, remote );
        view.setPresenter( this );
        this.service = service;
        this.notifier = notifier;
     }
    
    protected ApplicationView getView(){
        return (ApplicationView) super.getView();
    }

    @Override
    public boolean isDirty() {
        return super.isDirty() ||
                    (current != null && getView().isGroupDirty());
    }
    
    public void save( final Group group ) {
        this.notifier.saving();
        this.service.updateGroup(group.minimalClone(), new MethodCallback<Group>() {
            
            @Override
            public void onSuccess(Method method, Group response) {
                List<Group> groups = group.getApplication().getGroups();
                int i = groups.indexOf( response ); // identity is over id field
                groups.set( i, response );
                getView().edit(response);
                getRemote().fireUpdate( method, group.getApplication() );
            }
            
            @Override
            public void onFailure(Method method, Throwable exception) {
                getRemote().fireError( method, exception );
            }
        });
    }

    public void show( Group group ) {
        assert group != null;
        if( current != null && current.getId() == group.getId() ){
            current = null;
        }
        getView().show( group );
    }

    public void edit( Group group ) {
        assert group != null;
        if (current != null){
            getView().show( current );
        }
        getView().resetNewRow( group.getApplication() );
        getView().edit( group );
        current = group;
    }

    public void delete( final Group group ) {
        this.notifier.deleting();
        this.service.destroyGroup( group.minimalClone(), new MethodCallback<Void>() {
            
            @Override
            public void onSuccess( Method method, Void response ) {
                group.getApplication().getGroups().remove( group );
                getView().reset( group.getApplication() );
                getRemote().fireUpdate( method, group.getApplication() );
            }
            
            @Override
            public void onFailure(Method method, Throwable exception) {
                getRemote().fireError( method, exception );
            }
        });
    }
    
    public void reset( Group model ) {
        assert model != null;
        if( current != null && current.getId() == model.getId() ){
            getView().reset( model );
            current = model;
        }
        else {
            getView().show( model );
        }
    }

    public void showCurrent() {
        doShowCurrent();
        //getView().resetNewRow(miss);
   }

    private void doShowCurrent() {
        if( current != null ){
            getView().show( current );
            current = null;
        }
    }

    public void newGroup( Group model ) {
        doShowCurrent();
        getView().newGroup( model );
    }

    public void create( final Group group ) {
        this.notifier.saving();
        this.service.createGroup( group.minimalClone(), new MethodCallback<Group>() {
            
            @Override
            public void onSuccess( Method method, Group response ) {
                group.getApplication().getGroups().add( group );
                getView().reset( group.getApplication() );
                getRemote().fireUpdate( method, group.getApplication() );
            }
            
            @Override
            public void onFailure(Method method, Throwable exception) {
                getRemote().fireError( method, exception );
            }
        });
    }
}
