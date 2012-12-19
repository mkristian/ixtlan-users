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

import javax.inject.Inject;
import javax.inject.Singleton;

import org.fusesource.restygwt.client.Method;

import com.google.gwt.user.client.ui.Label;

import de.mkristian.gwt.rails.ErrorHandlerWithDisplay;
import de.mkristian.gwt.rails.Notice;

@Singleton
public class UsersErrorHandler extends ErrorHandlerWithDisplay {

    @Inject
    public UsersErrorHandler(Notice notice) {
        super(notice);
    }


    // @Override
    // protected void generalError(Method method) {
    //     show("Error");
    // }

    // @Override
    // protected void undefinedStatus(Method method) {
    //     if (method != null) {
    //         show("Error: " + method.getResponse().getStatusText());
    //     }
    //     else {
    //         show("Error");
    //     }
    // }

    // @Override
    // protected void conflict(Method method) {
    //     show("Conflict! Data was modified by someone else. Please reload the data.");
    // }

    // @Override
    // protected void unprocessableEntity(Method method) {
    //     showErrors(method);
    // }

    // @Override
    // protected void forbidden(Method method) {
    // 	display.setWidget(new Label("Forbidden."));
    // }

    @Override
    protected void notFound(Method method) {
    	getDisplay().setWidget(new Label("Resource Not Found."));
    }
}