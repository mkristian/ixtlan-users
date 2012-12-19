package de.mkristian.ixtlan.users.client.views;



import com.google.inject.ImplementedBy;

import de.mkristian.gwt.rails.views.CRUDListView;
import de.mkristian.ixtlan.users.client.models.Region;


@ImplementedBy(RegionListViewImpl.class)
public interface RegionListView extends CRUDListView<Region> {
}