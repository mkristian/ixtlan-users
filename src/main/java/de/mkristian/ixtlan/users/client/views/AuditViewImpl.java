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
import de.mkristian.ixtlan.users.client.editors.AuditEditor;
import de.mkristian.ixtlan.users.client.models.Audit;
import de.mkristian.ixtlan.users.client.places.AuditPlace;

@Singleton
public class AuditViewImpl extends ReadOnlyViewImpl<Audit>
            implements AuditView {

  @UiTemplate("ReadOnlyView.ui.xml")
  static interface Binder extends UiBinder<Widget, AuditViewImpl> {}

  static interface EditorDriver extends SimpleBeanEditorDriver<Audit, AuditEditor> {}

  static private final Binder BINDER = GWT.create(Binder.class);

  @SuppressWarnings("unchecked")
  @Inject
  public AuditViewImpl( PlaceController places ) {
      super( "Audits",
              places,
              new AuditEditor(),
              (SimpleBeanEditorDriver<Audit, Editor<Audit>>) GWT.create( EditorDriver.class ) );
      initWidget( BINDER.createAndBindUi( this ) );
  }

  @Override
  protected Place newListPlace() {
      return new AuditPlace( RestfulActionEnum.INDEX );
  }
}
