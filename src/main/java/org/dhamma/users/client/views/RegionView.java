package org.dhamma.users.client.views;

import java.util.List;

import org.dhamma.users.client.models.Region;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

import de.mkristian.gwt.rails.places.RestfulAction;

@ImplementedBy(RegionViewImpl.class)
public interface RegionView extends IsWidget {

    public interface Presenter {
        void create();
        void save();
        void delete(Region model);
        void goTo(Place place);
    }

    void setup(Presenter presenter, RestfulAction action);

    void reset(List<Region> models);

    void edit(Region model);

    Region flush();

    void removeFromList(Region model);
}