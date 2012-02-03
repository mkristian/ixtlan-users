package org.dhamma.users.client.views;

import java.util.List;

import org.dhamma.users.client.models.Application;
import org.dhamma.users.client.models.Group;
import org.dhamma.users.client.models.Region;
import org.dhamma.users.client.models.User;
import org.dhamma.users.client.models.UserQuery;

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

    void setup(Presenter presenter, RestfulAction action);

    void reset(List<User> models);

    void resetGroups(List<Group> groups);

    void resetApplications(List<Application> applications);

    void resetRegions(List<Region> regions);
    
    void edit(UserQuery query);
    
    void edit(User model);

    User flush();

    void removeFromList(User model);
}