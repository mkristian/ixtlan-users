package org.dhamma.users.client.places;

import org.dhamma.users.client.ActivityPlace;
import org.dhamma.users.client.managed.ActivityFactory;
import org.dhamma.users.client.models.User;

import com.google.gwt.activity.shared.Activity;

import de.mkristian.gwt.rails.places.RestfulAction;

public class UserPlace extends ActivityPlace<User> {
    
    public Activity create(ActivityFactory factory){
        return factory.create(this);
    }
    
    public UserPlace(User user, RestfulAction restfulAction) {
        super(user, restfulAction, "users");
    }
    
    public UserPlace(RestfulAction restfulAction) {
        super(restfulAction, "users");
    }

    public UserPlace(int id, RestfulAction restfulAction) {
        super(id, restfulAction, "users");
    }    
    
    public UserPlace(String id, RestfulAction restfulAction) {
        super(id, restfulAction, "users");
    }
}