package org.dhamma.users.client;

import org.dhamma.users.client.managed.UsersMenuPanel;
import org.dhamma.users.client.managed.UsersModule;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
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
        PlaceHistoryHandler getPlaceHistoryHandler();
        Application getApplication();
    }

    static public class UsersApplication extends Application {
        private final Notice notice;
        private final BreadCrumbsPanel breadCrumbs;
        private final UsersMenuPanel menu;
        private RootPanel root;

        @Inject
        UsersApplication(final Notice notice,
                                           final BreadCrumbsPanel breadCrumbs,
                                           final UsersMenuPanel menu,
                                           final ActivityManager activityManager){
            super(activityManager);
            this.notice = notice;
            this.breadCrumbs = breadCrumbs;
	    this.menu = menu;
        }

        protected Panel getApplicationPanel(){
            if (this.root == null) {
                this.root = RootPanel.get();
                this.root.add(notice);
                this.root.add(breadCrumbs);
                this.root.add(menu);
            }
            return this.root;
        }
    }

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        Defaults.setServiceRoot(GWT.getModuleBaseURL().replaceFirst("[a-zA-Z0-9_]+/$", ""));
        Defaults.setDispatcher(DefaultDispatcherSingleton.INSTANCE);
        GWT.log("base url for restservices: " + Defaults.getServiceRoot());

        final UsersGinjector injector = GWT.create(UsersGinjector.class);

        // setup display
        injector.getApplication().run();
     
        // Goes to the place represented on URL else default place
        injector.getPlaceHistoryHandler().handleCurrentHistory();
    }
}
