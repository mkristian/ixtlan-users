package de.mkristian.ixtlan.users.client.events;

import org.fusesource.restygwt.client.Method;

import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEventHandler;
import de.mkristian.ixtlan.users.client.models.Configuration;

public class ConfigurationEvent extends ModelEvent<Configuration> {

    public static final Type<ModelEventHandler<Configuration>> TYPE = new Type<ModelEventHandler<Configuration>>();
    
    public ConfigurationEvent( Method method, Configuration model, 
            ModelEvent.Action action) {
        super( method, model, action );
    }

    public ConfigurationEvent( Method method, Throwable e ) {
        super( method, e );
    }

    @Override
    public Type<ModelEventHandler<Configuration>> getAssociatedType() {
        return TYPE;
    }
}