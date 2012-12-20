package de.mkristian.ixtlan.users.client.views;



import com.google.inject.ImplementedBy;

import de.mkristian.gwt.rails.views.ReadOnlyListView;
import de.mkristian.ixtlan.users.client.audits.Audit;

@ImplementedBy(AuditListViewImpl.class)
public interface AuditListView extends ReadOnlyListView<Audit> {
}