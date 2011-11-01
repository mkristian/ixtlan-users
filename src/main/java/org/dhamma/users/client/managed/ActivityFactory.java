package org.dhamma.users.client.managed;

import com.google.gwt.activity.shared.Activity;
import com.google.inject.name.Named;
import org.dhamma.users.client.places.LoginPlace;
public interface ActivityFactory {
  @Named("errors") Activity create(org.dhamma.users.client.places.ErrorPlace place);
  @Named("applications") Activity create(org.dhamma.users.client.places.ApplicationPlace place);
  @Named("remote_permissions") Activity create(org.dhamma.users.client.places.RemotePermissionPlace place);
  @Named("groups") Activity create(org.dhamma.users.client.places.GroupPlace place);
  @Named("profiles") Activity create(org.dhamma.users.client.places.ProfilePlace place);
  @Named("configurations") Activity create(org.dhamma.users.client.places.ConfigurationPlace place);
  @Named("users") Activity create(org.dhamma.users.client.places.UserPlace place);
    @Named("login") Activity create(LoginPlace place);
}