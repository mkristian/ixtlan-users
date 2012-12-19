package de.mkristian.ixtlan.users.client.presenters;

import javax.inject.Inject;
import javax.inject.Singleton;


import de.mkristian.gwt.rails.presenters.ReadOnlyPresenterImpl;
import de.mkristian.ixtlan.users.client.UsersErrorHandler;
import de.mkristian.ixtlan.users.client.caches.ErrorRemoteReadOnly;
import de.mkristian.ixtlan.users.client.models.Error;
import de.mkristian.ixtlan.users.client.views.ErrorListView;
import de.mkristian.ixtlan.users.client.views.ErrorView;

@Singleton
public class ErrorPresenter extends ReadOnlyPresenterImpl<Error> {

    @Inject
    public ErrorPresenter( UsersErrorHandler errors, 
                ErrorView view, 
                ErrorListView listView, 
                ErrorRemoteReadOnly remote ){
        super( errors, view, listView, remote );
    }
}
