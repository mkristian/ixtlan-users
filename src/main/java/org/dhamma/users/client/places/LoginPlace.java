package org.dhamma.users.client.places;

import org.dhamma.users.client.managed.ActivityFactory;

import com.google.gwt.activity.shared.Activity;

import de.mkristian.gwt.rails.places.RestfulPlace;

public class LoginPlace extends RestfulPlace<Void, ActivityFactory> {

    public static final LoginPlace LOGIN = new LoginPlace();

    private LoginPlace() {
        super(null, null);
    }

    @Override
    public Activity create(ActivityFactory factory) {
        return factory.create(this);
    }
}
