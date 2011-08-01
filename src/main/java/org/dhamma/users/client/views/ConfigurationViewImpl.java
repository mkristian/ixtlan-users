package org.dhamma.users.client.views;


import java.util.Date;

import de.mkristian.gwt.rails.RestfulAction;
import de.mkristian.gwt.rails.RestfulActionEnum;

import org.dhamma.users.client.models.Configuration;
import org.dhamma.users.client.places.ConfigurationPlace;

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
public class ConfigurationViewImpl extends Composite
        implements ConfigurationView {

    @UiTemplate("ConfigurationView.ui.xml")
    interface ConfigurationViewUiBinder extends UiBinder<Widget, ConfigurationViewImpl> {}
    
    private static ConfigurationViewUiBinder uiBinder = GWT.create(ConfigurationViewUiBinder.class);

    
    @UiField
    Button editButton;

    @UiField
    Button saveButton;

    @UiField
    Label createdAt;

    @UiField
    Label updatedAt;

    @UiField
    TextBox idleSessionTimeout;

    @UiField
    TextBox passwordFromEmail;

    @UiField
    TextBox loginUrl;


    private Presenter presenter;


    private Date createdAtCache;
    private Date updatedAtCache;

    public ConfigurationViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }
    @UiHandler("editButton")
    void onClickEdit(ClickEvent e) {
        presenter.goTo(new ConfigurationPlace(RestfulActionEnum.EDIT));
    }

    @UiHandler("saveButton")
    void onClickSave(ClickEvent e) {
        presenter.save();
    }
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public void reset(Configuration model) {
	//TODO singleton support
        idleSessionTimeout.setText(model.idleSessionTimeout + "");
        passwordFromEmail.setText(model.passwordFromEmail);
        loginUrl.setText(model.loginUrl);
    }

    public void reset(RestfulAction action) {
        GWT.log(action.name() + " Configuration");
        editButton.setVisible(action.name().equals(RestfulActionEnum.SHOW.name()));
        saveButton.setVisible(action.name().equals(RestfulActionEnum.EDIT.name()));
        setEnabled(!action.viewOnly());
    }

    public Configuration retrieveConfiguration() {
        Configuration model = new Configuration();

        model.createdAt = createdAtCache;
        model.updatedAt = updatedAtCache;

        model.idleSessionTimeout = Integer.parseInt(idleSessionTimeout.getText());

        model.passwordFromEmail = passwordFromEmail.getText();

        model.loginUrl = loginUrl.getText();

        return model;
    }

    public void setEnabled(boolean enabled) {
         idleSessionTimeout.setEnabled(enabled);
         passwordFromEmail.setEnabled(enabled);
         loginUrl.setEnabled(enabled);
    }
}
