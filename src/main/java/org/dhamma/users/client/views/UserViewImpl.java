package org.dhamma.users.client.views;

import java.util.List;

import de.mkristian.gwt.rails.views.IdentifyableTimestampedView;
import de.mkristian.gwt.rails.views.ModelButton;
import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulActionEnum;

import org.dhamma.users.client.models.User;
import org.dhamma.users.client.places.UserPlace;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Singleton;

@Singleton
public class UserViewImpl extends IdentifyableTimestampedView
        implements UserView {

    @UiTemplate("UserView.ui.xml")
    interface UserViewUiBinder extends UiBinder<Widget, UserViewImpl> {}
    
    private static UserViewUiBinder uiBinder = GWT.create(UserViewUiBinder.class);

    @UiField
    Button newButton;
    
    @UiField
    Button createButton;
    
    @UiField
    Button editButton;

    @UiField
    Button saveButton;
    @UiField
    Button deleteButton;

    @UiField
    TextBox login;

    @UiField
    TextBox email;

    @UiField
    TextBox name;

    @UiField
    Panel form;
    
    @UiField
    FlexTable list;

    private Presenter presenter;

    public UserViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("newButton")
    void onClickNew(ClickEvent e) {
        presenter.goTo(new UserPlace(RestfulActionEnum.NEW));
    }

    @UiHandler("createButton")
    void onClickCreate(ClickEvent e) {
        presenter.create();
    }

    @UiHandler("editButton")
    void onClickEdit(ClickEvent e) {
        presenter.goTo(new UserPlace(id.getValue(), RestfulActionEnum.EDIT));
    }

    @UiHandler("saveButton")
    void onClickSave(ClickEvent e) {
        presenter.save();
    }

    @UiHandler("deleteButton")
    void onClickDelete(ClickEvent e) {
        presenter.delete(retrieveUser());
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public void reset(User model) {

        resetSignature(model.id, model.createdAt, model.updatedAt);
        login.setText(model.login);
        email.setText(model.email);
        name.setText(model.name);
    }

    public void reset(RestfulAction action) {
        newButton.setVisible(!action.name().equals(RestfulActionEnum.NEW.name()));
        if(action.name().equals(RestfulActionEnum.INDEX.name())){
            editButton.setVisible(false);
            list.setVisible(true);
            form.setVisible(false);
        }
        else {
            createButton.setVisible(action.name().equals(RestfulActionEnum.NEW.name()));
            editButton.setVisible(action.name().equals(RestfulActionEnum.SHOW.name()));
            saveButton.setVisible(action.name().equals(RestfulActionEnum.EDIT.name()));
            deleteButton.setVisible(action.name().equals(RestfulActionEnum.EDIT.name()));
            setEnabled(!action.viewOnly());
            list.setVisible(false);
            form.setVisible(true);
        }
    }

    public User retrieveUser() {
        User model = new User();
        model.id = id.getValue() == null ? 0 : id.getValue();

        model.createdAt = createdAt.getValue();
        model.updatedAt = updatedAt.getValue();

        model.login = login.getText();

        model.email = email.getText();

        model.name = name.getText();

        return model;
    }

    public void setEnabled(boolean enabled) {
         login.setEnabled(enabled);
         email.setEnabled(enabled);
         name.setEnabled(enabled);
    }

    private final ClickHandler clickHandler = new ClickHandler() {
        
        @SuppressWarnings("unchecked")
        public void onClick(ClickEvent event) {
            ModelButton<User> button = (ModelButton<User>)event.getSource();
            switch(button.action){
                case DESTROY:
                    presenter.delete(button.model);
                    break;
                default:
                    presenter.goTo(new UserPlace(button.model, button.action));
            }
        }
    };

    private Button newButton(RestfulActionEnum action, User model){
        ModelButton<User> button = new ModelButton<User>(action, model);
        button.addClickHandler(clickHandler);
        return button;
    }

    public void reset(List<User> models) {
        list.removeAllRows();
        list.setText(0, 0, "Id");
        list.setText(0, 1, "Login");
        list.setText(0, 2, "Email");
        list.setText(0, 3, "Name");
        list.getRowFormatter().addStyleName(0, "model-list-header");
        int row = 1;
        for(User model: models){
            setRow(row, model);
            row++;
        }
    }

    private void setRow(int row, User model) {
        list.setText(row, 0, model.id + "");
        list.setText(row, 1, model.login + "");

        list.setText(row, 2, model.email + "");

        list.setText(row, 3, model.name + "");

        list.setWidget(row, 4, newButton(RestfulActionEnum.SHOW, model));
        list.setWidget(row, 5, newButton(RestfulActionEnum.EDIT, model));
        list.setWidget(row, 6, newButton(RestfulActionEnum.DESTROY, model));
    }

    public void updateInList(User model) {
        String id = model.id + "";
        for(int i = 0; i < list.getRowCount(); i++){
            if(list.getText(i, 0).equals(id)){
                setRow(i, model);
                return;
            }
        }
    }

    public void removeFromList(User model) {
        String id = model.id + "";
        for(int i = 0; i < list.getRowCount(); i++){
            if(list.getText(i, 0).equals(id)){
                list.removeRow(i);
                return;
            }
        }
    }

    public void addToList(User model) {
        setRow(list.getRowCount(), model);
    }
}
