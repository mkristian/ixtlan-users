package org.dhamma.users.client.views;

import java.util.List;

import org.dhamma.users.client.models.Application;
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

    void setup(Presenter presenter, RestfulAction action);

    void reset(List<RemotePermission> models);

    void resetApplications(List<Application> list);

    void edit(RemotePermission model);

    RemotePermission flush();

    void removeFromList(RemotePermission model);
}