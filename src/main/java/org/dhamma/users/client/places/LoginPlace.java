package org.dhamma.users.client.places;

import org.dhamma.users.client.ActivityPlace;
import org.dhamma.users.client.managed.ActivityFactory;

import com.google.gwt.activity.shared.Activity;

public class LoginPlace extends ActivityPlace {

    public static final LoginPlace LOGIN = new LoginPlace();

    private LoginPlace() {
        super(null);
    }

    @Override
    public Activity create(ActivityFactory factory) {
        return factory.create(this);
    }
}