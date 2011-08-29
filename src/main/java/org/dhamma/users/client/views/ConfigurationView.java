package org.dhamma.users.client.views;

import org.dhamma.users.client.models.Configuration;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

import de.mkristian.gwt.rails.places.RestfulAction;

@ImplementedBy(ConfigurationViewImpl.class)
public interface ConfigurationView extends IsWidget {

    public interface Presenter {
        
        void save();
        void goTo(Place place);
    }
    void setPresenter(Presenter presenter);

    void reset(Configuration model);

    void reset(RestfulAction action);
    
    void setEnabled(boolean enabled);

    Configuration retrieveConfiguration();
}