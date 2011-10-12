package org.dhamma.users.client.views;

import java.util.List;

import org.dhamma.users.client.models.Group;
import org.dhamma.users.client.models.User;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

import de.mkristian.gwt.rails.places.RestfulAction;

@ImplementedBy(UserViewImpl.class)
public interface UserView extends IsWidget {

    public interface Presenter {
        void create();
        
        void save();
        void delete(User model);
        void goTo(Place place);
    }
    void setPresenter(Presenter presenter);

    void edit(User model);

    User flush();

    void reset(List<User> models);

    void reset(RestfulAction action);
    
    void setEnabled(boolean enabled);

    void updateInList(User model);

    void removeFromList(User model);

    void addToList(User model);

    void resetGroups(List<Group> groups);
}