package org.dhamma.users.client.events;

import java.util.List;

import org.dhamma.users.client.models.User;

import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEventHandler;

public class UserEvent extends ModelEvent<User> {

    public static final Type<ModelEventHandler<User>> TYPE = new Type<ModelEventHandler<User>>();
    
    public UserEvent(User model, ModelEvent.Action action) {
        super(model, action);
    }

    public UserEvent(List<User> models, ModelEvent.Action action) {
        super(models, action);
    }

    @Override
    public Type<ModelEventHandler<User>> getAssociatedType() {
        return TYPE;
    }
}