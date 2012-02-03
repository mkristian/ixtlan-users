package org.dhamma.users.client.places;

import de.mkristian.gwt.rails.places.RestfulPlaceTokenizer;

public class RegionPlaceTokenizer extends RestfulPlaceTokenizer<RegionPlace> {
    
    public RegionPlace getPlace(String token) {
        Token t = toToken(token);
        if(t.identifier == null){
            return new RegionPlace(t.action);
        }
        else {
            return new RegionPlace(t.id, t.action);
        }
    }
}
