package org.dhamma.users.client.places;

import de.mkristian.gwt.rails.places.RestfulPlaceTokenizer;

public class ErrorPlaceTokenizer extends RestfulPlaceTokenizer<ErrorPlace> {
    
    public ErrorPlace getPlace(String token) {
        Token t = toToken(token);
        if(t.identifier == null){
            return new ErrorPlace(t.action);
        }
        else {
            return new ErrorPlace(t.id, t.action);
        }
    }
}
