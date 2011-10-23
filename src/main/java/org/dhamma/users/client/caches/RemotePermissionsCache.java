package org.dhamma.users.client.caches;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.dhamma.users.client.events.RemotePermissionEvent;
import org.dhamma.users.client.events.RemotePermissionEventHandler;
import org.dhamma.users.client.models.RemotePermission;
import org.dhamma.users.client.models.User;
import org.dhamma.users.client.restservices.RemotePermissionsRestService;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;

import de.mkristian.gwt.rails.caches.AbstractModelCache;
import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEvent.Action;
import de.mkristian.gwt.rails.session.SessionManager;

@Singleton
public class RemotePermissionsCache extends AbstractModelCache<RemotePermission>{

    private final RemotePermissionsRestService restService;
    
    @Inject
    RemotePermissionsCache(SessionManager<User> manager, EventBus eventBus, RemotePermissionsRestService restService) {
        super(manager, eventBus);
        this.restService = restService;
        eventBus.addHandler(RemotePermissionEvent.TYPE, new RemotePermissionEventHandler() {
            
            public void onModelEvent(ModelEvent<RemotePermission> event) {
                RemotePermissionsCache.this.onModelEvent(event);
            }
        });
    }
    
    protected void loadModels() {
        restService.index(newMethodCallback());
    }

    protected RemotePermission newModel() {
        return new RemotePermission();
    }

    @Override
    protected GwtEvent<?> newEvent(List<RemotePermission> response) {
        return new RemotePermissionEvent(response, Action.LOAD);
    }
}