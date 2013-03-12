package de.mkristian.ixtlan.users.client.views;


import static de.mkristian.gwt.rails.places.RestfulActionEnum.DESTROY;
import static de.mkristian.gwt.rails.places.RestfulActionEnum.EDIT;
import static de.mkristian.gwt.rails.places.RestfulActionEnum.INDEX;
import static de.mkristian.gwt.rails.places.RestfulActionEnum.NEW;
import static de.mkristian.gwt.rails.places.RestfulActionEnum.SHOW;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

import de.mkristian.gwt.rails.places.RestfulActionEnum;
import de.mkristian.gwt.rails.session.Guard;
import de.mkristian.gwt.rails.views.CRUDViewImpl;
import de.mkristian.gwt.rails.views.ModelButton;
import de.mkristian.ixtlan.users.client.editors.ApplicationEditor;
import de.mkristian.ixtlan.users.client.editors.GroupEditor;
import de.mkristian.ixtlan.users.client.models.Application;
import de.mkristian.ixtlan.users.client.models.Group;
import de.mkristian.ixtlan.users.client.places.ApplicationPlace;
import de.mkristian.ixtlan.users.client.presenters.ApplicationPresenter;

@Singleton
public class ApplicationViewImpl extends CRUDViewImpl<Application, ApplicationPresenter> 
            implements ApplicationView {

    @UiTemplate("ApplicationView.ui.xml")
    static interface Binder extends UiBinder<Widget, ApplicationViewImpl> {}
    
    static private Binder BINDER = GWT.create( Binder.class );

    static interface EditorDriver extends SimpleBeanEditorDriver<Application, ApplicationEditor> {}
    static interface GroupEditorDriver 
                extends SimpleBeanEditorDriver<Group, GroupEditor> {}

    private final GroupEditorDriver editorDriver = GWT.create(GroupEditorDriver.class);

    private GroupEditor groupEditor = new GroupEditor();

    private final Button create = new Button("Create");
    
    private final Button save = new Button("Save");

    private final Button cancel = new Button("Cancel");

    @UiField FlexTable list;
    
    @SuppressWarnings("unchecked")
    @Inject
    public ApplicationViewImpl( Guard guard, PlaceController places ) {
        super( "Applications", 
                guard, 
                places, 
                new ApplicationEditor(),
                (SimpleBeanEditorDriver<Application, Editor<Application>>) GWT.create(EditorDriver.class) );
        editorDriver.initialize( groupEditor );
        initWidget( BINDER.createAndBindUi( this ) );
        create.addClickHandler( createClickHandler );
        save.addClickHandler( saveClickHandler );
        cancel.addClickHandler( cancelClickHandler );
    }
    
    @Override
    protected String placeName() {
        return ApplicationPlace.NAME;
    }
    
    @Override
    protected Place newPlace() {
        return new ApplicationPlace( NEW );
    }

    @Override
    protected Place showPlace( Application model ) {
        return new ApplicationPlace( model, SHOW );
    }

    @Override
    protected Place editPlace( Application model ) {
        return new ApplicationPlace( model, EDIT );
    }

    @Override
    protected Application newModel() {
        return new Application();
    }

    @Override
    protected Place showAllPlace() {
        return new ApplicationPlace( INDEX );
    }

    @Override
    public void edit( Application model ) {
        super.edit( model );
        resetGroups( model );
        this.header.setText( "Applications" + " - " + model.getName() );
    }

    @Override
    public void show(Application model) {
        super.show(model);
        resetGroups( model );
        this.header.setText( "Applications" + " - " + model.getName() );
    }

    @Override
    public void reset(Application model) {
        super.reset( model );
        resetGroups( model );
        this.header.setText( "Applications" + " - " + model.getName() );
    }

    @Override
    public boolean isGroupDirty() {
        return editorDriver.isDirty();
    }
    
    private final ClickHandler createClickHandler = new ClickHandler() {
        
        public void onClick(ClickEvent event) {
            getPresenter().create( editorDriver.flush() );
        }
    };

    private final ClickHandler saveClickHandler = new ClickHandler() {
        
        public void onClick(ClickEvent event) {
            getPresenter().save( editorDriver.flush() );
        }
    };

    private final ClickHandler cancelClickHandler = new ClickHandler() {
        
        public void onClick(ClickEvent event) {
            getPresenter().showCurrent();
        }
    };
    
    private final ClickHandler clickHandler = new ClickHandler() {
        
        @SuppressWarnings("unchecked")
        public void onClick(ClickEvent event) {
            ModelButton<Group> button = (ModelButton<Group>)event.getSource();
            switch(button.action){
                case NEW:
                    getPresenter().newGroup( button.model ); break;
                case EDIT:
                    getPresenter().edit( button.model ); break;
                case DESTROY:
                    getPresenter().delete( button.model ); break; 
            }
        }
    };

    private Button newButton(RestfulActionEnum action, Group model){
        ModelButton<Group> button = new ModelButton<Group>(action, model);
        button.addClickHandler(clickHandler);
        return button;
    }

    private final Map<Integer, Integer> id2row = new HashMap<Integer, Integer>();
    
    private void setRow( int row, Group model ) {
        id2row.put( model.getId(), row );
        list.setText( row, 0, "" + model.getId() );
        list.setText( row, 1, model.getName() );
        list.setText( row, 2, model.hasRegions() ? "X" : "_" );
        list.setText( row, 3, model.hasLocales() ? "X" : "_" );
        list.setText( row, 4, model.hasDomains() ? "X" : "_" );
        list.setWidget( row, 5, newButton( EDIT, model ) );
        list.setWidget( row, 6, newButton( DESTROY, model ) );
    } 
    
    @Override
    public void resetGroups( Application application ){
        list.removeAllRows();
        list.setText(0, 0, "Id");
        list.setText(0, 1, "Name");
        list.setText(0, 2, "Has Regions");
        list.setText(0, 3, "Has Locales");
        list.setText(0, 4, "Has Domains");
        list.getRowFormatter().addStyleName(0, "users-group-header");
        resetNewRow( application );

        int row = 2;
        for(Group t: application.getGroups() ){
            setRow(row, t);
            row ++;
        }
    }
    
    @Override
    public void resetNewRow( Application application ) {
        list.setText( 1, 0, "" );
        list.setText( 1, 1, "" );
        list.getFlexCellFormatter().setColSpan( 1, 1, 4 );
        list.setWidget( 1, 2, newButton( NEW, new Group( application ) ) );
        list.setText( 1, 3, "" );
        GWT.log("reset new row");
    }

    @Override
    public void show( Group model ){
        int row = id2row.get( model.getId() );
        setRow( row, model );
        list.getFlexCellFormatter().setColSpan( row, 0, 1 );
    }
    
    @Override
    public void edit( Group model ){
        int row = id2row.get( model.getId() );
        list.setWidget( row, 0, groupEditor );
        list.setWidget( row, 1, save );
        list.setWidget( row, 2, cancel );
        list.clearCell( row, 3 );
        list.clearCell( row, 4 );
        list.clearCell( row, 5 );
        list.clearCell( row, 6 );
        FlexCellFormatter format = list.getFlexCellFormatter();
        format.setColSpan( row, 0, 5 );
        format.setVerticalAlignment( row, 0, HasAlignment.ALIGN_TOP );
        format.setVerticalAlignment( row, 1, HasAlignment.ALIGN_TOP );
        format.setVerticalAlignment( row, 2, HasAlignment.ALIGN_TOP );
        editorDriver.edit( model );
        groupEditor.setIsNew( false );
    }

    @Override
    public void newGroup( Group model ){
        list.setWidget( 1, 1, groupEditor );
        list.setWidget( 1, 2, create );
        list.setWidget( 1, 3, cancel );
//        FlexCellFormatter format = list.getFlexCellFormatter();
//        format.setColSpan( 1, 1, 4 );
//        format.setVerticalAlignment( 1, 0, HasAlignment.ALIGN_TOP );
//        format.setVerticalAlignment( 1, 2, HasAlignment.ALIGN_BOTTOM );
//        format.setVerticalAlignment( 1, 3, HasAlignment.ALIGN_BOTTOM );
        editorDriver.edit( model );
        groupEditor.setIsNew( true );
    }

    @Override
    public void reset( Group model ){
        editorDriver.edit( model );
    }
 }
