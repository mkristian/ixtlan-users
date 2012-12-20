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
import de.mkristian.ixtlan.users.client.caches.DomainCache;
import de.mkristian.ixtlan.users.client.caches.DomainRemote;
import de.mkristian.ixtlan.users.client.models.Domain;
import de.mkristian.ixtlan.users.client.views.DomainListView;
import de.mkristian.ixtlan.users.client.views.DomainView;

@Singleton
public class DomainPresenter extends CRUDPresenterImpl<Domain> {

    @Inject
    public DomainPresenter( UsersErrorHandler errors,
            DomainView view,
            DomainListView listView,
            DomainCache cache,
            DomainRemote remote ){
        super( errors, view, listView, cache, remote );
    }
}
