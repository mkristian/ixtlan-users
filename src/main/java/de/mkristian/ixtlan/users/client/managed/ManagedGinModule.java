package de.mkristian.ixtlan.users.client.managed;


import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

import de.mkristian.gwt.rails.BaseModule;
import de.mkristian.ixtlan.users.client.activities.LoginActivity;

public class ManagedGinModule extends BaseModule {

    @Override
    protected void configure() {
        super.configure();
//        bind(de.mkristian.ixtlan.users.client.restservices.AtsRestService.class).toProvider(AtsRestServiceProvider.class);
        bind(de.mkristian.ixtlan.users.client.restservices.RegionsRestService.class).toProvider(RegionsRestServiceProvider.class);
        bind(de.mkristian.ixtlan.users.client.restservices.AuditsRestService.class).toProvider(AuditsRestServiceProvider.class);
        bind(de.mkristian.ixtlan.users.client.restservices.ErrorsRestService.class).toProvider(ErrorsRestServiceProvider.class);
        bind(de.mkristian.ixtlan.users.client.restservices.ApplicationsRestService.class).toProvider(ApplicationsRestServiceProvider.class);
        bind(de.mkristian.ixtlan.users.client.restservices.RemotePermissionsRestService.class).toProvider(RemotePermissionsRestServiceProvider.class);
        bind(de.mkristian.ixtlan.users.client.restservices.GroupsRestService.class).toProvider(GroupsRestServiceProvider.class);
        bind(de.mkristian.ixtlan.users.client.restservices.ProfileRestService.class).toProvider(ProfilesRestServiceProvider.class);
        bind(de.mkristian.ixtlan.users.client.restservices.ConfigurationRestService.class).toProvider(ConfigurationsRestServiceProvider.class);
        bind(de.mkristian.ixtlan.users.client.restservices.UsersRestService.class).toProvider(UsersRestServiceProvider.class);
//        bind(Application.class).to(UsersApplication.class);     
//        bind(PlaceHistoryMapper.class).to(UsersPlaceHistoryMapper.class).in(Singleton.class);
//        bind(ActivityMapper.class).to(SessionActivityPlaceActivityMapper.class).in(Singleton.class);
//        bind(LoginView.class).to(LoginViewImpl.class);
        install(new GinFactoryModuleBuilder()
            .implement(Activity.class, Names.named("ats"), de.mkristian.ixtlan.users.client.activities.AtActivity.class)
            .implement(Activity.class, Names.named("regions"), de.mkristian.ixtlan.users.client.activities.RegionActivity.class)
            .implement(Activity.class, Names.named("audits"), de.mkristian.ixtlan.users.client.activities.AuditActivity.class)
            .implement(Activity.class, Names.named("errors"), de.mkristian.ixtlan.users.client.activities.ErrorActivity.class)
            .implement(Activity.class, Names.named("applications"), de.mkristian.ixtlan.users.client.activities.ApplicationActivity.class)
            .implement(Activity.class, Names.named("profiles"), de.mkristian.ixtlan.users.client.activities.ProfileActivity.class)
            .implement(Activity.class, Names.named("configurations"), de.mkristian.ixtlan.users.client.activities.ConfigurationActivity.class)
            .implement(Activity.class, Names.named("users"), de.mkristian.ixtlan.users.client.activities.UserActivity.class)
            .implement(Activity.class, Names.named("login"), LoginActivity.class)
            .build(ActivityFactory.class));
    }

    @Singleton
    public static class UsersRestServiceProvider implements Provider<de.mkristian.ixtlan.users.client.restservices.UsersRestService> {
        private final de.mkristian.ixtlan.users.client.restservices.UsersRestService service = GWT.create(de.mkristian.ixtlan.users.client.restservices.UsersRestService.class);
        public de.mkristian.ixtlan.users.client.restservices.UsersRestService get() {
            return service;
        }
    }

