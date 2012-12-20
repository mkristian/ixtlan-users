package de.mkristian.ixtlan.users.client.events;

import java.util.List;

import org.fusesource.restygwt.client.Method;

import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEventHandler;
import de.mkristian.ixtlan.users.client.models.Region;

public class RegionEvent extends ModelEvent<Region> {

    public static final Type<ModelEventHandler<Region>> TYPE = new Type<ModelEventHandler<Region>>();
    
    public RegionEvent( Method method, Region model,
                ModelEvent.Action action ) {
        super( method, model, action );
    }

    public RegionEvent( Method method, List<Region> models,
                ModelEvent.Action action ) {
        super( method, models, action );
    }

    public RegionEvent( Method method, Throwable e ) {
        super( method, e );
    }

    @Override
    public Type<ModelEventHandler<Region>> getAssociatedType() {
        return TYPE;
    }
}