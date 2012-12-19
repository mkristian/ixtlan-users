package de.mkristian.ixtlan.users.client.events;

import java.util.List;

import org.fusesource.restygwt.client.Method;

import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEventHandler;
import de.mkristian.ixtlan.users.client.models.Group;

public class GroupEvent extends ModelEvent<Group> {

    public static final Type<ModelEventHandler<Group>> TYPE = new Type<ModelEventHandler<Group>>();
    
    public GroupEvent( Method method, Group model, 
                ModelEvent.Action action ) {
        super( model, action );
    }

    public GroupEvent( Method method, List<Group> models, 
                ModelEvent.Action action ) {
        super( models, action );
    }

    public GroupEvent(Method method, Throwable e) {
        super( method, e );
    }

    @Override
    public Type<ModelEventHandler<Group>> getAssociatedType() {
        return TYPE;
    }
}