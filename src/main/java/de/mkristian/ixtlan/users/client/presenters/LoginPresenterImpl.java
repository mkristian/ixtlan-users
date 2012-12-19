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

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.core.client.GWT;

import de.mkristian.gwt.rails.Notice;
import de.mkristian.gwt.rails.session.Authentication;
import de.mkristian.gwt.rails.session.LoginPresenter;
import de.mkristian.gwt.rails.session.Session;
import de.mkristian.gwt.rails.session.SessionHandler;
import de.mkristian.gwt.rails.session.SessionManager;
import de.mkristian.ixtlan.users.client.UsersApplication;
import de.mkristian.ixtlan.users.client.models.User;
import de.mkristian.ixtlan.users.client.restservices.SessionRestService;

@Singleton
public class LoginPresenterImpl implements LoginPresenter {

    private final SessionRestService service;
    private final SessionManager<User> sessionManager;
    private final Notice notice;

    @Inject
    public LoginPresenterImpl(final SessionRestService service,
            final SessionManager<User> sessionManager,
            final Notice notice, 
            final UsersApplication app) {
        this.service = service;
        this.sessionManager = sessionManager;
        this.notice = notice;
        sessionManager.addSessionHandler(new SessionHandler<User>() {
            
            //@Override
            public void timeout() {
                notice.info("timeout");
                logout();
            }
                
            //@Override
            public void logout() {
                app.stopSession();
                service.destroy(new MethodCallback<Void>() {
                    public void onSuccess(Method method, Void response) {
                        sessionManager.purgeCaches();
                    }
                    public void onFailure(Method method, Throwable exception) {
                        sessionManager.purgeCaches();
                    }
                });
            }
                
            //@Override
            public void login(User user) {
                app.startSession(user);
            }
            
            //@Override
            public void accessDenied() {
                notice.error("access denied");
            }
        });        
    }

    public void login(final String login, final String password) {
        Authentication authentication = new Authentication(login, password);
        service.create(authentication, new MethodCallback<Session<User>>() {

            public void onSuccess(Method method, Session<User> session) {
                GWT.log("logged in: " + login);
                sessionManager.login(session);
            }

            public void onFailure(Method method, Throwable exception) {
                GWT.log("login failed: " + exception.getMessage(), exception);
                sessionManager.accessDenied();
            }
        });
    }

    public void resetPassword(final String login) {
        Authentication authentication = new Authentication(login);
        service.resetPassword(authentication, new MethodCallback<Void>() {

            public void onSuccess(Method method, Void result) {
                notice.info("new password was sent to your email address");
            }

            public void onFailure(Method method, Throwable exception) {
                notice.error("could not reset password - username/email unknown");
            }
        });
    }
}