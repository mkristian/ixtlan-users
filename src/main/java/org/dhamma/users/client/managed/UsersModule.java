package org.dhamma.users.client.managed;

import org.dhamma.users.client.SessionActivityPlaceActivityMapper;
import org.dhamma.users.client.UsersEntryPoint.UsersApplication;
import org.dhamma.users.client.activities.LoginActivity;
import org.dhamma.users.client.views.LoginViewImpl;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

import de.mkristian.gwt.rails.Application;
import de.mkristian.gwt.rails.BaseModule;
import de.mkristian.gwt.rails.session.LoginView;
public class UsersModule extends BaseModule {

    @Override
    protected void configure() {
        super.configure();
        bind(org.dhamma.users.client.restservices.ProfilesRestService.class).toProvider(ProfilesRestServiceProvider.class);
        bind(org.dhamma.users.client.restservices.ConfigurationsRestService.class).toProvider(ConfigurationsRestServiceProvider.class);
        bind(org.dhamma.users.client.restservices.UsersRestService.class).toProvider(UsersRestServiceProvider.class);
        bind(Application.class).to(UsersApplication.class);     
        bind(PlaceHistoryMapper.class).to(UsersPlaceHistoryMapper.class).in(Singleton.class);
        bind(ActivityMapper.class).to(SessionActivityPlaceActivityMapper.class).in(Singleton.class);
        bind(LoginView.class).to(LoginViewImpl.class);
        install(new GinFactoryModuleBuilder()
            .implement(Activity.class, Names.named("profiles"), org.dhamma.users.client.activities.ProfileActivity.class)
            .implement(Activity.class, Names.named("configurations"), org.dhamma.users.client.activities.ConfigurationActivity.class)
            .implement(Activity.class, Names.named("users"), org.dhamma.users.client.activities.UserActivity.class)
		.implement(Activity.class, Names.named("login"), LoginActivity.class)
            .build(ActivityFactory.class));
    }

    @Singleton
    public static class UsersRestServiceProvider implements Provider<org.dhamma.users.client.restservices.UsersRestService> {
        private final org.dhamma.users.client.restservices.UsersRestService service = GWT.create(org.dhamma.users.client.restservices.UsersRestService.class);
        public org.dhamma.users.client.restservices.UsersRestService get() {
            return service;
        }
    }

    @Singleton
    public static class ConfigurationsRestServiceProvider implements Provider<org.dhamma.users.client.restservices.ConfigurationsRestService> {
        private final org.dhamma.users.client.restservices.ConfigurationsRestService service = GWT.create(org.dhamma.users.client.restservices.ConfigurationsRestService.class);
        public org.dhamma.users.client.restservices.ConfigurationsRestService get() {
            return service;
        }
    }

    @Singleton
    public static class ProfilesRestServiceProvider implements Provider<org.dhamma.users.client.restservices.ProfilesRestService> {
        private final org.dhamma.users.client.restservices.ProfilesRestService service = GWT.create(org.dhamma.users.client.restservices.ProfilesRestService.class);
        public org.dhamma.users.client.restservices.ProfilesRestService get() {
            return service;
        }
    }
}


