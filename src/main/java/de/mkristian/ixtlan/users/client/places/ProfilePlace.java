package de.mkristian.ixtlan.users.client.places;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulPlace;
import de.mkristian.ixtlan.users.client.managed.ActivityFactory;
import de.mkristian.ixtlan.users.client.models.Profile;


import com.google.gwt.activity.shared.Activity;

public class ProfilePlace extends RestfulPlace<Profile, ActivityFactory> {
    
    public static final String NAME = "profile";

    public Activity create(ActivityFactory factory){
        return factory.create(this);
    }
    
    public ProfilePlace(RestfulAction restfulAction) {
        super(restfulAction, NAME);
    }

    public ProfilePlace(Profile model, RestfulAction restfulAction) {
        super(model, restfulAction, NAME);
    }
}