    @Singleton
    public static class ConfigurationsRestServiceProvider implements Provider<de.mkristian.ixtlan.users.client.restservices.ConfigurationRestService> {
        private final de.mkristian.ixtlan.users.client.restservices.ConfigurationRestService service = GWT.create(de.mkristian.ixtlan.users.client.restservices.ConfigurationRestService.class);
        public de.mkristian.ixtlan.users.client.restservices.ConfigurationRestService get() {
            return service;
        }
    }

    @Singleton
    public static class ProfilesRestServiceProvider implements Provider<de.mkristian.ixtlan.users.client.restservices.ProfileRestService> {
        private final de.mkristian.ixtlan.users.client.restservices.ProfileRestService service = GWT.create(de.mkristian.ixtlan.users.client.restservices.ProfileRestService.class);
        public de.mkristian.ixtlan.users.client.restservices.ProfileRestService get() {
            return service;
        }
    }

    @Singleton
    public static class GroupsRestServiceProvider implements Provider<de.mkristian.ixtlan.users.client.restservices.GroupsRestService> {
        private final de.mkristian.ixtlan.users.client.restservices.GroupsRestService service = GWT.create(de.mkristian.ixtlan.users.client.restservices.GroupsRestService.class);
        public de.mkristian.ixtlan.users.client.restservices.GroupsRestService get() {
            return service;
        }
    }

    @Singleton
    public static class RemotePermissionsRestServiceProvider implements Provider<de.mkristian.ixtlan.users.client.restservices.RemotePermissionsRestService> {
        private final de.mkristian.ixtlan.users.client.restservices.RemotePermissionsRestService service = GWT.create(de.mkristian.ixtlan.users.client.restservices.RemotePermissionsRestService.class);
        public de.mkristian.ixtlan.users.client.restservices.RemotePermissionsRestService get() {
            return service;
        }
    }

    @Singleton
    public static class ApplicationsRestServiceProvider implements Provider<de.mkristian.ixtlan.users.client.restservices.ApplicationsRestService> {
        private final de.mkristian.ixtlan.users.client.restservices.ApplicationsRestService service = GWT.create(de.mkristian.ixtlan.users.client.restservices.ApplicationsRestService.class);
        public de.mkristian.ixtlan.users.client.restservices.ApplicationsRestService get() {
            return service;
        }
    }

    @Singleton
    public static class ErrorsRestServiceProvider implements Provider<de.mkristian.ixtlan.users.client.restservices.ErrorsRestService> {
        private final de.mkristian.ixtlan.users.client.restservices.ErrorsRestService service = GWT.create(de.mkristian.ixtlan.users.client.restservices.ErrorsRestService.class);
        public de.mkristian.ixtlan.users.client.restservices.ErrorsRestService get() {
            return service;
        }
    }

    @Singleton
    public static class AuditsRestServiceProvider implements Provider<de.mkristian.ixtlan.users.client.restservices.AuditsRestService> {
        private final de.mkristian.ixtlan.users.client.restservices.AuditsRestService service = GWT.create(de.mkristian.ixtlan.users.client.restservices.AuditsRestService.class);
        public de.mkristian.ixtlan.users.client.restservices.AuditsRestService get() {
            return service;
        }
    }

    @Singleton
    public static class RegionsRestServiceProvider implements Provider<de.mkristian.ixtlan.users.client.restservices.RegionsRestService> {
        private final de.mkristian.ixtlan.users.client.restservices.RegionsRestService service = GWT.create(de.mkristian.ixtlan.users.client.restservices.RegionsRestService.class);
        public de.mkristian.ixtlan.users.client.restservices.RegionsRestService get() {
            return service;
        }
    }
//
//    @Singleton
//    public static class AtsRestServiceProvider implements Provider<de.mkristian.ixtlan.users.client.restservices.AtsRestService> {
//        private final de.mkristian.ixtlan.users.client.restservices.AtsRestService service = GWT.create(de.mkristian.ixtlan.users.client.restservices.AtsRestService.class);
//        public de.mkristian.ixtlan.users.client.restservices.AtsRestService get() {
//            return service;
//        }
//    }
}











