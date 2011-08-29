package org.dhamma.users.client.places;

import de.mkristian.gwt.rails.places.RestfulPlaceTokenizer;

public class ProfilePlaceTokenizer extends RestfulPlaceTokenizer<ProfilePlace> {
    
    public ProfilePlace getPlace(String token) {
        return new ProfilePlace(toSingletonToken(token).action);
    }
}
