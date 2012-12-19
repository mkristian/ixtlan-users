package de.mkristian.ixtlan.users.client.places;


import com.google.gwt.activity.shared.Activity;

import de.mkristian.gwt.rails.places.QueryableRestfulPlace;
import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulActionEnum;
import de.mkristian.ixtlan.users.client.managed.ActivityFactory;
import de.mkristian.ixtlan.users.client.models.User;

public class UserPlace extends QueryableRestfulPlace<User, ActivityFactory> {
    
    public static final String NAME = "users";

    public Activity create(ActivityFactory factory){
        return factory.create(this);
    }

    public UserPlace(String query) {
        super(RestfulActionEnum.INDEX, NAME, query);
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

    public UserPlace(int id, RestfulAction action, String query) {
        super( id, action, NAME, query );
    }

    public UserPlace(RestfulAction action, String query) {
        super( action, NAME, query );
    }
}