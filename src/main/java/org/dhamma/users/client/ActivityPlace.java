package org.dhamma.users.client;

import org.dhamma.users.client.managed.ActivityFactory;

import com.google.gwt.activity.shared.Activity;

import de.mkristian.gwt.rails.RestfulAction;
import de.mkristian.gwt.rails.RestfulPlace;

public abstract class ActivityPlace extends RestfulPlace {

    protected ActivityPlace(RestfulAction restfulAction) {
        super(restfulAction);
    }

    protected ActivityPlace(int id, RestfulAction restfulAction) {
        super(id, restfulAction);
    }

    protected ActivityPlace(String id, RestfulAction restfulAction) {
        super(id, restfulAction);
    }

    public abstract Activity create(ActivityFactory factory);
}