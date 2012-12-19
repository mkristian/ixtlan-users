package de.mkristian.ixtlan.users.client.events;

import java.util.List;

import org.fusesource.restygwt.client.Method;

import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEventHandler;
import de.mkristian.ixtlan.users.client.models.User;

public class UserEvent extends ModelEvent<User> {

    public static final Type<ModelEventHandler<User>> TYPE = new Type<ModelEventHandler<User>>();
    
    public UserEvent(Method method, User model, ModelEvent.Action action) {
        super(method, model, action);
    }

    public UserEvent(Method method, List<User> models, ModelEvent.Action action) {
        super(method, models, action);
    }

    public UserEvent( Method method, Throwable e ) {
        super( method, e );
    }

    @Override
    public Type<ModelEventHandler<User>> getAssociatedType() {
        return TYPE;
    }
}