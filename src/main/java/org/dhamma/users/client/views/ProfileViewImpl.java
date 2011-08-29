package org.dhamma.users.client.views;

import org.dhamma.users.client.models.Profile;
import org.dhamma.users.client.places.ProfilePlace;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulActionEnum;
import de.mkristian.gwt.rails.views.TimestampedView;

@Singleton
public class ProfileViewImpl extends TimestampedView
        implements ProfileView {

    @UiTemplate("ProfileView.ui.xml")
    interface ProfileViewUiBinder extends UiBinder<Widget, ProfileViewImpl> {}
    
    private static ProfileViewUiBinder uiBinder = GWT.create(ProfileViewUiBinder.class);

    
    @UiField
    Button editButton;

    @UiField
    Button saveButton;
    @UiField
    TextBox email;

    @UiField
    TextBox name;

    @UiField
    TextBox openidIdentifier;

    @UiField
    PasswordTextBox password;

    @UiField
    Panel form;

    private Presenter presenter;

    public ProfileViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("editButton")
    void onClickEdit(ClickEvent e) {
        presenter.goTo(new ProfilePlace(RestfulActionEnum.EDIT));
    }

    @UiHandler("saveButton")
    void onClickSave(ClickEvent e) {
        presenter.save();
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public void reset(Profile model) {
        resetSignature(model.createdAt, model.updatedAt);
        email.setText(model.email);
        name.setText(model.name);
        openidIdentifier.setText(model.openidIdentifier);
        password.setText(null);
    }

    public void reset(RestfulAction action) {
        editButton.setVisible(action.name().equals(RestfulActionEnum.SHOW.name()) || 
                action.name().equals(RestfulActionEnum.INDEX.name()));
        saveButton.setVisible(action.name().equals(RestfulActionEnum.EDIT.name()));
        setEnabled(!action.viewOnly());
    }

    public Profile retrieveProfile() {
        Profile model = new Profile();

        model.createdAt = createdAt.getValue();
        model.updatedAt = updatedAt.getValue();

        model.email = email.getText();

        model.name = name.getText();

        model.openidIdentifier = openidIdentifier.getText();

        model.password = password.getText();

        return model;
    }

    public void setEnabled(boolean enabled) {
         email.setEnabled(enabled);
         name.setEnabled(enabled);
         openidIdentifier.setEnabled(enabled);
         password.setEnabled(enabled);
    }
}
