package de.mkristian.ixtlan.users.client.views;



import com.google.inject.ImplementedBy;

import de.mkristian.gwt.rails.views.ReadOnlyView;
import de.mkristian.ixtlan.users.client.models.Audit;

@ImplementedBy(AuditViewImpl.class)
public interface AuditView extends ReadOnlyView<Audit> {
}
