package de.mkristian.ixtlan.users.client.events;


import org.fusesource.restygwt.client.Method;

import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEventHandler;
import de.mkristian.ixtlan.users.client.models.Profile;

public class ProfileEvent extends ModelEvent<Profile> {

    public static final Type<ModelEventHandler<Profile>> TYPE = new Type<ModelEventHandler<Profile>>();
    
    public ProfileEvent( Method method, Profile model, ModelEvent.Action action ) {
        super( method, model, action );
    }

    public ProfileEvent( Method method, Throwable e ) {
        super( method, e );
    }

    @Override
    public Type<ModelEventHandler<Profile>> getAssociatedType() {
        return TYPE;
    }
}