package org.dhamma.users.client.events;

import org.dhamma.users.client.models.Configuration;

import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEventHandler;

public class ConfigurationEvent extends ModelEvent<Configuration> {

    public static final Type<ModelEventHandler<Configuration>> TYPE = new Type<ModelEventHandler<Configuration>>();
    
    public ConfigurationEvent(Configuration model, ModelEvent.Action action) {
        super(model, action);
    }

    @Override
    public Type<ModelEventHandler<Configuration>> getAssociatedType() {
        return TYPE;
    }
}