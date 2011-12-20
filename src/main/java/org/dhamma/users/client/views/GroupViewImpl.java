package org.dhamma.users.client.views;

import java.util.List;

import javax.inject.Inject;

import org.dhamma.users.client.editors.GroupEditor;
import org.dhamma.users.client.models.Application;
import org.dhamma.users.client.models.User;
import org.dhamma.users.client.models.Group;
import org.dhamma.users.client.places.GroupPlace;

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
import static de.mkristian.gwt.rails.places.RestfulActionEnum.*;
import de.mkristian.gwt.rails.session.SessionManager;
import de.mkristian.gwt.rails.views.ModelButton;

@Singleton
public class GroupViewImpl extends Composite implements GroupView {

    @UiTemplate("GroupView.ui.xml")
    interface Binder extends UiBinder<Widget, GroupViewImpl> {}
    
    private static Binder BINDER = GWT.create(Binder.class);

    interface EditorDriver extends SimpleBeanEditorDriver<Group, GroupEditor> {}

    private final EditorDriver editorDriver = GWT.create(EditorDriver.class);

    @UiField Button newButton;
    @UiField Button editButton;
    @UiField Button showButton;

    @UiField Button createButton;
    @UiField Button saveButton;
    @UiField Button deleteButton;

    @UiField Panel model;
    @UiField FlexTable list;

    @UiField GroupEditor editor;

    private Presenter presenter;

    private final SessionManager<User> session;

    public GroupViewImpl() {
        this(null);
    }

    @Inject
    public GroupViewImpl(SessionManager<User> session) {
        initWidget(BINDER.createAndBindUi(this));
        editorDriver.initialize(editor);
        this.session = session;
    }

    private boolean isAllowed(RestfulActionEnum action){
        return session == null || session.isAllowed(GroupPlace.NAME, action);
    }

    @UiHandler("newButton")
    void onClickNew(ClickEvent e) {
        presenter.goTo(new GroupPlace(RestfulActionEnum.NEW));
    }

    @UiHandler("showButton")
    void onClickShow(ClickEvent e) {
        presenter.goTo(new GroupPlace(editor.id.getValue(), RestfulActionEnum.SHOW));
    }

    @UiHandler("editButton")
    void onClickEdit(ClickEvent e) {
        presenter.goTo(new GroupPlace(editor.id.getValue(), RestfulActionEnum.EDIT));
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

    public void setup(Presenter presenter, RestfulAction a) {
        RestfulActionEnum action = RestfulActionEnum.valueOf(a);
        this.presenter = presenter;
        newButton.setVisible(action != NEW && isAllowed(NEW));
        if(action == INDEX){
            editButton.setVisible(false);
            showButton.setVisible(false);
            list.setVisible(true);
            model.setVisible(false);
        }
        else {
            createButton.setVisible(action == NEW);
            editButton.setVisible(action == SHOW && isAllowed(EDIT));
            showButton.setVisible(action == EDIT);
            saveButton.setVisible(action == EDIT);
            deleteButton.setVisible(action == EDIT && isAllowed(DESTROY));
            list.setVisible(false);
            model.setVisible(true);
        }
        editor.setEnabled(!action.viewOnly());
    }

    public void edit(Group model) {
        this.editorDriver.edit(model);
        this.editor.resetVisibility();
    }

    public Group flush() {
        return editorDriver.flush();
    }

    private final ClickHandler clickHandler = new ClickHandler() {
        
        @SuppressWarnings("unchecked")
        public void onClick(ClickEvent event) {
            ModelButton<Group> button = (ModelButton<Group>)event.getSource();
            switch(button.action){
                case DESTROY:
                    presenter.delete(button.model);
                    break;
                default:
                    presenter.goTo(new GroupPlace(button.model, button.action));
            }
        }
    };

    private Button newButton(RestfulActionEnum action, Group model){
        ModelButton<Group> button = new ModelButton<Group>(action, model);
        button.addClickHandler(clickHandler);
        return button;
    }

    public void reset(List<Group> models) {
        list.removeAllRows();
        list.setText(0, 0, "Id");
        list.setText(0, 1, "Name");
        list.setText(0, 2, "Description");
        list.setText(0, 3, "Application");
        list.setText(0, 4, "Has regions");
        list.getRowFormatter().addStyleName(0, "gwt-rails-model-list-header");
        int row = 1;
        for(Group model: models){
            setRow(row, model);
            row++;
        }
    }

    private void setRow(int row, Group model) {
        list.setText(row, 0, model.getId() + "");
        list.setText(row, 1, model.getName() + "");
        list.setText(row, 2, model.getDescription() + "");
        list.setText(row, 3, model.getApplication() == null ? "-" : model.getApplication().toDisplay());
        list.setText(row, 4, model.getHasRegions() + "");

        list.setWidget(row, 5, newButton(RestfulActionEnum.SHOW, model));
        list.setWidget(row, 6, newButton(RestfulActionEnum.EDIT, model));
        list.setWidget(row, 7, newButton(RestfulActionEnum.DESTROY, model));
    }

    public void removeFromList(Group model) {
        String id = model.getId() + "";
        for(int i = 0; i < list.getRowCount(); i++){
            if(list.getText(i, 0).equals(id)){
                list.removeRow(i);
                return;
            }
        }
    }

    public void resetApplications(List<Application> list){
        editor.resetApplications(list);
    }
}
