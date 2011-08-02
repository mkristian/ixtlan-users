package org.dhamma.users.client.places;

import de.mkristian.gwt.rails.RestfulAction;

import org.dhamma.users.client.managed.ActivityFactory;
import org.dhamma.users.client.ActivityPlace;

import com.google.gwt.activity.shared.Activity;

public class ProfilePlace extends ActivityPlace {
    
    public Activity create(ActivityFactory factory){
        return factory.create(this);
    }
    
    public ProfilePlace(RestfulAction restfulAction) {
        super(restfulAction);
    }

    public ProfilePlace(int id, RestfulAction restfulAction) {
        super(id, restfulAction);
    }    
    
    public ProfilePlace(String id, RestfulAction restfulAction) {
        super(id, restfulAction);
    }
}