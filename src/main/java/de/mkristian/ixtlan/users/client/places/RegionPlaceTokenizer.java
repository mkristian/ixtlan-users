package de.mkristian.ixtlan.users.client.places;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulPlaceTokenizer;

public class RegionPlaceTokenizer extends RestfulPlaceTokenizer<RegionPlace> {
    
    @Override
    protected RegionPlace newRestfulPlace( RestfulAction action ) {
        return new RegionPlace( action );
    }

    @Override
    protected RegionPlace newRestfulPlace( int id, RestfulAction action ) {
        return new RegionPlace( id, action );
    }
}
