package org.dhamma.users.client.places;

import de.mkristian.gwt.rails.places.RestfulPlaceTokenizer;

public class ConfigurationPlaceTokenizer extends RestfulPlaceTokenizer<ConfigurationPlace> {
    
    public ConfigurationPlace getPlace(String token) {
        return new ConfigurationPlace(toSingletonToken(token).action);
    }
}
