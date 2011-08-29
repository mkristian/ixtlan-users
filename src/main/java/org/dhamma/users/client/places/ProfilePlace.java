package org.dhamma.users.client.places;

import de.mkristian.gwt.rails.places.RestfulAction;

import org.dhamma.users.client.managed.ActivityFactory;
import org.dhamma.users.client.models.Profile;
import org.dhamma.users.client.ActivityPlace;

import com.google.gwt.activity.shared.Activity;

public class ProfilePlace extends ActivityPlace<Profile> {
    
    public Activity create(ActivityFactory factory){
        return factory.create(this);
    }
    
    public ProfilePlace(RestfulAction restfulAction) {
        super(restfulAction, "profiles");
    }

    public ProfilePlace(Profile model, RestfulAction restfulAction) {
        super(model, restfulAction, "profiles");
    }

    public ProfilePlace(int id, RestfulAction restfulAction) {
        super(id, restfulAction, "profiles");
    }    
    
    public ProfilePlace(String id, RestfulAction restfulAction) {
        super(id, restfulAction, "profiles");
    }
}