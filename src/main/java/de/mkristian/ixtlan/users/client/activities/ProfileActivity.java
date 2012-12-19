package de.mkristian.ixtlan.users.client.activities;


import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.mkristian.gwt.rails.activities.AbstractSingletonActivity;
import de.mkristian.gwt.rails.events.ModelEventHandler;
import de.mkristian.gwt.rails.places.RestfulActionEnum;
import de.mkristian.gwt.rails.places.RestfulPlace;
import de.mkristian.ixtlan.users.client.events.ProfileEvent;
import de.mkristian.ixtlan.users.client.models.Profile;
import de.mkristian.ixtlan.users.client.places.ProfilePlace;
import de.mkristian.ixtlan.users.client.presenters.ProfilePresenter;

public class ProfileActivity extends AbstractSingletonActivity<Profile> {
    
    @Inject
    public ProfileActivity( @Assisted ProfilePlace place, 
                ProfilePresenter presenter,
                PlaceController places ) {
        super( place, presenter, places );
    }

    protected Type<ModelEventHandler<Profile>> eventType() {
        return ProfileEvent.TYPE;
    }

    //@Override
    protected RestfulPlace<Profile, ?> showPlace(Profile model) {
        return new ProfilePlace( model, RestfulActionEnum.SHOW );
    }
}