package org.dhamma.users.client.places;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulPlace;

import org.dhamma.users.client.managed.ActivityFactory;
import org.dhamma.users.client.models.Group;

import com.google.gwt.activity.shared.Activity;

public class GroupPlace extends RestfulPlace<Group, ActivityFactory> {
    
    public static final String NAME = "groups";

    public Activity create(ActivityFactory factory){
        return factory.create(this);
    }
    
    public GroupPlace(RestfulAction restfulAction) {
        super(restfulAction, NAME);
    }

    public GroupPlace(Group model, RestfulAction restfulAction) {
        super(model.getId(), model, restfulAction, NAME);
    }

    public GroupPlace(int id, RestfulAction restfulAction) {
        super(id, restfulAction, NAME);
    }    
    
    public GroupPlace(String id, RestfulAction restfulAction) {
        super(id, restfulAction, NAME);
    }
}