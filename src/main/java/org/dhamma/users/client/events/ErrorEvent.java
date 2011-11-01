package org.dhamma.users.client.events;

import java.util.List;

import org.dhamma.users.client.models.Error;

import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEventHandler;

public class ErrorEvent extends ModelEvent<Error> {

    public static final Type<ModelEventHandler<Error>> TYPE = new Type<ModelEventHandler<Error>>();
    
    public ErrorEvent(Error model, ModelEvent.Action action) {
        super(model, action);
    }

    public ErrorEvent(List<Error> models, ModelEvent.Action action) {
        super(models, action);
    }

    @Override
    public Type<ModelEventHandler<Error>> getAssociatedType() {
        return TYPE;
    }
}