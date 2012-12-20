package de.mkristian.ixtlan.users.client.activities;


import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.mkristian.gwt.rails.activities.AbstractCRUDActivitiy;
import de.mkristian.gwt.rails.events.ModelEventHandler;
import de.mkristian.gwt.rails.places.RestfulActionEnum;
import de.mkristian.gwt.rails.places.RestfulPlace;
import de.mkristian.ixtlan.users.client.events.DomainEvent;
import de.mkristian.ixtlan.users.client.models.Domain;
import de.mkristian.ixtlan.users.client.places.DomainPlace;
import de.mkristian.ixtlan.users.client.presenters.DomainPresenter;

public class DomainActivity extends AbstractCRUDActivitiy<Domain> {

    @Inject
    public DomainActivity( @Assisted DomainPlace place, 
                DomainPresenter presenter,
                PlaceController places ) {
        super( place, presenter, places );
    }

    protected Type<ModelEventHandler<Domain>> eventType() {
        return DomainEvent.TYPE;
    }

    @Override
    protected RestfulPlace<Domain, ?> showPlace( Domain model ) {
        return new DomainPlace( model, RestfulActionEnum.SHOW );
    }
    
    @Override
    protected RestfulPlace<Domain, ?> showAllPlace() {
        return new DomainPlace( RestfulActionEnum.INDEX );
    }
}