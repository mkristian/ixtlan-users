package org.dhamma.users.client.managed;

import com.google.gwt.activity.shared.Activity;
import com.google.inject.name.Named;
import org.dhamma.users.client.places.LoginPlace;
public interface ActivityFactory {
    @Named("login") Activity create(LoginPlace place);
}