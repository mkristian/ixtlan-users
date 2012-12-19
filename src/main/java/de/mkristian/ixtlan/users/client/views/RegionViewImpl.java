package de.mkristian.ixtlan.users.client.views;


import javax.inject.Inject;


import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

import de.mkristian.gwt.rails.editors.IdentifiableEditor;
import de.mkristian.gwt.rails.places.RestfulActionEnum;
import de.mkristian.gwt.rails.session.Guard;
import de.mkristian.gwt.rails.views.CRUDViewImpl;
import de.mkristian.ixtlan.users.client.editors.RegionEditor;
import de.mkristian.ixtlan.users.client.models.Region;
import de.mkristian.ixtlan.users.client.places.RegionPlace;

@Singleton
public class RegionViewImpl extends CRUDViewImpl<Region> 
            implements RegionView {

    @UiTemplate("View.ui.xml")
    static interface Binder extends UiBinder<Widget, RegionViewImpl> {}
    
    static private Binder BINDER = GWT.create( Binder.class );

    static interface EditorDriver extends SimpleBeanEditorDriver<Region, IdentifiableEditor<Region>> {}

    @SuppressWarnings("unchecked")
    @Inject
    public RegionViewImpl( Guard guard, PlaceController places ) {
        super( "Regions", 
                guard, 
                places, 
                new RegionEditor(),
                (SimpleBeanEditorDriver<Region, Editor<Region>>) GWT.create(EditorDriver.class) );
        initWidget( BINDER.createAndBindUi( this ) );
        editorDriver.initialize( editor ); 
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
    protected Place showPlace( int id ) {
        return new RegionPlace( id, RestfulActionEnum.SHOW );
    }

    @Override
    protected Place editPlace(int id) {
        return new RegionPlace( id, RestfulActionEnum.EDIT );
    }

    @Override
    protected Region newModel() {
        return new Region();
    }
 }
