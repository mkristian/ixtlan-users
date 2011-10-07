package org.dhamma.users.client.places;

import de.mkristian.gwt.rails.places.RestfulAction;

import org.dhamma.users.client.managed.ActivityFactory;
import org.dhamma.users.client.models.Group;
import org.dhamma.users.client.ActivityPlace;

import com.google.gwt.activity.shared.Activity;

public class GroupPlace extends ActivityPlace<Group> {
    
    public Activity create(ActivityFactory factory){
        return factory.create(this);
    }
    
    public GroupPlace(RestfulAction restfulAction) {
        super(restfulAction, "groups");
    }

    public GroupPlace(Group model, RestfulAction restfulAction) {
        super(model.getId(), model, restfulAction, "groups");
    }

    public GroupPlace(int id, RestfulAction restfulAction) {
        super(id, restfulAction, "groups");
    }    
    
    public GroupPlace(String id, RestfulAction restfulAction) {
        super(id, restfulAction, "groups");
    }
}