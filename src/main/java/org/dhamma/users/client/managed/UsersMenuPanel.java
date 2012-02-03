package org.dhamma.users.client.managed;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.dhamma.users.client.models.User;

import com.google.gwt.place.shared.PlaceController;

import static de.mkristian.gwt.rails.places.RestfulActionEnum.*;
import de.mkristian.gwt.rails.session.SessionManager;
import de.mkristian.gwt.rails.views.MenuPanel;

@Singleton
public class UsersMenuPanel extends MenuPanel<User> {

    @Inject
    UsersMenuPanel(final PlaceController placeController, SessionManager<User> sessionManager){
        super(sessionManager, placeController);
        addButton("Ats", new org.dhamma.users.client.places.AtPlace(INDEX));
        addButton("Regions", new org.dhamma.users.client.places.RegionPlace(INDEX), SHOW);
        addButton("Audits", new org.dhamma.users.client.places.AuditPlace(INDEX));
        // TODO profile should move into bread-crumbs
        addButton("Profile", new org.dhamma.users.client.places.ProfilePlace(SHOW));
        addButton("Configuration", new org.dhamma.users.client.places.ConfigurationPlace(SHOW));
        addButton("Errors", new org.dhamma.users.client.places.ErrorPlace(INDEX));
        addButton("Applications", new org.dhamma.users.client.places.ApplicationPlace(INDEX), SHOW);
        addButton("Remote permissions", new org.dhamma.users.client.places.RemotePermissionPlace(INDEX));
        addButton("Groups", new org.dhamma.users.client.places.GroupPlace(INDEX), SHOW);
        addButton("Users", new org.dhamma.users.client.places.UserPlace(INDEX), SHOW);
    }
}
