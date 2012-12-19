package de.mkristian.ixtlan.users.client.places;


import com.google.gwt.activity.shared.Activity;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulPlace;
import de.mkristian.ixtlan.users.client.managed.ActivityFactory;
import de.mkristian.ixtlan.users.client.models.Configuration;

public class ConfigurationPlace extends RestfulPlace<Configuration, ActivityFactory> {
    
    public static final String NAME = "configuration";

    public Activity create(ActivityFactory factory){
        return factory.create(this);
    }
    
    public ConfigurationPlace(RestfulAction restfulAction) {
        super(restfulAction, NAME);
    }

    public ConfigurationPlace(Configuration model, RestfulAction restfulAction) {
        super(model, restfulAction, NAME);
    }
}