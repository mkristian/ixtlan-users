package de.mkristian.ixtlan.users.client.places;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulPlaceTokenizer;

public class ErrorPlaceTokenizer extends RestfulPlaceTokenizer<ErrorPlace> {
    
    @Override
    protected ErrorPlace newRestfulPlace( RestfulAction action ) {
        return new ErrorPlace( action );
    }

    @Override
    protected ErrorPlace newRestfulPlace( int id, RestfulAction action ) {
        return new ErrorPlace( id, action );
    }
}
