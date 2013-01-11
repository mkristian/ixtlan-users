package de.mkristian.ixtlan.users.client.managed;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.mkristian.gwt.rails.places.SessionRestfulPlaceHistoryMapper;
import de.mkristian.gwt.rails.session.HasSession;

@Singleton
public class UsersPlaceHistoryMapper extends SessionRestfulPlaceHistoryMapper {
    
    @Inject
    public UsersPlaceHistoryMapper( HasSession session ){
        super( session );
        //register("ats", new de.mkristian.ixtlan.users.client.places.AtPlaceTokenizer());
        register("domains", new de.mkristian.ixtlan.users.client.places.DomainPlaceTokenizer());
        //register("regions", new de.mkristian.ixtlan.users.client.places.RegionPlaceTokenizer());
        register("audits", new de.mkristian.ixtlan.users.client.places.AuditPlaceTokenizer());
        register("errors", new de.mkristian.ixtlan.users.client.places.ErrorPlaceTokenizer());
        register("applications", new de.mkristian.ixtlan.users.client.places.ApplicationPlaceTokenizer());
        register("configuration", new de.mkristian.ixtlan.users.client.places.ConfigurationPlaceTokenizer());
        register("profile", new de.mkristian.ixtlan.users.client.places.ProfilePlaceTokenizer());
        register("users", new de.mkristian.ixtlan.users.client.places.UserPlaceTokenizer());
    }
}
