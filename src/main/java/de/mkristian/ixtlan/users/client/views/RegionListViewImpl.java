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
import de.mkristian.ixtlan.users.client.models.Region;
import de.mkristian.ixtlan.users.client.places.RegionPlace;

@Singleton
public class RegionListViewImpl extends CRUDListViewImpl<Region>
            implements RegionListView {

    @UiTemplate("ListView.ui.xml")
    static interface Binder extends UiBinder<Widget, RegionListViewImpl> {}
    
    private static Binder BINDER = GWT.create( Binder.class );

    @Inject
    public RegionListViewImpl( Guard guard,
                PlaceController places ) {
        super( "Regions", guard, places );
        initWidget( BINDER.createAndBindUi( this ) );
    }

    @Override
    protected String placeName() {
        return RegionPlace.NAME;
    }

    @Override
    protected Place newPlace() {
        return new RegionPlace( RestfulActionEnum.NEW );
    }
    
    @Override
    protected Place place( Region model, RestfulAction action ) {
        return new RegionPlace( model, action );
    }
    
    public void reset(List<Region> models) {
        list.removeAllRows();
        list.setText(0, 0, "Id");
        list.setText(0, 1, "Name");
        list.getRowFormatter().addStyleName(0, "gwt-rails-model-list-header");
        int row = 1;
        for(Region model: models){
            setRow(row, model);
            row++;
        }
    }

    private void setRow(int row, Region model) {
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
