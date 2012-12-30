package de.mkristian.ixtlan.users.client.editors;


import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.NumberLabel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.mkristian.gwt.rails.editors.EnabledEditor;
import de.mkristian.gwt.rails.editors.UserLabel;
import de.mkristian.ixtlan.users.client.models.Application;
import de.mkristian.ixtlan.users.client.models.User;

public class ApplicationEditor extends EnabledEditor<Application>{
    
    interface Binder extends UiBinder<Widget, ApplicationEditor> {}

    private static final Binder BINDER = GWT.create(Binder.class);
    
    @Ignore @UiField FlowPanel signature;

    @UiField public NumberLabel<Integer> id;
    @UiField DateLabel createdAt;
    @UiField DateLabel updatedAt;
    @UiField UserLabel<User> modifiedBy;

    @UiField TextBox name;
    @UiField TextBox url;
    @UiField TextBox authenticationToken;
    @UiField TextBox allowedIp;

    public ApplicationEditor() {
        initWidget(BINDER.createAndBindUi(this));
    }

    public void resetVisibility() {
        this.signature.setVisible(id.getValue() != null && id.getValue() > 0);
    }

    public void setEnabled(boolean enabled) {
        resetVisibility();
        this.name.setEnabled(enabled);
        this.url.setEnabled(enabled);
    }
}
