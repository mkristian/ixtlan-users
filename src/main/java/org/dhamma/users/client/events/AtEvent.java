package org.dhamma.users.client.events;

import java.util.List;

import org.dhamma.users.client.models.At;

import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEventHandler;

public class AtEvent extends ModelEvent<At> {

    public static final Type<ModelEventHandler<At>> TYPE = new Type<ModelEventHandler<At>>();
    
    public AtEvent(At model, ModelEvent.Action action) {
        super(model, action);
    }

    public AtEvent(List<At> models, ModelEvent.Action action) {
        super(models, action);
    }

    @Override
    public Type<ModelEventHandler<At>> getAssociatedType() {
        return TYPE;
    }
}