package org.dhamma.users.client.places;

import de.mkristian.gwt.rails.RestfulAction;

import org.dhamma.users.client.managed.ActivityFactory;
import org.dhamma.users.client.ActivityPlace;

import com.google.gwt.activity.shared.Activity;

public class ConfigurationPlace extends ActivityPlace {
    
    public Activity create(ActivityFactory factory){
        return factory.create(this);
    }
    
    public ConfigurationPlace(RestfulAction restfulAction) {
        super(restfulAction);
    }

    public ConfigurationPlace(int id, RestfulAction restfulAction) {
        super(id, restfulAction);
    }    
    
    public ConfigurationPlace(String id, RestfulAction restfulAction) {
        super(id, restfulAction);
    }
}