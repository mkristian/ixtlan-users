package org.dhamma.users.client.places;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulPlace;

import org.dhamma.users.client.managed.ActivityFactory;
import org.dhamma.users.client.models.Profile;

import com.google.gwt.activity.shared.Activity;

public class ProfilePlace extends RestfulPlace<Profile, ActivityFactory> {
    
    public static final String NAME = "profiles";

    public Activity create(ActivityFactory factory){
        return factory.create(this);
    }
    
    public ProfilePlace(RestfulAction restfulAction) {
        super(restfulAction, NAME);
    }

    public ProfilePlace(Profile model, RestfulAction restfulAction) {
        super(model, restfulAction, NAME);
    }

    public ProfilePlace(int id, RestfulAction restfulAction) {
        super(id, restfulAction, NAME);
    }    
    
    public ProfilePlace(String id, RestfulAction restfulAction) {
        super(id, restfulAction, NAME);
    }
}