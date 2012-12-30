package de.mkristian.ixtlan.users.client.views;


import com.google.inject.ImplementedBy;

import de.mkristian.gwt.rails.views.ReadOnlyListView;
import de.mkristian.ixtlan.gwt.errors.Error;

@ImplementedBy(ErrorListViewImpl.class)
public interface ErrorListView extends ReadOnlyListView<Error> {
}