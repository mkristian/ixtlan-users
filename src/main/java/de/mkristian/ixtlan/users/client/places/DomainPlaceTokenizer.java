package de.mkristian.ixtlan.users.client.places;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulPlaceTokenizer;

public class DomainPlaceTokenizer extends RestfulPlaceTokenizer<DomainPlace> {
    
    @Override
    protected DomainPlace newRestfulPlace( RestfulAction action ) {
        return new DomainPlace( action );
    }

    @Override
    protected DomainPlace newRestfulPlace( int id, RestfulAction action ) {
        return new DomainPlace( id, action );
    }
}
