package de.mkristian.ixtlan.users.client.places;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.SingletonRestfulPlaceTokenizer;

public class ConfigurationPlaceTokenizer extends SingletonRestfulPlaceTokenizer<ConfigurationPlace> {
    
    @Override
    protected ConfigurationPlace newRestfulPlace( RestfulAction action ) {
        return new ConfigurationPlace( action );
    }
}
