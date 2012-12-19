package de.mkristian.ixtlan.users.client.views;


import static de.mkristian.gwt.rails.places.RestfulActionEnum.SHOW;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.views.ReadOnlyListViewImpl;
import de.mkristian.ixtlan.users.client.models.Audit;
import de.mkristian.ixtlan.users.client.places.AuditPlace;

@Singleton
public class AuditListViewImpl extends ReadOnlyListViewImpl<Audit>
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
    
    //@Override
    public void reset(List<Audit> models) {
        list.removeAllRows();
        list.setText(0, 0, "Id");
        list.setText(0, 1, "Login");
        list.setText(0, 2, "Message");
        list.getRowFormatter().addStyleName(0, "gwt-rails-model-list-header");
        if (models != null) {
            int row = 1;
            for(Audit model: models){
                setRow(row, model);
                row++;
            }
        }
    }

    private void setRow(int row, Audit model) {
        list.setText(row, 0, model.getId() + "");
        list.setText(row, 1, model.getLogin() + "");
        list.setText(row, 2, model.getMessage() + "");

        list.setWidget(row, 3, newButton(SHOW, model));
    }
}
