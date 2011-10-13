package org.dhamma.users.client.places;

import de.mkristian.gwt.rails.places.RestfulAction;

import org.dhamma.users.client.managed.ActivityFactory;
import org.dhamma.users.client.models.Application;
import org.dhamma.users.client.ActivityPlace;

import com.google.gwt.activity.shared.Activity;

public class ApplicationPlace extends ActivityPlace<Application> {
    
    public Activity create(ActivityFactory factory){
        return factory.create(this);
    }
    
    public ApplicationPlace(RestfulAction restfulAction) {
        super(restfulAction, "applications");
    }

    public ApplicationPlace(Application model, RestfulAction restfulAction) {
        super(model.getId(), model, restfulAction, "applications");
    }

    public ApplicationPlace(int id, RestfulAction restfulAction) {
        super(id, restfulAction, "applications");
    }    
    
    public ApplicationPlace(String id, RestfulAction restfulAction) {
        super(id, restfulAction, "applications");
    }
}