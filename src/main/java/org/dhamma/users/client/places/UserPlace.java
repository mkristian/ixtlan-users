package org.dhamma.users.client.places;

import org.dhamma.users.client.managed.ActivityFactory;
import org.dhamma.users.client.models.User;

import com.google.gwt.activity.shared.Activity;

import de.mkristian.gwt.rails.places.QueryableRestfulPlace;
import de.mkristian.gwt.rails.places.RestfulAction;

public class UserPlace extends QueryableRestfulPlace<User, ActivityFactory> {
    
    public static final String NAME = "users";
    
    public Activity create(ActivityFactory factory){
        return factory.create(this);
    }

    public UserPlace(String query) {
        super(query, NAME);
    }

    public UserPlace(RestfulAction restfulAction) {
        super(restfulAction, NAME);
    }

    public UserPlace(User model, RestfulAction restfulAction) {
        super(model.getId(), model, restfulAction, NAME);
    }

    public UserPlace(int id, RestfulAction restfulAction) {
        super(id, restfulAction, NAME);
    }    
    
    public UserPlace(String id, RestfulAction restfulAction) {
        super(id, restfulAction, NAME);
    }
    
    
    public int hashCode(){
        return super.hashCode() + (query != null ? 17 * query.hashCode() : 0);
    }
    
    public boolean equals(Object other){
        boolean result = super.equals(other);
        if(result){
            UserPlace place = (UserPlace) other;
            return (query == null && place.query == null) || (query != null && query.equals(place.query)); 
        }
        else {
            return result;
        }
    }
}