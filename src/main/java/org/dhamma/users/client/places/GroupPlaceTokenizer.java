package org.dhamma.users.client.places;

import de.mkristian.gwt.rails.places.RestfulPlaceTokenizer;

public class GroupPlaceTokenizer extends RestfulPlaceTokenizer<GroupPlace> {
    
    public GroupPlace getPlace(String token) {
        Token t = toToken(token);
        if(t.identifier == null){
            return new GroupPlace(t.action);
        }
        else {
            return new GroupPlace(t.id, t.action);
        }
    }
}
