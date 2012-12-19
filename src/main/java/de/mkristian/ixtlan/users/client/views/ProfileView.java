package de.mkristian.ixtlan.users.client.views;



import com.google.inject.ImplementedBy;

import de.mkristian.gwt.rails.views.SingletonView;
import de.mkristian.ixtlan.users.client.models.Profile;

@ImplementedBy(ProfileViewImpl.class)
public interface ProfileView extends SingletonView<Profile> {
}
