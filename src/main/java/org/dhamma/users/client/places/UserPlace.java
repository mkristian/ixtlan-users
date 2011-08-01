package org.dhamma.users.client.places;

import de.mkristian.gwt.rails.RestfulAction;

import org.dhamma.users.client.managed.ActivityFactory;
import org.dhamma.users.client.ActivityPlace;

import com.google.gwt.activity.shared.Activity;

public class UserPlace extends ActivityPlace {
    
    public Activity create(ActivityFactory factory){
        return factory.create(this);
    }
    
    public UserPlace(RestfulAction restfulAction) {
        super(restfulAction);
    }

    public UserPlace(int id, RestfulAction restfulAction) {
        super(id, restfulAction);
    }    
    
    public UserPlace(String id, RestfulAction restfulAction) {
        super(id, restfulAction);
    }
}