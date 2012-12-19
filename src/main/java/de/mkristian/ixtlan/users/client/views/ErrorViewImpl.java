package de.mkristian.ixtlan.users.client.views;


import javax.inject.Inject;
import javax.inject.Singleton;


import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;

import de.mkristian.gwt.rails.places.RestfulActionEnum;
import de.mkristian.gwt.rails.views.ReadOnlyViewImpl;
import de.mkristian.ixtlan.users.client.editors.ErrorEditor;
import de.mkristian.ixtlan.users.client.models.Error;
import de.mkristian.ixtlan.users.client.places.ErrorPlace;


@Singleton
public class ErrorViewImpl extends ReadOnlyViewImpl<Error> 
            implements ErrorView {

    @UiTemplate("ReadOnlyView.ui.xml")
    static interface Binder extends UiBinder<Widget, ErrorViewImpl> {}

    static interface EditorDriver extends SimpleBeanEditorDriver<Error, ErrorEditor> {}

    static private final Binder BINDER = GWT.create( Binder.class );

    @SuppressWarnings("unchecked")
    @Inject
    public ErrorViewImpl( PlaceController places ) {
        super( "Errors", 
                places,
                new ErrorEditor(),
                (SimpleBeanEditorDriver<Error, Editor<Error>>) GWT.create(EditorDriver.class));
        initWidget( BINDER.createAndBindUi( this ) );
        editorDriver.initialize( editor );
    }

    @Override
    protected Place newListPlace() {
        return new ErrorPlace( RestfulActionEnum.INDEX );
    }
}
