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
        void load(String query);
        void save();
        void delete(User model);
        void goTo(Place place);
    }

    void setup(Presenter presenter, RestfulAction action);

    void reset(List<User> models);

    void resetGroups(List<Group> groups);

    void edit(User model);

    User flush();

    void removeFromList(User model);
}