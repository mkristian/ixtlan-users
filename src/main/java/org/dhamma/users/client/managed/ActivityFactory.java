package org.dhamma.users.client.managed;

import com.google.gwt.activity.shared.Activity;
import com.google.inject.name.Named;
import org.dhamma.users.client.places.LoginPlace;
public interface ActivityFactory {
  @Named("profiles") Activity create(org.dhamma.users.client.places.ProfilePlace place);
  @Named("configurations") Activity create(org.dhamma.users.client.places.ConfigurationPlace place);
  @Named("users") Activity create(org.dhamma.users.client.places.UserPlace place);
    @Named("login") Activity create(LoginPlace place);
}