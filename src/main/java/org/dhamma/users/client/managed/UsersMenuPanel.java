package org.dhamma.users.client.managed;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.dhamma.users.client.models.User;

import com.google.gwt.place.shared.PlaceController;

import de.mkristian.gwt.rails.places.RestfulActionEnum;
import de.mkristian.gwt.rails.session.SessionManager;
import de.mkristian.gwt.rails.views.MenuPanel;

@Singleton
public class UsersMenuPanel extends MenuPanel<User> {

    @Inject
    UsersMenuPanel(final PlaceController placeController, SessionManager<User> sessionManager){
        super(sessionManager, placeController);
        // TODO profile should move into bread-crumbs
        addButton("Profile", new org.dhamma.users.client.places.ProfilePlace(RestfulActionEnum.SHOW));
        addButton("Configuration", new org.dhamma.users.client.places.ConfigurationPlace(RestfulActionEnum.SHOW));
        addButton("Applications", new org.dhamma.users.client.places.ApplicationPlace(RestfulActionEnum.INDEX));
        addButton("Remote permissions", new org.dhamma.users.client.places.RemotePermissionPlace(RestfulActionEnum.INDEX));
        addButton("Groups", new org.dhamma.users.client.places.GroupPlace(RestfulActionEnum.INDEX));
        addButton("Users", new org.dhamma.users.client.places.UserPlace(RestfulActionEnum.INDEX));
    }
}
