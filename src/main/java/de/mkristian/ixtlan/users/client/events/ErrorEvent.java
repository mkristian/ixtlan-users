package de.mkristian.ixtlan.users.client.events;

import java.util.List;

import org.fusesource.restygwt.client.Method;

import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEventHandler;
import de.mkristian.ixtlan.users.client.models.Error;

public class ErrorEvent extends ModelEvent<Error> {

    public static final Type<ModelEventHandler<Error>> TYPE = new Type<ModelEventHandler<Error>>();
    
    public ErrorEvent( Method method, Error model,
                ModelEvent.Action action ) {
        super( method, model, action );
    }

    public ErrorEvent( Method method, List<Error> models,
                ModelEvent.Action action ) {
        super( method, models, action );
    }

    public ErrorEvent( Method method, Throwable e) {
        super( method, e );
    }

    @Override
    public Type<ModelEventHandler<Error>> getAssociatedType() {
        return TYPE;
    }
}