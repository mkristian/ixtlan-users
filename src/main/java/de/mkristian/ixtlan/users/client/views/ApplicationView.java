package de.mkristian.ixtlan.users.client.views;

import java.util.List;


import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.ixtlan.users.client.models.Application;

@ImplementedBy(ApplicationViewImpl.class)
public interface ApplicationView extends IsWidget {

    public interface Presenter {
        void create();
        void save();
        void delete(Application model);
        void goTo(Place place);
    }

    void setup(Presenter presenter, RestfulAction action);

    void reset(List<Application> models);

    void edit(Application model);

    Application flush();

    void removeFromList(Application model);
}