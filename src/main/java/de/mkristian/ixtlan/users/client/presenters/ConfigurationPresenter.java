package de.mkristian.ixtlan.users.client.presenters;

import javax.inject.Inject;


import de.mkristian.gwt.rails.presenters.SingletonPresenterImpl;
import de.mkristian.ixtlan.users.client.UsersErrorHandler;
import de.mkristian.ixtlan.users.client.caches.ConfigurationRemoteSingleton;
import de.mkristian.ixtlan.users.client.models.Configuration;
import de.mkristian.ixtlan.users.client.views.ConfigurationView;

public class ConfigurationPresenter 
            extends SingletonPresenterImpl<Configuration> {

    @Inject
    public ConfigurationPresenter( UsersErrorHandler errors,
                ConfigurationView view,
                ConfigurationRemoteSingleton remote ){
        super( errors, view, remote );
    }
}
