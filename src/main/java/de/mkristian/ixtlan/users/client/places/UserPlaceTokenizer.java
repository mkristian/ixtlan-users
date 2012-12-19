package de.mkristian.ixtlan.users.client.places;

import de.mkristian.gwt.rails.places.QueryableRestfulPlaceTokenizer;
import de.mkristian.gwt.rails.places.RestfulAction;

public class UserPlaceTokenizer extends QueryableRestfulPlaceTokenizer<UserPlace> {
    
    @Override
    protected UserPlace newRestfulPlace(RestfulAction action, String query) {
        return new UserPlace(action, query);
    }

    @Override
    protected UserPlace newRestfulPlace(int id, RestfulAction action,
            String query) {
        return new UserPlace(id, action, query);
    }

}
