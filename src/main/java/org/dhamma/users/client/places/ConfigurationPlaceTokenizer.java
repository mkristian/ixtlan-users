package org.dhamma.users.client.places;

import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

import de.mkristian.gwt.rails.RestfulPlaceTokenizer;

@Prefix("configuration") 
public class ConfigurationPlaceTokenizer extends RestfulPlaceTokenizer<ConfigurationPlace> 
    implements PlaceTokenizer<ConfigurationPlace> {
    
    public ConfigurationPlace getPlace(String token) {
	return new ConfigurationPlace(toSingletonToken(token).action);
    }
}
