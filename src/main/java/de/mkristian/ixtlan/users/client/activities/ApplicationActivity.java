package de.mkristian.ixtlan.users.client.activities;


import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.mkristian.gwt.rails.activities.AbstractCRUDActivitiy;
import de.mkristian.gwt.rails.events.ModelEventHandler;
import de.mkristian.gwt.rails.places.RestfulActionEnum;
import de.mkristian.gwt.rails.places.RestfulPlace;
import de.mkristian.ixtlan.users.client.events.ApplicationEvent;
import de.mkristian.ixtlan.users.client.models.Application;
import de.mkristian.ixtlan.users.client.places.ApplicationPlace;
import de.mkristian.ixtlan.users.client.presenters.ApplicationPresenter;

public class ApplicationActivity extends AbstractCRUDActivitiy<Application> {

    @Inject
    public ApplicationActivity( @Assisted ApplicationPlace place, 
                ApplicationPresenter presenter,
                PlaceController places ) {
        super( place, presenter, places );
    }

    protected Type<ModelEventHandler<Application>> eventType() {
        return ApplicationEvent.TYPE;
    }

    @Override
    protected RestfulPlace<Application, ?> showPlace( Application model ) {
        return new ApplicationPlace( model, RestfulActionEnum.SHOW );
    }
    
    @Override
    protected RestfulPlace<Application, ?> showAllPlace() {
        return new ApplicationPlace( RestfulActionEnum.INDEX );
    }
}