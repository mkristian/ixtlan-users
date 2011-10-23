package org.dhamma.users.client.caches;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.dhamma.users.client.events.GroupEvent;
import org.dhamma.users.client.events.GroupEventHandler;
import org.dhamma.users.client.models.Group;
import org.dhamma.users.client.models.User;
import org.dhamma.users.client.restservices.GroupsRestService;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;

import de.mkristian.gwt.rails.caches.AbstractModelCache;
import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEvent.Action;
import de.mkristian.gwt.rails.session.SessionManager;

@Singleton
public class GroupsCache extends AbstractModelCache<Group>{

    private final GroupsRestService restService;
    
    @Inject
    GroupsCache(SessionManager<User> manager, EventBus eventBus, GroupsRestService restService) {
        super(manager, eventBus);
        this.restService = restService;
        eventBus.addHandler(GroupEvent.TYPE, new GroupEventHandler() {
            
            public void onModelEvent(ModelEvent<Group> event) {
                GroupsCache.this.onModelEvent(event);
            }
        });
    }
    
    protected void loadModels() {
        restService.index(newMethodCallback());
    }

    protected Group newModel() {
        return new Group();
    }

    @Override
    protected GwtEvent<?> newEvent(List<Group> response) {
        return new GroupEvent(response, Action.LOAD);
    }
}