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
import de.mkristian.ixtlan.users.client.editors.ConfigurationEditor;
import de.mkristian.ixtlan.users.client.models.Configuration;
import de.mkristian.ixtlan.users.client.places.ConfigurationPlace;

@Singleton
public class ConfigurationViewImpl extends SingletonViewImpl<Configuration>
            implements ConfigurationView {

  @UiTemplate("SingletonView.ui.xml")
  static interface Binder extends UiBinder<Widget, ConfigurationViewImpl> {}

  static interface EditorDriver extends SimpleBeanEditorDriver<Configuration, ConfigurationEditor> {}

  static private final Binder BINDER = GWT.create(Binder.class);

  @SuppressWarnings("unchecked")
  @Inject
  public ConfigurationViewImpl( Guard guard, PlaceController places ) {
      super( "Configuration",
              guard,
              places,
              new ConfigurationEditor(),
              (SimpleBeanEditorDriver<Configuration, Editor<Configuration>>) GWT.create( EditorDriver.class ) );
      initWidget( BINDER.createAndBindUi( this ) );
  }

    @Override
    protected String placeName() {
        return ConfigurationPlace.NAME;
    }
    
    @Override
    protected Place showPlace( Configuration model ) {
       return new ConfigurationPlace( model, RestfulActionEnum.SHOW );
    }
    
    @Override
    protected Place editPlace( Configuration model ) {
        return new ConfigurationPlace( model, RestfulActionEnum.EDIT );
    }
}
