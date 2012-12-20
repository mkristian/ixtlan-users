package de.mkristian.ixtlan.users.client.managed;

import static de.mkristian.gwt.rails.places.RestfulActionEnum.INDEX;
import static de.mkristian.gwt.rails.places.RestfulActionEnum.SHOW;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.place.shared.PlaceController;

import de.mkristian.gwt.rails.session.Guard;
import de.mkristian.gwt.rails.views.SessionMenu;

@Singleton
public class UsersMenu extends SessionMenu {

    @Inject
    UsersMenu( final PlaceController placeController, 
            final Guard guard ){
        super( placeController, guard );
        // TODO profile should move into bread-crumbs
        addButton("Profile", new de.mkristian.ixtlan.users.client.places.ProfilePlace(SHOW));
        addButton("Configuration", new de.mkristian.ixtlan.users.client.places.ConfigurationPlace(SHOW));
        addButton("Audits", new de.mkristian.ixtlan.users.client.places.AuditPlace(INDEX));
        addButton("Errors", new de.mkristian.ixtlan.users.client.places.ErrorPlace(INDEX));
        addButton("Domains", new de.mkristian.ixtlan.users.client.places.RegionPlace(INDEX), SHOW);
        addButton("Regions", new de.mkristian.ixtlan.users.client.places.RegionPlace(INDEX), SHOW);
        addButton("Applications", new de.mkristian.ixtlan.users.client.places.ApplicationPlace(INDEX), SHOW);
        addButton("Users", new de.mkristian.ixtlan.users.client.places.UserPlace(INDEX), SHOW);
        addButton("Ats", new de.mkristian.ixtlan.users.client.places.AtPlace(INDEX));
    }
}
