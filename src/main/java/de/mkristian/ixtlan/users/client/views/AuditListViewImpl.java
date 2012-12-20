package de.mkristian.ixtlan.users.client.views;




import javax.inject.Inject;
import javax.inject.Singleton;


import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.ixtlan.users.client.audits.AbstractAuditListViewImpl;
import de.mkristian.ixtlan.users.client.audits.Audit;
import de.mkristian.ixtlan.users.client.places.AuditPlace;

@Singleton
public class AuditListViewImpl extends AbstractAuditListViewImpl
            implements AuditListView {

    @UiTemplate("ListView.ui.xml")
    static interface Binder extends UiBinder<Widget, AuditListViewImpl> {}

    static private Binder BINDER = GWT.create(Binder.class);

    @Inject
    public AuditListViewImpl( PlaceController places ) {
        super( "Audits", places );
        initWidget( BINDER.createAndBindUi( this ) );
    }
    
    @Override
    protected Place place(Audit model, RestfulAction action) {
        return new AuditPlace( model,action );
    }
}
