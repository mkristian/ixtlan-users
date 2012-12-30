package de.mkristian.ixtlan.users.client.views;



import com.google.inject.ImplementedBy;

import de.mkristian.gwt.rails.views.CRUDListView;
import de.mkristian.ixtlan.users.client.models.Application;


@ImplementedBy(ApplicationListViewImpl.class)
public interface ApplicationListView extends CRUDListView<Application> {
}