package de.mkristian.ixtlan.users.client.places;


import com.google.gwt.activity.shared.Activity;

import de.mkristian.gwt.rails.places.QueryableRestfulPlace;
import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulActionEnum;
import de.mkristian.ixtlan.users.client.managed.ActivityFactory;
import de.mkristian.ixtlan.users.client.models.User;

public class AtPlace extends QueryableRestfulPlace<User, ActivityFactory> {
    
    public static final String NAME = "ats";

    public Activity create(ActivityFactory factory){
        return factory.create(this);
    }

    public AtPlace( String query ) {
        super( RestfulActionEnum.INDEX, NAME, query );
    }

    public AtPlace(int id, RestfulAction action, String query) {
        super( id, action, NAME, query );
    }

    public AtPlace(RestfulAction action, String query) {
        super( action, NAME, query );
    }
    public AtPlace(RestfulAction restfulAction) {
        super(restfulAction, NAME);
    }

    public AtPlace(User model, RestfulAction restfulAction) {
        super(model.getId(), model, restfulAction, NAME);
    }

    public AtPlace(int id, RestfulAction restfulAction) {
        super(id, restfulAction, NAME);
    }    
    
    public AtPlace(String id, RestfulAction restfulAction) {
        super(id, restfulAction, NAME);
    }
}