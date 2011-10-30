package org.dhamma.users.client.caches;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.dhamma.users.client.events.UserEvent;
import org.dhamma.users.client.events.UserEventHandler;
import org.dhamma.users.client.models.User;
import org.dhamma.users.client.restservices.UsersRestService;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;

import de.mkristian.gwt.rails.caches.AbstractModelCache;
import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEvent.Action;
import de.mkristian.gwt.rails.session.SessionManager;

@Singleton
public class UsersCache extends AbstractModelCache<User>{

    private final UsersRestService restService;
    
    @Inject
    UsersCache(SessionManager<User> manager, EventBus eventBus, UsersRestService restService) {
        super(manager, eventBus);
        this.restService = restService;
        eventBus.addHandler(UserEvent.TYPE, new UserEventHandler() {
            
            public void onModelEvent(ModelEvent<User> event) {
                UsersCache.this.onModelEvent(event);
            }
        });
    }
    
    protected void loadModels() {
        restService.index(newMethodCallback());
    }

    protected User newModel() {
        return new User();
    }

    @Override
    protected GwtEvent<?> newEvent(List<User> response) {
        return new UserEvent(response, Action.LOAD);
    }
}