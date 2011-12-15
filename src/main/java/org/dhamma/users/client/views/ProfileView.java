package org.dhamma.users.client.views;

import org.dhamma.users.client.models.Profile;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

import de.mkristian.gwt.rails.places.RestfulAction;

@ImplementedBy(ProfileViewImpl.class)
public interface ProfileView extends IsWidget {

    public interface Presenter {
        void save();
        void goTo(Place place);
    }

    void setup(Presenter presenter, RestfulAction action);

    void edit(Profile model);

    Profile flush();
}