package org.dhamma.users.client.managed;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.dhamma.users.client.models.User;

import com.google.gwt.place.shared.Place;

import de.mkristian.gwt.rails.places.RestfulPlace;
import de.mkristian.gwt.rails.places.RestfulPlaceHistoryMapper;
import de.mkristian.gwt.rails.session.SessionManager;

@Singleton
public class UsersPlaceHistoryMapper extends RestfulPlaceHistoryMapper {
    
    private final SessionManager<User> manager;
    
    @Inject
    public UsersPlaceHistoryMapper(SessionManager<User> manager){
        register("ats", new org.dhamma.users.client.places.AtPlaceTokenizer());
        register("regions", new org.dhamma.users.client.places.RegionPlaceTokenizer());
        register("audits", new org.dhamma.users.client.places.AuditPlaceTokenizer());
        register("errors", new org.dhamma.users.client.places.ErrorPlaceTokenizer());
        register("applications", new org.dhamma.users.client.places.ApplicationPlaceTokenizer());
        register("remote_permissions", new org.dhamma.users.client.places.RemotePermissionPlaceTokenizer());
        register("groups", new org.dhamma.users.client.places.GroupPlaceTokenizer());
        register("configurations", new org.dhamma.users.client.places.ConfigurationPlaceTokenizer());
        register("profiles", new org.dhamma.users.client.places.ProfilePlaceTokenizer());
        register("users", new org.dhamma.users.client.places.UserPlaceTokenizer());
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
