package org.dhamma.users.client.events;

import java.util.List;

import org.dhamma.users.client.models.Group;

import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEventHandler;

public class GroupEvent extends ModelEvent<Group> {

    public static final Type<ModelEventHandler<Group>> TYPE = new Type<ModelEventHandler<Group>>();
    
    public GroupEvent(Group model, ModelEvent.Action action) {
        super(model, action);
    }

    public GroupEvent(List<Group> models, ModelEvent.Action action) {
        super(models, action);
    }

    @Override
    public Type<ModelEventHandler<Group>> getAssociatedType() {
        return TYPE;
    }
}