package org.dhamma.users.client.places;

import de.mkristian.gwt.rails.places.RestfulPlaceTokenizer;

public class ApplicationPlaceTokenizer extends RestfulPlaceTokenizer<ApplicationPlace> {
    
    public ApplicationPlace getPlace(String token) {
        Token t = toToken(token);
        if(t.identifier == null){
            return new ApplicationPlace(t.action);
        }
        else {
            return new ApplicationPlace(t.id, t.action);
        }
    }
}
