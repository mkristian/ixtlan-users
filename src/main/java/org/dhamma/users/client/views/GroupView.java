package org.dhamma.users.client.views;

import java.util.List;

import org.dhamma.users.client.models.Application;
import org.dhamma.users.client.models.Group;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

import de.mkristian.gwt.rails.places.RestfulAction;

@ImplementedBy(GroupViewImpl.class)
public interface GroupView extends IsWidget {

    public interface Presenter {
        void create();
        void save();
        void delete(Group model);
        void goTo(Place place);
    }

    void setup(Presenter presenter, RestfulAction action);

    void reset(List<Group> models);

    void resetApplications(List<Application> list);

    void edit(Group model);

    Group flush();

    void removeFromList(Group model);
}