package org.dhamma.users.client.managed;

import org.dhamma.users.client.UsersEntryPoint.UsersApplication;
import org.dhamma.users.client.SessionActivityPlaceActivityMapper;
import org.dhamma.users.client.activities.LoginActivity;
import de.mkristian.gwt.rails.Application;
import de.mkristian.gwt.rails.BaseModule;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

import org.dhamma.users.client.views.LoginViewImpl;

import de.mkristian.gwt.rails.session.LoginView;
public class UsersModule extends BaseModule {

    @Override
    protected void configure() {
        super.configure();
        bind(Application.class).to(UsersApplication.class);
        bind(ActivityMapper.class).to(SessionActivityPlaceActivityMapper.class).in(Singleton.class);
        bind(LoginView.class).to(LoginViewImpl.class);
        install(new GinFactoryModuleBuilder()
		.implement(Activity.class, Names.named("login"), LoginActivity.class)
            .build(ActivityFactory.class));
    }
}