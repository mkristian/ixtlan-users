package de.mkristian.ixtlan.users.client.places;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulPlace;
import de.mkristian.ixtlan.gwt.errors.Error;
import de.mkristian.ixtlan.users.client.managed.ActivityFactory;


import com.google.gwt.activity.shared.Activity;

public class ErrorPlace extends RestfulPlace<Error, ActivityFactory> {
    
    public static final String NAME = "errors";

    public Activity create(ActivityFactory factory){
        return factory.create(this);
    }
    
    public ErrorPlace(RestfulAction restfulAction) {
        super(restfulAction, NAME);
    }

    public ErrorPlace(Error model, RestfulAction restfulAction) {
        super(model.getId(), model, restfulAction, NAME);
    }

    public ErrorPlace(int id, RestfulAction restfulAction) {
        super(id, restfulAction, NAME);
    }    
    
    public ErrorPlace(String id, RestfulAction restfulAction) {
        super(id, restfulAction, NAME);
    }
}