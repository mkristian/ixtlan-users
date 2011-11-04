package org.dhamma.users.client.events;

import java.util.List;

import org.dhamma.users.client.models.Audit;

import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEventHandler;

public class AuditEvent extends ModelEvent<Audit> {

    public static final Type<ModelEventHandler<Audit>> TYPE = new Type<ModelEventHandler<Audit>>();
    
    public AuditEvent(Audit model, ModelEvent.Action action) {
        super(model, action);
    }

    public AuditEvent(List<Audit> models, ModelEvent.Action action) {
        super(models, action);
    }

    @Override
    public Type<ModelEventHandler<Audit>> getAssociatedType() {
        return TYPE;
    }
}