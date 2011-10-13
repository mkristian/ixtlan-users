package org.dhamma.users.client.views;

import java.util.List;

import org.dhamma.users.client.models.Application;
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

    void edit(Configuration model);

    Configuration flush();

    void reset(RestfulAction action);
    
    void setEnabled(boolean enabled);

    void resetApplications(List<Application> list);
}