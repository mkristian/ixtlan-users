package org.dhamma.users.client.events;

import java.util.List;

import org.dhamma.users.client.models.Region;

import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEventHandler;

public class RegionEvent extends ModelEvent<Region> {

    public static final Type<ModelEventHandler<Region>> TYPE = new Type<ModelEventHandler<Region>>();
    
    public RegionEvent(Region model, ModelEvent.Action action) {
        super(model, action);
    }

    public RegionEvent(List<Region> models, ModelEvent.Action action) {
        super(models, action);
    }

    @Override
    public Type<ModelEventHandler<Region>> getAssociatedType() {
        return TYPE;
    }
}