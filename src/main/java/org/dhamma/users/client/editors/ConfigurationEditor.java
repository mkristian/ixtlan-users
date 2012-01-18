package org.dhamma.users.client.editors;

import org.dhamma.users.client.models.Configuration;
import org.dhamma.users.client.models.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Widget;

import de.mkristian.gwt.rails.editors.UserLabel;
import de.mkristian.gwt.rails.editors.IntegerBox;

public class ConfigurationEditor extends Composite implements Editor<Configuration>{
    
    interface Binder extends UiBinder<Widget, ConfigurationEditor> {}

    private static final Binder BINDER = GWT.create(Binder.class);
    
    @Ignore @UiField FlowPanel signature;

    @UiField DateLabel createdAt;
    @UiField DateLabel updatedAt;
    @UiField UserLabel<User> modifiedBy;

    @UiField IntegerBox errorsKeepDumps;

    @UiField TextBox errorsBaseUrl;

    @UiField TextBox errorsFromEmail;

    @UiField TextBox errorsToEmails;

    @UiField IntegerBox idleSessionTimeout;

    @UiField IntegerBox auditsKeepLogs;

    @UiField TextBox fromEmail;

    @UiField TextBox profileUrl;

    @UiField TextBox atsUrl;

    public ConfigurationEditor() {
        initWidget(BINDER.createAndBindUi(this));
    }

    public void resetVisibility() {
        this.signature.setVisible(createdAt.getValue() != null);
    }

    public void setEnabled(boolean enabled) {
        resetVisibility();
        this.errorsKeepDumps.setEnabled(enabled);
        this.errorsBaseUrl.setEnabled(enabled);
        this.errorsFromEmail.setEnabled(enabled);
        this.errorsToEmails.setEnabled(enabled);
        this.idleSessionTimeout.setEnabled(enabled);
        this.auditsKeepLogs.setEnabled(enabled);
        this.fromEmail.setEnabled(enabled);
        this.profileUrl.setEnabled(enabled);
        this.atsUrl.setEnabled(enabled);
    }
}
