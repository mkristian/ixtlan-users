/**
 * 
 */
package org.dhamma.users.client.activities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.mkristian.gwt.rails.session.SessionManager;

@Singleton
public class SimpleCache<T> extends AbstractCache<T>{
    
    private final Map<String, List<?>> cache = new HashMap<String, List<?>>();
    
    @Inject
    public SimpleCache(SessionManager<T> session) {
        super(session);
    }

    public void put(String key, List<?> models){
        cache.put(key, models);
    }
    
    @SuppressWarnings("unchecked")
    <S> List<S> get(String key){
        return (List<S>) cache.get(key);
    }

    public void remote(String key){
        cache.remove(key);
    }
    
    @Override
    public void purgeCache() {
        cache.clear();
    }  
    
    public String toString(){
        return getClass().getName().replaceFirst(".*\\.", "") + cache.keySet();
    }
}