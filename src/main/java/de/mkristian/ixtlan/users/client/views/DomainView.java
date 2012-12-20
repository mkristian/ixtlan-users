package de.mkristian.ixtlan.users.client.views;


import com.google.inject.ImplementedBy;

import de.mkristian.gwt.rails.views.CRUDView;
import de.mkristian.ixtlan.users.client.models.Domain;

@ImplementedBy(DomainViewImpl.class)
public interface DomainView extends CRUDView<Domain> {
}