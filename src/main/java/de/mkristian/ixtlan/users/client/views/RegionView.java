package de.mkristian.ixtlan.users.client.views;


import com.google.inject.ImplementedBy;

import de.mkristian.gwt.rails.views.CRUDView;
import de.mkristian.ixtlan.users.client.models.Region;
import de.mkristian.ixtlan.users.client.presenters.RegionPresenter;

@ImplementedBy(RegionViewImpl.class)
public interface RegionView extends CRUDView<Region, RegionPresenter> {
}