package org.dhamma.users.client.views;


import java.util.Date;

import de.mkristian.gwt.rails.RestfulAction;
import de.mkristian.gwt.rails.RestfulActionEnum;

import org.dhamma.users.client.models.User;
import org.dhamma.users.client.places.UserPlace;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Singleton;

@Singleton
public class UserViewImpl extends Composite
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
    Label id;


    @UiField
    Label createdAt;

    @UiField
    Label updatedAt;

    @UiField
    TextBox login;

    @UiField
    TextBox email;

    @UiField
    TextBox name;


    private Presenter presenter;

    private int idCache;


    private Date createdAtCache;
    private Date updatedAtCache;

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
        presenter.goTo(new UserPlace(idCache, RestfulActionEnum.EDIT));
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
        if(model.id > 0){
            id.setText("id: " + model.id);

            createdAt.setText("created at: "
                    + DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM).format(model.created_at));
            updatedAt.setText("updated at: "
                    + DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM).format(model.updated_at));
        }
        else {
            id.setText(null);

            createdAt.setText(null);
            updatedAt.setText(null);
        }
        this.idCache = model.id;

        this.createdAtCache = model.created_at;
        this.updatedAtCache = model.updated_at;
        login.setText(model.login);
        email.setText(model.email);
        name.setText(model.name);
    }

    public void reset(RestfulAction action) {
        GWT.log(action.name() + " User" + (idCache > 0 ? "(" + id + ")" : ""));
        newButton.setVisible(!action.name().equals(RestfulActionEnum.NEW.name()));
        createButton.setVisible(action.name().equals(RestfulActionEnum.NEW.name()));
        editButton.setVisible(action.name().equals(RestfulActionEnum.SHOW.name()));
        saveButton.setVisible(action.name().equals(RestfulActionEnum.EDIT.name()));
        deleteButton.setVisible(!action.name().equals(RestfulActionEnum.NEW.name()));
        setEnabled(!action.viewOnly());
    }

    public User retrieveUser() {
        User model = new User();
        model.id = idCache;

        model.created_at = createdAtCache;
        model.updated_at = updatedAtCache;

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
