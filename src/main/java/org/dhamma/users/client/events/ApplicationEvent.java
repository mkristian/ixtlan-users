package org.dhamma.users.client.events;

import java.util.List;

import org.dhamma.users.client.models.Application;

import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEventHandler;

public class ApplicationEvent extends ModelEvent<Application> {

    public static final Type<ModelEventHandler<Application>> TYPE = new Type<ModelEventHandler<Application>>();
    
    public ApplicationEvent(Application model, ModelEvent.Action action) {
        super(model, action);
    }

    public ApplicationEvent(List<Application> models, ModelEvent.Action action) {
        super(models, action);
    }

    @Override
    public Type<ModelEventHandler<Application>> getAssociatedType() {
        return TYPE;
    }
}