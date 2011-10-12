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
import com.google.gwt.user.datepicker.client.DateBox;

import de.mkristian.gwt.rails.editors.UserLabel;
import de.mkristian.gwt.rails.editors.DoubleBox;
import de.mkristian.gwt.rails.editors.IntegerBox;
import de.mkristian.gwt.rails.editors.LongBox;
import de.mkristian.gwt.rails.editors.IdentifyableListBox;

public class ConfigurationEditor extends Composite implements Editor<Configuration>{
    
    interface Binder extends UiBinder<Widget, ConfigurationEditor> {}

    private static final Binder BINDER = GWT.create(Binder.class);
    
    @Ignore @UiField FlowPanel signature;

    @UiField DateLabel createdAt;
    @UiField DateLabel updatedAt;
    @UiField UserLabel<User> modifiedBy;

    @UiField IntegerBox idleSessionTimeout;

    @UiField TextBox passwordFromEmail;

    @UiField TextBox loginUrl;

    public ConfigurationEditor() {
        initWidget(BINDER.createAndBindUi(this));
    }

    public void setEnabled(boolean enabled) {
        this.signature.setVisible(createdAt.getValue() != null);
        this.idleSessionTimeout.setEnabled(enabled);
        this.passwordFromEmail.setEnabled(enabled);
        this.loginUrl.setEnabled(enabled);
    }
}