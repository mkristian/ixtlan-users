package org.dhamma.users.client.places;

import de.mkristian.gwt.rails.places.QueryableRestfulPlaceTokenizer;
import de.mkristian.gwt.rails.places.RestfulAction;

public class AtPlaceTokenizer extends QueryableRestfulPlaceTokenizer<AtPlace> {

    @Override
    protected AtPlace newRestfulPlace(String query) {
        return new AtPlace(query);
    }

    @Override
    protected AtPlace newRestfulPlace(RestfulAction action) {
        return new AtPlace(action);
    }

    @Override
    protected AtPlace newRestfulPlace(int id, RestfulAction action) {
        return new AtPlace(id, action);
    }

//
//    public AtPlace getPlace(String token) {
//        Token t = toToken(token);
//        if(t.identifier == null){
//            return new AtPlace(t.action);
//        }
//        else {
//            return new AtPlace(t.id, t.action);
//        }
//    }
}
