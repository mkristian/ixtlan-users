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
    void setPresenter(Presenter presenter);

    void edit(Group model);

    Group flush();

    void reset(List<Group> models);

    void reset(RestfulAction action);
    
    void setEnabled(boolean enabled);

    void updateInList(Group model);

    void removeFromList(Group model);

    void addToList(Group model);

    void resetApplications(List<Application> list);
}