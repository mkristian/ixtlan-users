package org.dhamma.users.client.places;

import de.mkristian.gwt.rails.places.RestfulPlaceTokenizer;

public class AuditPlaceTokenizer extends RestfulPlaceTokenizer<AuditPlace> {
    
    public AuditPlace getPlace(String token) {
        Token t = toToken(token);
        if(t.identifier == null){
            return new AuditPlace(t.action);
        }
        else {
            return new AuditPlace(t.id, t.action);
        }
    }
}
