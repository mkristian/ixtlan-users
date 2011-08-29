package org.dhamma.users.client.places;

import de.mkristian.gwt.rails.places.RestfulAction;

import org.dhamma.users.client.managed.ActivityFactory;
import org.dhamma.users.client.models.User;
import org.dhamma.users.client.ActivityPlace;

import com.google.gwt.activity.shared.Activity;

public class UserPlace extends ActivityPlace<User> {
    
    public Activity create(ActivityFactory factory){
        return factory.create(this);
    }
    
    public UserPlace(RestfulAction restfulAction) {
        super(restfulAction, "users");
    }

    public UserPlace(User model, RestfulAction restfulAction) {
        super(model, restfulAction, "users");
    }

    public UserPlace(int id, RestfulAction restfulAction) {
        super(id, restfulAction, "users");
    }    
    
    public UserPlace(String id, RestfulAction restfulAction) {
        super(id, restfulAction, "users");
    }
}