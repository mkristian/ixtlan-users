package de.mkristian.ixtlan.users.client.places;

import com.google.gwt.activity.shared.Activity;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulPlace;
import de.mkristian.ixtlan.users.client.managed.ActivityFactory;
import de.mkristian.ixtlan.users.client.models.Domain;

public class DomainPlace extends RestfulPlace<Domain, ActivityFactory> {
    
    public static final String NAME = "domains";

    public Activity create(ActivityFactory factory){
        return factory.create(this);
    }
    
    public DomainPlace(RestfulAction restfulAction) {
        super(restfulAction, NAME);
    }

    public DomainPlace(Domain model, RestfulAction restfulAction) {
        super(model.getId(), model, restfulAction, NAME);
    }

    public DomainPlace(int id, RestfulAction restfulAction) {
        super(id, restfulAction, NAME);
    }    
    
    public DomainPlace(String id, RestfulAction restfulAction) {
        super(id, restfulAction, NAME);
    }
}