package org.dhamma.users.client.caches;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.dhamma.users.client.events.RegionEvent;
import org.dhamma.users.client.events.RegionEventHandler;
import org.dhamma.users.client.models.Region;
import org.dhamma.users.client.models.User;
import org.dhamma.users.client.restservices.RegionsRestService;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;

import de.mkristian.gwt.rails.caches.AbstractModelCache;
import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEvent.Action;
import de.mkristian.gwt.rails.session.SessionManager;

@Singleton
public class RegionsCache extends AbstractModelCache<Region>{

    private final RegionsRestService restService;
    
    @Inject
    RegionsCache(SessionManager<User> manager, EventBus eventBus, RegionsRestService restService) {
        super(manager, eventBus);
        this.restService = restService;
        eventBus.addHandler(RegionEvent.TYPE, new RegionEventHandler() {
            
            public void onModelEvent(ModelEvent<Region> event) {
                RegionsCache.this.onModelEvent(event);
            }
        });
    }
    
    protected void loadModels() {
        restService.index(newMethodCallback());
    }

    protected Region newModel() {
        return new Region();
    }

    @Override
    protected GwtEvent<?> newEvent(List<Region> response) {
        return new RegionEvent(response, Action.LOAD);
    }
}