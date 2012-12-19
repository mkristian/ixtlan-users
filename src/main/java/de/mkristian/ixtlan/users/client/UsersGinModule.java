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

package de.mkristian.ixtlan.users.client;



import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.PlaceController.Delegate;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.inject.Key;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

import de.mkristian.gwt.rails.Application;
import de.mkristian.gwt.rails.session.Guard;
import de.mkristian.gwt.rails.session.HasSession;
import de.mkristian.gwt.rails.session.LoginPresenter;
import de.mkristian.gwt.rails.session.LoginView;
import de.mkristian.gwt.rails.session.SessionManager;
import de.mkristian.ixtlan.users.client.managed.ManagedGinModule;
import de.mkristian.ixtlan.users.client.managed.UsersPlaceHistoryMapper;
import de.mkristian.ixtlan.users.client.models.User;
import de.mkristian.ixtlan.users.client.presenters.LoginPresenterImpl;
import de.mkristian.ixtlan.users.client.views.LoginViewImpl;

public class UsersGinModule extends ManagedGinModule {

    @Override
    protected void configure() {
        super.configure();
        bind(Application.class).to(UsersApplication.class);
        bind(LoginPresenter.class).to(LoginPresenterImpl.class);
        bind(Guard.class).to(Key.get(new TypeLiteral<SessionManager<User>>() {})).in(Singleton.class);
        bind(HasSession.class).to(Key.get(new TypeLiteral<SessionManager<User>>() {})).in(Singleton.class);
        bind(PlaceHistoryMapper.class).to(UsersPlaceHistoryMapper.class).in(Singleton.class);
        bind(ActivityMapper.class).to(SessionActivityPlaceActivityMapper.class).in(Singleton.class);
        bind(Delegate.class).to(UsersConfirmation.class);
        bind(LoginView.class).to(LoginViewImpl.class);
        //bind(UsersConfirmation.class);
    }
}
