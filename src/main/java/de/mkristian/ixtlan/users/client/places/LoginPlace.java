package de.mkristian.ixtlan.users.client.places;


import com.google.gwt.activity.shared.Activity;

import de.mkristian.gwt.rails.places.RestfulPlace;
import de.mkristian.ixtlan.users.client.managed.ActivityFactory;

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
