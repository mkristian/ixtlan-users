package de.mkristian.ixtlan.users.client.activities;

import javax.inject.Inject;


import com.google.inject.assistedinject.Assisted;

import de.mkristian.gwt.rails.activities.AbstractLoginActivity;
import de.mkristian.gwt.rails.session.LoginPresenter;
import de.mkristian.gwt.rails.session.LoginView;
import de.mkristian.ixtlan.users.client.places.LoginPlace;

public class LoginActivity extends AbstractLoginActivity {

    @Inject
    public LoginActivity( @Assisted LoginPlace place,
            LoginView view,
            LoginPresenter presenter ) {
        super( view, presenter );
    }
}