package de.mkristian.ixtlan.users.client.views;

import java.util.List;

import javax.inject.Inject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulActionEnum;
import de.mkristian.gwt.rails.session.Guard;
import de.mkristian.gwt.rails.views.CRUDListViewImpl;
import de.mkristian.ixtlan.users.client.models.Application;
import de.mkristian.ixtlan.users.client.places.ApplicationPlace;

@Singleton
public class ApplicationListViewImpl extends CRUDListViewImpl<Application>
            implements ApplicationListView {

    @UiTemplate("ListView.ui.xml")
    static interface Binder extends UiBinder<Widget, ApplicationListViewImpl> {}
    
    private static Binder BINDER = GWT.create( Binder.class );

    @Inject
    public ApplicationListViewImpl( Guard guard,
                PlaceController places ) {
        super( "Regions", guard, places );
        initWidget( BINDER.createAndBindUi( this ) );
    }

    @Override
    protected String placeName() {
        return ApplicationPlace.NAME;
    }

    @Override
    protected Place place( Application model, RestfulAction action ) {
        return new ApplicationPlace( model, action );
    }
    
    public void reset(List<Application> models) {
        list.removeAllRows();
        list.setText(0, 0, "Id");
        list.setText(0, 1, "Name");
        list.getRowFormatter().addStyleName(0, "gwt-rails-model-list-header");
        int row = 1;
        for(Application model: models){
            setRow(row, model);
            row++;
        }
    }

    private void setRow(int row, Application model) {
        list.setText(row, 0, model.getId() + "");
        list.setText(row, 1, model.getName() + "");

        int index = 2;
        list.setWidget(row, index, newButton(RestfulActionEnum.SHOW, model));
        if ( isAllowed( RestfulActionEnum.EDIT ) ){
            list.setWidget(row, ++index, newButton(RestfulActionEnum.EDIT, model ) );
        }
        if ( isAllowed( RestfulActionEnum.DESTROY ) ){
            list.setWidget(row, ++index, newButton(RestfulActionEnum.DESTROY, model ) );
        }
    }
}
