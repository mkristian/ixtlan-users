package org.dhamma.users.client.places;

import org.dhamma.users.client.ActivityPlace;
import org.dhamma.users.client.managed.ActivityFactory;
import org.dhamma.users.client.models.Configuration;

import com.google.gwt.activity.shared.Activity;

import de.mkristian.gwt.rails.places.RestfulAction;

public class ConfigurationPlace extends ActivityPlace<Configuration> {
    
    public Activity create(ActivityFactory factory){
        return factory.create(this);
    }
    
    public ConfigurationPlace(RestfulAction restfulAction) {
        super(restfulAction, "configurations");
    }
    
    public ConfigurationPlace(Configuration model, RestfulAction restfulAction) {
        super(model, restfulAction, "configurations");
    }

    public ConfigurationPlace(int id, RestfulAction restfulAction) {
        super(id, restfulAction, "configurations");
    }    
    
    public ConfigurationPlace(String id, RestfulAction restfulAction) {
        super(id, restfulAction, "configurations");
    }
}