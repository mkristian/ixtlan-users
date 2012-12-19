package de.mkristian.ixtlan.users.client.editors;


import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.mkristian.gwt.rails.editors.EnabledEditor;
import de.mkristian.ixtlan.users.client.models.Profile;


public class ProfileEditor extends EnabledEditor<Profile>{
    
    interface Binder extends UiBinder<Widget, ProfileEditor> {}

    private static final Binder BINDER = GWT.create(Binder.class);
    
    @Ignore @UiField FlowPanel signature;

    @UiField DateLabel createdAt;
    @UiField DateLabel updatedAt;

    @UiField TextBox name;

    @UiField TextBox email;

    @UiField PasswordTextBox password;

    public ProfileEditor() {
        initWidget(BINDER.createAndBindUi(this));
    }

    public void resetVisibility() {
        this.signature.setVisible(createdAt.getValue() != null);
    }

    public void setEnabled(boolean enabled) {
        resetVisibility();
        this.name.setEnabled(enabled);
        this.email.setEnabled(enabled);
        this.password.setEnabled(enabled);
    }
}