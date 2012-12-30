package de.mkristian.ixtlan.users.client.views;


import com.google.inject.ImplementedBy;

import de.mkristian.gwt.rails.views.CRUDView;
import de.mkristian.ixtlan.users.client.models.Domain;
import de.mkristian.ixtlan.users.client.presenters.DomainPresenter;

@ImplementedBy(DomainViewImpl.class)
public interface DomainView extends CRUDView<Domain, DomainPresenter> {
}