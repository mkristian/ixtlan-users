package org.dhamma.users.client;

import org.dhamma.users.client.managed.ActivityFactory;

import com.google.gwt.activity.shared.Activity;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulPlace;

public abstract class ActivityPlace<T> extends RestfulPlace<T> {

    protected ActivityPlace(RestfulAction restfulAction, String name) {
        super(restfulAction, name);
    }

    protected ActivityPlace(T model, RestfulAction restfulAction, String name) {
        super(model, restfulAction, name);
    }

    protected ActivityPlace(int id, RestfulAction restfulAction, String name) {
        super(id, restfulAction, name);
    }

    protected ActivityPlace(String id, RestfulAction restfulAction, String name) {
        super(id, restfulAction, name);
    }

    public abstract Activity create(ActivityFactory factory);
}
