package org.dhamma.users.client.places;

import de.mkristian.gwt.rails.places.RestfulAction;

import org.dhamma.users.client.managed.ActivityFactory;
import org.dhamma.users.client.models.RemotePermission;
import org.dhamma.users.client.ActivityPlace;

import com.google.gwt.activity.shared.Activity;

public class RemotePermissionPlace extends ActivityPlace<RemotePermission> {
    
    public Activity create(ActivityFactory factory){
        return factory.create(this);
    }
    
    public RemotePermissionPlace(RestfulAction restfulAction) {
        super(restfulAction, "remote_permissions");
    }

    public RemotePermissionPlace(RemotePermission model, RestfulAction restfulAction) {
        super(model.getId(), model, restfulAction, "remote_permissions");
    }

    public RemotePermissionPlace(int id, RestfulAction restfulAction) {
        super(id, restfulAction, "remote_permissions");
    }    
    
    public RemotePermissionPlace(String id, RestfulAction restfulAction) {
        super(id, restfulAction, "remote_permissions");
    }
}