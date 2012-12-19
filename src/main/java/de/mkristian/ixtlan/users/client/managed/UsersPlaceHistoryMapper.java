package de.mkristian.ixtlan.users.client.managed;

import javax.inject.Inject;
import javax.inject.Singleton;


import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;

import de.mkristian.gwt.rails.places.RestfulPlace;
import de.mkristian.gwt.rails.places.RestfulPlaceHistoryMapper;
import de.mkristian.gwt.rails.session.SessionManager;
import de.mkristian.ixtlan.users.client.models.User;

import static de.mkristian.gwt.rails.places.RestfulPlaceTokenizer.SEPARATOR;
import static de.mkristian.gwt.rails.places.QueryableRestfulPlaceTokenizer.QUERY_SEPARATOR;

@Singleton
public class UsersPlaceHistoryMapper extends RestfulPlaceHistoryMapper {
    
    private final SessionManager<User> manager;
    
    @Inject
    public UsersPlaceHistoryMapper(SessionManager<User> manager){
        register("ats", new de.mkristian.ixtlan.users.client.places.AtPlaceTokenizer());
        register("regions", new de.mkristian.ixtlan.users.client.places.RegionPlaceTokenizer());
        register("audits", new de.mkristian.ixtlan.users.client.places.AuditPlaceTokenizer());
        register("errors", new de.mkristian.ixtlan.users.client.places.ErrorPlaceTokenizer());
        register("applications", new de.mkristian.ixtlan.users.client.places.ApplicationPlaceTokenizer());
        register("configuration", new de.mkristian.ixtlan.users.client.places.ConfigurationPlaceTokenizer());
        register("profile", new de.mkristian.ixtlan.users.client.places.ProfilePlaceTokenizer());
        register("users", new de.mkristian.ixtlan.users.client.places.UserPlaceTokenizer());
        this.manager = manager;
    }
    
    @Override
    public Place getPlace(String token) {
        RestfulPlace<?, ?> place = (RestfulPlace<?, ?>) super.getPlace(token);
        if(place != null){
            // place needs to be different on the level of equals in order to trigger an activity
            place.hasSession = manager.hasSession();
        }
        return place;
    }
}
