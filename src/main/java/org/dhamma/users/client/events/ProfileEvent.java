package org.dhamma.users.client.events;

import org.dhamma.users.client.models.Profile;

import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEventHandler;

public class ProfileEvent extends ModelEvent<Profile> {

    public static final Type<ModelEventHandler<Profile>> TYPE = new Type<ModelEventHandler<Profile>>();
    
    public ProfileEvent(Profile model, ModelEvent.Action action) {
        super(model, action);
    }

    @Override
    public Type<ModelEventHandler<Profile>> getAssociatedType() {
        return TYPE;
    }
}