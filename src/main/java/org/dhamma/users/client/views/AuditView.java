package org.dhamma.users.client.views;

import java.util.List;

import org.dhamma.users.client.models.Audit;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

import de.mkristian.gwt.rails.places.RestfulAction;

@ImplementedBy(AuditViewImpl.class)
public interface AuditView extends IsWidget {

    public interface Presenter {
        void goTo(Place place);
    }

    void setup(Presenter presenter, RestfulAction action);

    void reset(List<Audit> models);

    void edit(Audit model);
}