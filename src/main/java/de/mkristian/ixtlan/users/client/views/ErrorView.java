package de.mkristian.ixtlan.users.client.views;



import com.google.inject.ImplementedBy;

import de.mkristian.gwt.rails.views.ReadOnlyView;
import de.mkristian.ixtlan.gwt.errors.Error;

@ImplementedBy(ErrorViewImpl.class)
public interface ErrorView extends ReadOnlyView<Error> {
}
