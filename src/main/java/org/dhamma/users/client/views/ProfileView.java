package org.dhamma.users.client.views;

import org.dhamma.users.client.models.Profile;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

import de.mkristian.gwt.rails.RestfulAction;

@ImplementedBy(ProfileViewImpl.class)
public interface ProfileView extends IsWidget {

    public interface Presenter {
        
        void save();
        void goTo(Place place);
    }
    void setPresenter(Presenter presenter);

    void reset(Profile model);

    void reset(RestfulAction action);
    
    void setEnabled(boolean enabled);

    Profile retrieveProfile();
}