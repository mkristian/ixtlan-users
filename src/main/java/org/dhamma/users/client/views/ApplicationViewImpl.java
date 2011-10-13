package org.dhamma.users.client.views;

import java.util.List;

import org.dhamma.users.client.editors.ApplicationEditor;
import org.dhamma.users.client.models.Application;
import org.dhamma.users.client.places.ApplicationPlace;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulActionEnum;
import de.mkristian.gwt.rails.views.ModelButton;

@Singleton
public class ApplicationViewImpl extends Composite implements ApplicationView {

    @UiTemplate("ApplicationView.ui.xml")
    interface Binder extends UiBinder<Widget, ApplicationViewImpl> {}
    
    private static Binder BINDER = GWT.create(Binder.class);

    interface EditorDriver extends SimpleBeanEditorDriver<Application, ApplicationEditor> {}

    private final EditorDriver editorDriver = GWT.create(EditorDriver.class);

    @UiField Button newButton;
    @UiField Button editButton;
    @UiField Button showButton;

    @UiField Button createButton;
    @UiField Button saveButton;
    @UiField Button deleteButton;

    @UiField Panel model;
    @UiField FlexTable list;

    @UiField ApplicationEditor editor;

    private Presenter presenter;

    public ApplicationViewImpl() {
        initWidget(BINDER.createAndBindUi(this));
        editorDriver.initialize(editor);
    }

    @UiHandler("newButton")
    void onClickNew(ClickEvent e) {
        presenter.goTo(new ApplicationPlace(RestfulActionEnum.NEW));
    }

    @UiHandler("showButton")
    void onClickShow(ClickEvent e) {
        presenter.goTo(new ApplicationPlace(editor.id.getValue(), RestfulActionEnum.SHOW));
    }

    @UiHandler("editButton")
    void onClickEdit(ClickEvent e) {
        presenter.goTo(new ApplicationPlace(editor.id.getValue(), RestfulActionEnum.EDIT));
    }

    @UiHandler("createButton")
    void onClickCreate(ClickEvent e) {
        presenter.create();
    }

    @UiHandler("saveButton")
    void onClickSave(ClickEvent e) {
        presenter.save();
    }

    @UiHandler("deleteButton")
    void onClickDelete(ClickEvent e) {
        presenter.delete(flush());
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public void edit(Application model) {
        this.editorDriver.edit(model);
    }

    public Application flush() {
        return editorDriver.flush();
    }

    public void setEnabled(boolean enabled) {
        editor.setEnabled(enabled);
    }

    public void reset(RestfulAction action) {
        newButton.setVisible(!action.name().equals(RestfulActionEnum.NEW.name()));
        if(action.name().equals(RestfulActionEnum.INDEX.name())){
            editButton.setVisible(false);
            showButton.setVisible(false);
            list.setVisible(true);
            model.setVisible(false);
        }
        else {
            createButton.setVisible(action.name().equals(RestfulActionEnum.NEW.name()));
            editButton.setVisible(action.name().equals(RestfulActionEnum.SHOW.name()));
            showButton.setVisible(action.name().equals(RestfulActionEnum.EDIT.name()));
            saveButton.setVisible(action.name().equals(RestfulActionEnum.EDIT.name()));
            deleteButton.setVisible(action.name().equals(RestfulActionEnum.EDIT.name()));
            setEnabled(!action.viewOnly());
            list.setVisible(false);
            model.setVisible(true);
        }
    }

    private final ClickHandler clickHandler = new ClickHandler() {
        
        @SuppressWarnings("unchecked")
        public void onClick(ClickEvent event) {
            ModelButton<Application> button = (ModelButton<Application>)event.getSource();
            switch(button.action){
                case DESTROY:
                    presenter.delete(button.model);
                    break;
                default:
                    presenter.goTo(new ApplicationPlace(button.model, button.action));
            }
        }
    };

    private Button newButton(RestfulActionEnum action, Application model){
        ModelButton<Application> button = new ModelButton<Application>(action, model);
        button.addClickHandler(clickHandler);
        return button;
    }

    public void reset(List<Application> models) {
        list.removeAllRows();
        list.setText(0, 0, "Id");
        list.setText(0, 1, "Name");
        list.setText(0, 2, "Url");
        list.getRowFormatter().addStyleName(0, "model-list-header");
        int row = 1;
        for(Application model: models){
            setRow(row, model);
            row++;
        }
    }

    private void setRow(int row, Application model) {
        list.setText(row, 0, model.getId() + "");
        list.setText(row, 1, model.getName() + "");

        list.setText(row, 2, model.getUrl() + "");

        list.setWidget(row, 3, newButton(RestfulActionEnum.SHOW, model));
        list.setWidget(row, 4, newButton(RestfulActionEnum.EDIT, model));
        list.setWidget(row, 5, newButton(RestfulActionEnum.DESTROY, model));
    }

    public void updateInList(Application model) {
        String id = model.getId() + "";
        for(int i = 0; i < list.getRowCount(); i++){
            if(list.getText(i, 0).equals(id)){
                setRow(i, model);
                return;
            }
        }
    }

    public void removeFromList(Application model) {
        String id = model.getId() + "";
        for(int i = 0; i < list.getRowCount(); i++){
            if(list.getText(i, 0).equals(id)){
                list.removeRow(i);
                return;
            }
        }
    }

    public void addToList(Application model) {
        setRow(list.getRowCount(), model);
    }
}
