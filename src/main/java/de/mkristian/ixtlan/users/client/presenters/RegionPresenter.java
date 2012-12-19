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


import de.mkristian.gwt.rails.caches.Cache;
import de.mkristian.gwt.rails.caches.Remote;
import de.mkristian.gwt.rails.presenters.AbstractPresenter;
import de.mkristian.gwt.rails.presenters.CRUDPresenter;
import de.mkristian.gwt.rails.views.CRUDListView;
import de.mkristian.gwt.rails.views.CRUDView;
import de.mkristian.ixtlan.users.client.UsersErrorHandler;
import de.mkristian.ixtlan.users.client.caches.RegionCache;
import de.mkristian.ixtlan.users.client.caches.RegionRemote;
import de.mkristian.ixtlan.users.client.models.Region;
import de.mkristian.ixtlan.users.client.views.RegionListView;
import de.mkristian.ixtlan.users.client.views.RegionView;

@Singleton
public class RegionPresenter extends AbstractPresenter 
            implements CRUDPresenter<Region> {

    private final CRUDView<Region> view;
    private final CRUDListView<Region> listView;
    private final Cache<Region> cache;
    private final Remote<Region> remote;

    private boolean isEditing = false;

    @Inject
    public RegionPresenter( UsersErrorHandler errors,
            RegionView view,
            RegionListView listView,
            RegionCache cache,
            RegionRemote remote ){
        super( errors );
        this.view = view;
        this.view.setPresenter(this);
        this.listView = listView;
        this.listView.setPresenter(this);
        this.cache = cache;
        this.remote = remote;
    }

    //@Override
    public void showAll(){
        isEditing = false;
        setWidget( listView );
        reset( cache.getOrLoadModels() );
    }

    //@Override
    public void edit(int id) {
        edit( cache.getOrLoadModel(id) );
    }
    
    //@Override
    public void edit( Region model ){
        isEditing = true;
        setWidget( view );
        view.edit( model );
    }

    //@Override
    public void showNew(){
        isEditing = true;
        setWidget( view );
        view.showNew( );
    }
    
    //@Override
    public void show( Region model ){
        isEditing = false;
        setWidget( view );
        view.show( model );
    }
    
    //@Override
    public void show( int id ){
        show( cache.getOrLoadModel(id) );
    }

    public void create( final Region model ) {
        remote.create( model );
    }

    public void delete( final Region model ) {
        remote.delete( model );
    }
        
    //@Override
    public void reset( Region model ) {
        view.reset( model );
    }
 
    //@Override
    public void reset( List<Region> models ) {
        listView.reset( models );
    }

    public void save( Region model ) { 
        remote.update( model );
    }

    //@Override
    public boolean isDirty() {
        return isEditing && view.isDirty();
    }
}
