package de.mkristian.ixtlan.users.client.activities;


import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.mkristian.gwt.rails.activities.AbstractSingletonActivity;
import de.mkristian.gwt.rails.events.ModelEventHandler;
import de.mkristian.gwt.rails.places.RestfulActionEnum;
import de.mkristian.gwt.rails.places.RestfulPlace;
import de.mkristian.ixtlan.users.client.events.ConfigurationEvent;
import de.mkristian.ixtlan.users.client.models.Configuration;
import de.mkristian.ixtlan.users.client.places.ConfigurationPlace;
import de.mkristian.ixtlan.users.client.presenters.ConfigurationPresenter;

public class ConfigurationActivity extends AbstractSingletonActivity<Configuration> {

    @Inject
    public ConfigurationActivity( @Assisted ConfigurationPlace place,
                ConfigurationPresenter presenter,
                PlaceController places ) {
        super( place, presenter, places );
    }

    @Override
    protected Type<ModelEventHandler<Configuration>> eventType() {
        return ConfigurationEvent.TYPE;
    }

    @Override
    protected RestfulPlace<Configuration, ?> showPlace(Configuration model) {
        return new ConfigurationPlace( model, RestfulActionEnum.SHOW );
    }
}
