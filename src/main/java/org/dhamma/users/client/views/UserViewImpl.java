package org.dhamma.users.client.views;


import org.dhamma.users.client.models.User;
import org.dhamma.users.client.places.UserPlace;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulActionEnum;
import de.mkristian.gwt.rails.views.IdentifyableTimestampedView;

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
        presenter.delete();
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
        GWT.log(action.name() + " User" + (id.getValue() > 0 ? "(" + id + ")" : ""));
        newButton.setVisible(!action.name().equals(RestfulActionEnum.NEW.name()));
        createButton.setVisible(action.name().equals(RestfulActionEnum.NEW.name()));
        editButton.setVisible(action.name().equals(RestfulActionEnum.SHOW.name()));
        saveButton.setVisible(action.name().equals(RestfulActionEnum.EDIT.name()));
        deleteButton.setVisible(!action.name().equals(RestfulActionEnum.NEW.name()));
        setEnabled(!action.viewOnly());
    }

    public User retrieveUser() {
        User model = new User();
        model.id = id.getValue();

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
}
