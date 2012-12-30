package de.mkristian.ixtlan.users.client.presenters;


import javax.inject.Inject;
import javax.inject.Singleton;


import de.mkristian.gwt.rails.presenters.ReadOnlyPresenterImpl;
import de.mkristian.ixtlan.gwt.audits.Audit;
import de.mkristian.ixtlan.gwt.audits.AuditRemoteReadOnly;
import de.mkristian.ixtlan.users.client.UsersErrorHandler;
import de.mkristian.ixtlan.users.client.views.AuditListView;
import de.mkristian.ixtlan.users.client.views.AuditView;

@Singleton
public class AuditPresenter extends ReadOnlyPresenterImpl<Audit> {

    @Inject
    public AuditPresenter( UsersErrorHandler errors, 
                AuditView view, 
                AuditListView listView, 
                AuditRemoteReadOnly remote ){
        super( errors, view, listView, remote );
    }
}
