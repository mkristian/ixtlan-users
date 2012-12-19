package de.mkristian.ixtlan.users.client.places;

import de.mkristian.gwt.rails.places.QueryableRestfulPlaceTokenizer;
import de.mkristian.gwt.rails.places.RestfulAction;

public class AtPlaceTokenizer extends QueryableRestfulPlaceTokenizer<AtPlace> {

    @Override
    protected AtPlace newRestfulPlace(RestfulAction action, String query) {
        return new AtPlace(action, query);
    }

    @Override
    protected AtPlace newRestfulPlace( int id, RestfulAction action, String query) {
        return new AtPlace( id, action, query );
    }
}
