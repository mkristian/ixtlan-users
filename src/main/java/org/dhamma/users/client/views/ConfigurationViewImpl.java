package org.dhamma.users.client.views;

import de.mkristian.gwt.rails.views.TimestampedView;
import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulActionEnum;

import org.dhamma.users.client.models.Configuration;
import org.dhamma.users.client.places.ConfigurationPlace;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Singleton;

@Singleton
public class ConfigurationViewImpl extends TimestampedView
        implements ConfigurationView {

    @UiTemplate("ConfigurationView.ui.xml")
    interface ConfigurationViewUiBinder extends UiBinder<Widget, ConfigurationViewImpl> {}
    
    private static ConfigurationViewUiBinder uiBinder = GWT.create(ConfigurationViewUiBinder.class);

    
    @UiField
    Button editButton;

    @UiField
    Button saveButton;
    @UiField
    TextBox idleSessionTimeout;

    @UiField
    TextBox passwordFromEmail;

    @UiField
    TextBox loginUrl;


    private Presenter presenter;

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
        resetSignature(model.createdAt, model.updatedAt);
        idleSessionTimeout.setText(model.idleSessionTimeout + "");
        passwordFromEmail.setText(model.passwordFromEmail);
        loginUrl.setText(model.loginUrl);
    }

    public void reset(RestfulAction action) {
        editButton.setVisible(action.name().equals(RestfulActionEnum.SHOW.name()) || 
                action.name().equals(RestfulActionEnum.INDEX.name()));
        saveButton.setVisible(action.name().equals(RestfulActionEnum.EDIT.name()));
        setEnabled(!action.viewOnly());
    }

    public Configuration retrieveConfiguration() {
        Configuration model = new Configuration();

        model.createdAt = createdAt.getValue();
        model.updatedAt = updatedAt.getValue();

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
