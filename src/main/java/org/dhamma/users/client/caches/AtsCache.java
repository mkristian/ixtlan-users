package org.dhamma.users.client.caches;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.dhamma.users.client.events.AtEvent;
import org.dhamma.users.client.events.AtEventHandler;
import org.dhamma.users.client.models.At;
import org.dhamma.users.client.models.User;
import org.dhamma.users.client.restservices.AtsRestService;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;

import de.mkristian.gwt.rails.caches.AbstractModelCache;
import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEvent.Action;
import de.mkristian.gwt.rails.session.SessionManager;

@Singleton
public class AtsCache extends AbstractModelCache<At>{

    private final AtsRestService restService;
    
    @Inject
    AtsCache(SessionManager<User> manager, EventBus eventBus, AtsRestService restService) {
        super(manager, eventBus);
        this.restService = restService;
        eventBus.addHandler(AtEvent.TYPE, new AtEventHandler() {
            
            public void onModelEvent(ModelEvent<At> event) {
                AtsCache.this.onModelEvent(event);
            }
        });
    }
    
    protected void loadModels() {
        restService.index(newMethodCallback());
    }

    protected At newModel() {
        return new At();
    }

    @Override
    protected GwtEvent<?> newEvent(List<At> response) {
        return new AtEvent(response, Action.LOAD);
    }
}