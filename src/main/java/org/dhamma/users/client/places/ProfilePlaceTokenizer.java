package org.dhamma.users.client.places;

import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

import de.mkristian.gwt.rails.RestfulPlaceTokenizer;

@Prefix("profile") 
public class ProfilePlaceTokenizer extends RestfulPlaceTokenizer<ProfilePlace> 
    implements PlaceTokenizer<ProfilePlace> {
    
    public ProfilePlace getPlace(String token) {
	return new ProfilePlace(toSingletonToken(token).action);
    }
}
