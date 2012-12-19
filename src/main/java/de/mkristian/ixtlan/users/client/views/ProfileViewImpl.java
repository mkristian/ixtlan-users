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
import de.mkristian.gwt.rails.session.Guard;
import de.mkristian.gwt.rails.views.SingletonViewImpl;
import de.mkristian.ixtlan.users.client.editors.ProfileEditor;
import de.mkristian.ixtlan.users.client.models.Profile;
import de.mkristian.ixtlan.users.client.places.ProfilePlace;

@Singleton
public class ProfileViewImpl extends SingletonViewImpl<Profile> 
            implements ProfileView {

    @UiTemplate("SingletonView.ui.xml")
    static interface Binder extends UiBinder<Widget, ProfileViewImpl> {}

    static interface EditorDriver extends SimpleBeanEditorDriver<Profile, ProfileEditor> {}

    static private final Binder BINDER = GWT.create(Binder.class);

    static private SimpleBeanEditorDriver<Profile, Editor<Profile>> editorDriver(){
        return GWT.create( EditorDriver.class );
    }
  
    @Inject
    public ProfileViewImpl( Guard guard, PlaceController places ) {
        super( "Profile",
                guard,
                places,
                new ProfileEditor(),
                editorDriver() );
        initWidget(BINDER.createAndBindUi(this));
        editorDriver.initialize(editor);
    }

    @Override
    protected String placeName() {
        return ProfilePlace.NAME;
    }
    
    @Override
    protected Place showPlace( Profile model ) {
        return new ProfilePlace( model, RestfulActionEnum.SHOW );
    }
    
    @Override
    protected Place editPlace( Profile model ) {
        return new ProfilePlace( model, RestfulActionEnum.EDIT );
    }
}
