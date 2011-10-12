package org.dhamma.users.client.views;

import java.util.List;

import org.dhamma.users.client.models.RemotePermission;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

import de.mkristian.gwt.rails.places.RestfulAction;

@ImplementedBy(RemotePermissionViewImpl.class)
public interface RemotePermissionView extends IsWidget {

    public interface Presenter {
        void create();
        
        void save();
        void delete(RemotePermission model);
        void goTo(Place place);
    }
    void setPresenter(Presenter presenter);

    void edit(RemotePermission model);

    RemotePermission flush();

    void reset(List<RemotePermission> models);

    void reset(RestfulAction action);
    
    void setEnabled(boolean enabled);

    void updateInList(RemotePermission model);

    void removeFromList(RemotePermission model);

    void addToList(RemotePermission model);
}