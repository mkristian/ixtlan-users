package org.dhamma.users.client.places;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulPlace;

import org.dhamma.users.client.managed.ActivityFactory;
import org.dhamma.users.client.models.RemotePermission;

import com.google.gwt.activity.shared.Activity;

public class RemotePermissionPlace extends RestfulPlace<RemotePermission, ActivityFactory> {
    
    public static final String NAME = "remote_permissions";

    public Activity create(ActivityFactory factory){
        return factory.create(this);
    }
    
    public RemotePermissionPlace(RestfulAction restfulAction) {
        super(restfulAction, NAME);
    }

    public RemotePermissionPlace(RemotePermission model, RestfulAction restfulAction) {
        super(model.getId(), model, restfulAction, NAME);
    }

    public RemotePermissionPlace(int id, RestfulAction restfulAction) {
        super(id, restfulAction, NAME);
    }    
    
    public RemotePermissionPlace(String id, RestfulAction restfulAction) {
        super(id, restfulAction, NAME);
    }
}