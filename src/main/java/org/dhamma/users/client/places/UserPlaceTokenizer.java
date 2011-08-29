package org.dhamma.users.client.places;

import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

import de.mkristian.gwt.rails.places.RestfulPlaceTokenizer;

@Prefix("users") 
public class UserPlaceTokenizer extends RestfulPlaceTokenizer<UserPlace> 
    implements PlaceTokenizer<UserPlace> {
    
    public UserPlace getPlace(String token) {
        Token t = toToken(token);
        if(t.identifier == null){
            return new UserPlace(t.action);
        }
        else {
            return new UserPlace(t.identifier, t.action);
        }
    }
}
