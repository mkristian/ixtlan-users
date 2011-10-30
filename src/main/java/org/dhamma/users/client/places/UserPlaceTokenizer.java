package org.dhamma.users.client.places;

import de.mkristian.gwt.rails.places.QueryableRestfulPlaceTokenizer;
import de.mkristian.gwt.rails.places.RestfulAction;

public class UserPlaceTokenizer extends QueryableRestfulPlaceTokenizer<UserPlace> {
    
    @Override
    protected UserPlace newRestfulPlace(String query) {
        return new UserPlace(query);
    }

    @Override
    protected UserPlace newRestfulPlace(RestfulAction action) {
        return new UserPlace(action);
    }

    @Override
    protected UserPlace newRestfulPlace(int id, RestfulAction action) {
        return new UserPlace(id, action);
    }

}
