package org.dhamma.users.client.managed;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({org.dhamma.users.client.places.ConfigurationPlaceTokenizer.class,
    org.dhamma.users.client.places.UserPlaceTokenizer.class})
public interface UsersPlaceHistoryMapper extends PlaceHistoryMapper {
}
