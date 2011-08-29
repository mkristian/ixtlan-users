package org.dhamma.users.client.places;

import org.dhamma.users.client.ActivityPlace;
import org.dhamma.users.client.managed.ActivityFactory;
import org.dhamma.users.client.models.Profile;

import com.google.gwt.activity.shared.Activity;

import de.mkristian.gwt.rails.places.RestfulAction;

public class ProfilePlace extends ActivityPlace<Profile> {
    
    public Activity create(ActivityFactory factory){
        return factory.create(this);
    }
    
    public ProfilePlace(Profile profile, RestfulAction restfulAction) {
        super(profile, restfulAction, "profiles");
    }

    public ProfilePlace(RestfulAction restfulAction) {
        super(restfulAction, "profiles");
    }

    public ProfilePlace(int id, RestfulAction restfulAction) {
        super(id, restfulAction, "profiles");
    }    
    
    public ProfilePlace(String id, RestfulAction restfulAction) {
        super(id, restfulAction, "profiles");
    }
}