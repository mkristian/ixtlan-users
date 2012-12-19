package de.mkristian.ixtlan.users.client.events;

import java.util.List;

import org.fusesource.restygwt.client.Method;

import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEventHandler;
import de.mkristian.ixtlan.users.client.models.Application;

public class ApplicationEvent extends ModelEvent<Application> {

    public static final Type<ModelEventHandler<Application>> TYPE = new Type<ModelEventHandler<Application>>();
    
    public ApplicationEvent(Method method, Application model, ModelEvent.Action action) {
        super(method, model, action);
    }

    public ApplicationEvent(Method method, List<Application> models, ModelEvent.Action action) {
        super(method, models, action);
    }

    public ApplicationEvent(Method method, Throwable e) {
        super( method, e );
    }

    @Override
    public Type<ModelEventHandler<Application>> getAssociatedType() {
        return TYPE;
    }
}