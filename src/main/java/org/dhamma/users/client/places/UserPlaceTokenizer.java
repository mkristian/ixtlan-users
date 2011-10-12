package org.dhamma.users.client.places;

import de.mkristian.gwt.rails.places.RestfulPlaceTokenizer;

public class UserPlaceTokenizer extends RestfulPlaceTokenizer<UserPlace> {
    
    public UserPlace getPlace(String token) {
        Token t = toToken(token);
        if(t.identifier == null){
            return new UserPlace(t.action);
        }
        else {
            return new UserPlace(t.id, t.action);
        }
    }
}
