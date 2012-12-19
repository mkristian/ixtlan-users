package de.mkristian.ixtlan.users.client.places;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulPlaceTokenizer;

public class ApplicationPlaceTokenizer extends RestfulPlaceTokenizer<ApplicationPlace> {

    @Override
    protected ApplicationPlace newRestfulPlace( RestfulAction action ) {
        return new ApplicationPlace( action );
    }

    @Override
    protected ApplicationPlace newRestfulPlace( int id, RestfulAction action ) {
        return new ApplicationPlace( id, action );
    }
}
