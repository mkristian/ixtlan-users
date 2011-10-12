package org.dhamma.users.client.places;

import de.mkristian.gwt.rails.places.RestfulPlaceTokenizer;

public class RemotePermissionPlaceTokenizer extends RestfulPlaceTokenizer<RemotePermissionPlace> {
    
    public RemotePermissionPlace getPlace(String token) {
        Token t = toToken(token);
        if(t.identifier == null){
            return new RemotePermissionPlace(t.action);
        }
        else {
            return new RemotePermissionPlace(t.id, t.action);
        }
    }
}
