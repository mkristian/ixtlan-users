package org.dhamma.users.client.views;

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
        void delete();
        void goTo(Place place);
    }
    void setPresenter(Presenter presenter);

    void reset(User model);

    void reset(RestfulAction action);
    
    void setEnabled(boolean enabled);

    User retrieveUser();
}