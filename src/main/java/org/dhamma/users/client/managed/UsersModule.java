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
        bind(org.dhamma.users.client.restservices.AtsRestService.class).toProvider(AtsRestServiceProvider.class);
        bind(org.dhamma.users.client.restservices.RegionsRestService.class).toProvider(RegionsRestServiceProvider.class);
        bind(org.dhamma.users.client.restservices.AuditsRestService.class).toProvider(AuditsRestServiceProvider.class);
        bind(org.dhamma.users.client.restservices.ErrorsRestService.class).toProvider(ErrorsRestServiceProvider.class);
        bind(org.dhamma.users.client.restservices.ApplicationsRestService.class).toProvider(ApplicationsRestServiceProvider.class);
        bind(org.dhamma.users.client.restservices.RemotePermissionsRestService.class).toProvider(RemotePermissionsRestServiceProvider.class);
        bind(org.dhamma.users.client.restservices.GroupsRestService.class).toProvider(GroupsRestServiceProvider.class);
        bind(org.dhamma.users.client.restservices.ProfilesRestService.class).toProvider(ProfilesRestServiceProvider.class);
        bind(org.dhamma.users.client.restservices.ConfigurationsRestService.class).toProvider(ConfigurationsRestServiceProvider.class);
        bind(org.dhamma.users.client.restservices.UsersRestService.class).toProvider(UsersRestServiceProvider.class);
        bind(Application.class).to(UsersApplication.class);     
        bind(PlaceHistoryMapper.class).to(UsersPlaceHistoryMapper.class).in(Singleton.class);
        bind(ActivityMapper.class).to(SessionActivityPlaceActivityMapper.class).in(Singleton.class);
        bind(LoginView.class).to(LoginViewImpl.class);
        install(new GinFactoryModuleBuilder()
            .implement(Activity.class, Names.named("ats"), org.dhamma.users.client.activities.AtActivity.class)
            .implement(Activity.class, Names.named("ats"), org.dhamma.users.client.activities.AtActivity.class)
            .implement(Activity.class, Names.named("regions"), org.dhamma.users.client.activities.RegionActivity.class)
            .implement(Activity.class, Names.named("audits"), org.dhamma.users.client.activities.AuditActivity.class)
            .implement(Activity.class, Names.named("errors"), org.dhamma.users.client.activities.ErrorActivity.class)
            .implement(Activity.class, Names.named("applications"), org.dhamma.users.client.activities.ApplicationActivity.class)
            .implement(Activity.class, Names.named("remote_permissions"), org.dhamma.users.client.activities.RemotePermissionActivity.class)
            .implement(Activity.class, Names.named("groups"), org.dhamma.users.client.activities.GroupActivity.class)
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

    @Singleton
    public static class GroupsRestServiceProvider implements Provider<org.dhamma.users.client.restservices.GroupsRestService> {
        private final org.dhamma.users.client.restservices.GroupsRestService service = GWT.create(org.dhamma.users.client.restservices.GroupsRestService.class);
        public org.dhamma.users.client.restservices.GroupsRestService get() {
            return service;
        }
    }

    @Singleton
    public static class RemotePermissionsRestServiceProvider implements Provider<org.dhamma.users.client.restservices.RemotePermissionsRestService> {
        private final org.dhamma.users.client.restservices.RemotePermissionsRestService service = GWT.create(org.dhamma.users.client.restservices.RemotePermissionsRestService.class);
        public org.dhamma.users.client.restservices.RemotePermissionsRestService get() {
            return service;
        }
    }

    @Singleton
    public static class ApplicationsRestServiceProvider implements Provider<org.dhamma.users.client.restservices.ApplicationsRestService> {
        private final org.dhamma.users.client.restservices.ApplicationsRestService service = GWT.create(org.dhamma.users.client.restservices.ApplicationsRestService.class);
        public org.dhamma.users.client.restservices.ApplicationsRestService get() {
            return service;
        }
    }

    @Singleton
    public static class ErrorsRestServiceProvider implements Provider<org.dhamma.users.client.restservices.ErrorsRestService> {
        private final org.dhamma.users.client.restservices.ErrorsRestService service = GWT.create(org.dhamma.users.client.restservices.ErrorsRestService.class);
        public org.dhamma.users.client.restservices.ErrorsRestService get() {
            return service;
        }
    }

    @Singleton
    public static class AuditsRestServiceProvider implements Provider<org.dhamma.users.client.restservices.AuditsRestService> {
        private final org.dhamma.users.client.restservices.AuditsRestService service = GWT.create(org.dhamma.users.client.restservices.AuditsRestService.class);
        public org.dhamma.users.client.restservices.AuditsRestService get() {
            return service;
        }
    }

    @Singleton
    public static class RegionsRestServiceProvider implements Provider<org.dhamma.users.client.restservices.RegionsRestService> {
        private final org.dhamma.users.client.restservices.RegionsRestService service = GWT.create(org.dhamma.users.client.restservices.RegionsRestService.class);
        public org.dhamma.users.client.restservices.RegionsRestService get() {
            return service;
        }
    }

    @Singleton
    public static class AtsRestServiceProvider implements Provider<org.dhamma.users.client.restservices.AtsRestService> {
        private final org.dhamma.users.client.restservices.AtsRestService service = GWT.create(org.dhamma.users.client.restservices.AtsRestService.class);
        public org.dhamma.users.client.restservices.AtsRestService get() {
            return service;
        }
    }
}











