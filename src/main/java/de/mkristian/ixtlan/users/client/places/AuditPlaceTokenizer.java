package de.mkristian.ixtlan.users.client.places;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulPlaceTokenizer;

public class AuditPlaceTokenizer extends RestfulPlaceTokenizer<AuditPlace> {
    
    @Override
    protected AuditPlace newRestfulPlace( RestfulAction action ) {
        return new AuditPlace( action );
    }

    @Override
    protected AuditPlace newRestfulPlace( int id, RestfulAction action ) {
        return new AuditPlace( id, action );
    }
}
