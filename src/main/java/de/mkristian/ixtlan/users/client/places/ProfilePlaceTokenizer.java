package de.mkristian.ixtlan.users.client.places;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.SingletonRestfulPlaceTokenizer;

public class ProfilePlaceTokenizer extends SingletonRestfulPlaceTokenizer<ProfilePlace> {
    
    @Override
    protected ProfilePlace newRestfulPlace(RestfulAction action) {
        return new ProfilePlace( action );
    }
}
