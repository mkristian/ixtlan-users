package org.dhamma.users.client;

import org.dhamma.users.client.managed.UsersPlaceHistoryMapper;
import org.dhamma.users.client.managed.UsersModule;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;

import de.mkristian.gwt.rails.Application;
import de.mkristian.gwt.rails.Notice;
import de.mkristian.gwt.rails.dispatchers.DefaultDispatcherSingleton;

import org.fusesource.restygwt.client.Defaults;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class UsersEntryPoint implements EntryPoint {

    @GinModules(UsersModule.class)
    static public interface UsersGinjector extends Ginjector {
        PlaceController getPlaceController();
        EventBus getEventBus();
        Application getApplication();
    }

    static public class UsersApplication extends Application {
        private final Notice notice;
        private final BreadCrumbsPanel breadCrumbs;
        private RootPanel root;

        @Inject
        UsersApplication(final Notice notice,
                                           final BreadCrumbsPanel breadCrumbs,
                                           final ActivityManager activityManager){
            super(activityManager);
            this.notice = notice;
            this.breadCrumbs = breadCrumbs;
 }

        protected Panel getApplicationPanel(){
            if (this.root == null) {
                this.root = RootPanel.get();
                this.root.add(notice);
                this.root.add(breadCrumbs);
            }
            return this.root;
        }
    }

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        Defaults.setServiceRoot(GWT.getModuleBaseURL().replaceFirst("[A-Za-z0-9_]+/$", ""));
        Defaults.setDispatcher(DefaultDispatcherSingleton.INSTANCE);
        GWT.log("base url for restservices: " + Defaults.getServiceRoot());

        final UsersGinjector injector = GWT.create(UsersGinjector.class);

        injector.getApplication().run();

        // Start PlaceHistoryHandler with our PlaceHistoryMapper
        UsersPlaceHistoryMapper historyMapper = GWT.create(UsersPlaceHistoryMapper.class);

        PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
        historyHandler.register(injector.getPlaceController(), injector.getEventBus(), Place.NOWHERE);

        // Goes to the place represented on URL else default place
        historyHandler.handleCurrentHistory();
    }
}