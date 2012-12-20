package de.mkristian.ixtlan.users.client.activities;


import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.mkristian.gwt.rails.activities.AbstractCRUDActivitiy;
import de.mkristian.gwt.rails.events.ModelEventHandler;
import de.mkristian.gwt.rails.places.RestfulActionEnum;
import de.mkristian.gwt.rails.places.RestfulPlace;
import de.mkristian.ixtlan.users.client.events.RegionEvent;
import de.mkristian.ixtlan.users.client.models.Region;
import de.mkristian.ixtlan.users.client.places.RegionPlace;
import de.mkristian.ixtlan.users.client.presenters.RegionPresenter;

public class RegionActivity extends AbstractCRUDActivitiy<Region> {

    @Inject
    public RegionActivity( @Assisted RegionPlace place, 
                RegionPresenter presenter,
                PlaceController places ) {
        super( place, presenter, places );
    }

    protected Type<ModelEventHandler<Region>> eventType() {
        return RegionEvent.TYPE;
    }

    @Override
    protected RestfulPlace<Region, ?> showPlace( Region model ) {
        return new RegionPlace( model, RestfulActionEnum.SHOW );
    }
    
    @Override
    protected RestfulPlace<Region, ?> showAllPlace() {
        return new RegionPlace( RestfulActionEnum.INDEX );
    }
}