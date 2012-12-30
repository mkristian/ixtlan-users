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


import javax.inject.Inject;
import javax.inject.Singleton;

import de.mkristian.gwt.rails.presenters.CRUDPresenterImpl;
import de.mkristian.ixtlan.users.client.UsersErrorHandler;
import de.mkristian.ixtlan.users.client.caches.ApplicationCache;
import de.mkristian.ixtlan.users.client.caches.ApplicationRemote;
import de.mkristian.ixtlan.users.client.models.Application;
import de.mkristian.ixtlan.users.client.models.Group;
import de.mkristian.ixtlan.users.client.views.ApplicationListView;
import de.mkristian.ixtlan.users.client.views.ApplicationView;

@Singleton
public class ApplicationPresenter extends CRUDPresenterImpl<Application> {

    private Group current;
    
    @Inject
    public ApplicationPresenter( UsersErrorHandler errors,
            ApplicationView view,
            ApplicationListView listView,
            ApplicationCache cache,
            ApplicationRemote remote ){
        super( errors, view, listView, cache, remote );
        view.setPresenter( this );
     }

    protected ApplicationView getView(){
        return (ApplicationView) super.getView();
    }
    
    public void save( Group group ) {
        errors.show("save clicked" + group.getName() + group.hasRegions());
    }

    public void show( Group group ) {
        assert group != null;
        if( current != null && current.getId() == group.getId() ){
            current = null;
        }
        getView().show( group );
    }

    public void edit( Group model ) {
        if (current != null){
            getView().show( current );
        }
        getView().edit( model );
        current = model;
    }

    public void delete(Group model) {
        errors.show("delete clicked");
    }
    
    public void reset( Group model ) {
        assert model != null;
        if( current != null && current.getId() == model.getId() ){
            getView().reset(model);
            current = model;
        }
        else {
            getView().show( model );
        }
    }

    public void showCurrent() {
        if( current != null ){
            getView().show( current );
            current = null;
        }
   }
}
