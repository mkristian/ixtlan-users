package de.mkristian.ixtlan.users.client.places;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulPlace;
import de.mkristian.ixtlan.users.client.managed.ActivityFactory;
import de.mkristian.ixtlan.users.client.models.Application;


import com.google.gwt.activity.shared.Activity;

public class ApplicationPlace extends RestfulPlace<Application, ActivityFactory> {
    
    public static final String NAME = "applications";

    public Activity create(ActivityFactory factory){
        return factory.create(this);
    }
    
    public ApplicationPlace(RestfulAction restfulAction) {
        super(restfulAction, NAME);
    }

    public ApplicationPlace(Application model, RestfulAction restfulAction) {
        super(model.getId(), model, restfulAction, NAME);
    }

    public ApplicationPlace(int id, RestfulAction restfulAction) {
        super(id, restfulAction, NAME);
    }    
    
    public ApplicationPlace(String id, RestfulAction restfulAction) {
        super(id, restfulAction, NAME);
    }
}