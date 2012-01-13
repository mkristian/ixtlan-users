package org.dhamma.users.client.editors;

import org.dhamma.users.client.models.At;
import org.dhamma.users.client.models.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.NumberLabel;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Widget;

import de.mkristian.gwt.rails.editors.UserLabel;

public class AtEditor extends Composite implements Editor<At>{
    
    interface Binder extends UiBinder<Widget, AtEditor> {}

    private static final Binder BINDER = GWT.create(Binder.class);
    
    @Ignore @UiField FlowPanel signature;

    @UiField public NumberLabel<Integer> id;
    @UiField DateLabel createdAt;
    @UiField DateLabel updatedAt;
    @UiField UserLabel<User> modifiedBy;

    @UiField TextBox login;

    @UiField TextBox email;

    @UiField TextBox name;

    @UiField TextBox hashed;

    @UiField TextBox hashed2;

    @UiField TextBox atToken;

    public AtEditor() {
        initWidget(BINDER.createAndBindUi(this));
    }

    public void resetVisibility() {
        this.signature.setVisible(id.getValue() != null && id.getValue() > 0);
    }

    public void setEnabled(boolean enabled) {
        resetVisibility();
        this.login.setEnabled(enabled);
        this.email.setEnabled(enabled);
        this.name.setEnabled(enabled);
        this.hashed.setEnabled(enabled);
        this.hashed2.setEnabled(enabled);
        this.atToken.setEnabled(enabled);
    }
}
