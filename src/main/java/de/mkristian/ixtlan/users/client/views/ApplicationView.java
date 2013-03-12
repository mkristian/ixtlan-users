package de.mkristian.ixtlan.users.client.views;

import com.google.inject.ImplementedBy;

import de.mkristian.gwt.rails.views.CRUDView;
import de.mkristian.ixtlan.users.client.models.Application;
import de.mkristian.ixtlan.users.client.models.Group;
import de.mkristian.ixtlan.users.client.presenters.ApplicationPresenter;

@ImplementedBy(ApplicationViewImpl.class)
public interface ApplicationView extends CRUDView<Application, ApplicationPresenter> {

    void resetGroups( Application application );

    void edit( Group group );

    void reset( Group model );

    void show( Group group );
    
    boolean isGroupDirty();

    void newGroup( Group model );

    void resetNewRow(Application application);
}