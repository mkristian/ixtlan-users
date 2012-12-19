package de.mkristian.ixtlan.users.client.views;

import java.util.List;


import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.ixtlan.users.client.models.User;
import de.mkristian.ixtlan.users.client.models.UserQuery;

@ImplementedBy(AtViewImpl.class)
public interface AtView extends IsWidget {

    public interface Presenter {
        void goTo(Place place);
    }

    void setup(Presenter presenter, RestfulAction action);

    void reset(List<User> models);

    void edit(User model);

    void edit(UserQuery query);
    
    void setupOther(User user);
}