package org.dhamma.users.client.caches;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.dhamma.users.client.events.ApplicationEvent;
import org.dhamma.users.client.events.ApplicationEventHandler;
import org.dhamma.users.client.models.Application;
import org.dhamma.users.client.models.User;
import org.dhamma.users.client.restservices.ApplicationsRestService;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;

import de.mkristian.gwt.rails.caches.AbstractModelCache;
import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEvent.Action;
import de.mkristian.gwt.rails.session.SessionManager;

@Singleton
public class ApplicationsCache extends AbstractModelCache<Application>{

    private final ApplicationsRestService restService;
    
    @Inject
    ApplicationsCache(SessionManager<User> manager, EventBus eventBus, ApplicationsRestService restService) {
        super(manager, eventBus);
        this.restService = restService;
        eventBus.addHandler(ApplicationEvent.TYPE, new ApplicationEventHandler() {
            
            public void onModelEvent(ModelEvent<Application> event) {
                ApplicationsCache.this.onModelEvent(event);
            }
        });
    }
    
    protected void loadModels() {
        restService.index(newMethodCallback());
    }

    protected Application newModel() {
        return new Application();
    }

    @Override
    protected GwtEvent<?> newEvent(List<Application> response) {
        return new ApplicationEvent(response, Action.LOAD);
    }
}