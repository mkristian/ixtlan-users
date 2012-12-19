package de.mkristian.ixtlan.users.client.events;

import java.util.List;


import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEventHandler;
import de.mkristian.ixtlan.users.client.models.RemotePermission;

public class RemotePermissionEvent extends ModelEvent<RemotePermission> {

    public static final Type<ModelEventHandler<RemotePermission>> TYPE = new Type<ModelEventHandler<RemotePermission>>();
    
    public RemotePermissionEvent(RemotePermission model, ModelEvent.Action action) {
        super(model, action);
    }

    public RemotePermissionEvent(List<RemotePermission> models, ModelEvent.Action action) {
        super(models, action);
    }

    @Override
    public Type<ModelEventHandler<RemotePermission>> getAssociatedType() {
        return TYPE;
    }
}