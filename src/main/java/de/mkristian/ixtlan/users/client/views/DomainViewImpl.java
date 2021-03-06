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

import de.mkristian.gwt.rails.places.RestfulActionEnum;
import de.mkristian.gwt.rails.session.Guard;
import de.mkristian.gwt.rails.views.CRUDViewImpl;
import de.mkristian.ixtlan.users.client.editors.DomainEditor;
import de.mkristian.ixtlan.users.client.models.Domain;
import de.mkristian.ixtlan.users.client.places.DomainPlace;
import de.mkristian.ixtlan.users.client.presenters.DomainPresenter;

@Singleton
public class DomainViewImpl extends CRUDViewImpl<Domain, DomainPresenter> 
            implements DomainView {

    @UiTemplate("View.ui.xml")
    static interface Binder extends UiBinder<Widget, DomainViewImpl> {}
    
    static private Binder BINDER = GWT.create( Binder.class );

    static interface EditorDriver extends SimpleBeanEditorDriver<Domain, DomainEditor> {}

    @SuppressWarnings("unchecked")
    @Inject
    public DomainViewImpl( Guard guard, PlaceController places ) {
        super( "Domains", 
                guard, 
                places, 
                new DomainEditor(),
                (SimpleBeanEditorDriver<Domain, Editor<Domain>>) GWT.create(EditorDriver.class) );
        initWidget( BINDER.createAndBindUi( this ) );
    }
    
    @Override
    protected String placeName() {
        return DomainPlace.NAME;
    }
    
    @Override
    protected Place newPlace() {
        return new DomainPlace( RestfulActionEnum.NEW );
    }

    @Override
    protected Place showPlace( Domain model ) {
        return new DomainPlace( model, RestfulActionEnum.SHOW );
    }

    @Override
    protected Place editPlace( Domain model ) {
        return new DomainPlace( model, RestfulActionEnum.EDIT );
    }

    @Override
    protected Domain newModel() {
        return new Domain();
    }

    @Override
    protected Place showAllPlace() {
        return new DomainPlace( RestfulActionEnum.INDEX );
    }
 }
