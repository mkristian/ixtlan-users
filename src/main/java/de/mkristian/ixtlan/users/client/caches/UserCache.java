package de.mkristian.ixtlan.users.client.caches;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;

import de.mkristian.gwt.rails.caches.AbstractCache;
import de.mkristian.gwt.rails.caches.MemoryStore;
import de.mkristian.gwt.rails.events.ModelEventHandler;
import de.mkristian.ixtlan.users.client.events.UserEvent;
import de.mkristian.ixtlan.users.client.models.User;

@Singleton
public class UserCache extends AbstractCache<User>{

    @Inject
    UserCache( EventBus eventBus, UserRemoteModel remote ) {
        super( eventBus, new MemoryStore<User>(), remote );
    }

    @Override
    protected Type<ModelEventHandler<User>> eventType() {
        return UserEvent.TYPE;
    }

    public List<User> getOrLoadAts(){
        List<User> result = super.getOrLoadModels();
        if (result != null){
            Iterator<User> iter = result.iterator();
            while(iter.hasNext()){
                if (!iter.next().isAt()){
                    iter.remove();
                }
            }
        }
        return result;
    }
    
    public User otherAt(User at){
        List<User> ats = getOrLoadAts();
        if (ats != null && at != null && at.getAtToken() != null){
            for(User secondAt : ats){
                if (at.getId() != secondAt.getId() && at.getAtToken().equals(secondAt.getAtToken())){
                    return secondAt;
                }
            }
            return new User();
        }
        return null;
    }
}