package org.dhamma.users.client.places;

import org.dhamma.users.client.managed.ActivityFactory;
import org.dhamma.users.client.models.User;

import com.google.gwt.activity.shared.Activity;

import de.mkristian.gwt.rails.places.QueryableRestfulPlace;
import de.mkristian.gwt.rails.places.RestfulAction;

public class AtPlace extends QueryableRestfulPlace<User, ActivityFactory> {
    
    public static final String NAME = "ats";

    public Activity create(ActivityFactory factory){
        return factory.create(this);
    }

    public AtPlace(String query) {
        super(query, NAME);
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