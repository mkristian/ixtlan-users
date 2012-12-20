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
import de.mkristian.ixtlan.users.client.models.Domain;
import de.mkristian.ixtlan.users.client.models.User;

public class DomainEditor extends EnabledEditor<Domain>{
    
    interface Binder extends UiBinder<Widget, DomainEditor> {}

    private static final Binder BINDER = GWT.create(Binder.class);
    
    @Ignore @UiField FlowPanel signature;

    @UiField NumberLabel<Integer> id;
    @UiField DateLabel createdAt;
    @UiField DateLabel updatedAt;
    @UiField UserLabel<User> modifiedBy;

    @UiField TextBox name;

    public DomainEditor() {
        initWidget(BINDER.createAndBindUi(this));
    }

    public void setEnabled(boolean enabled) {
        this.signature.setVisible(id.getValue() != null && id.getValue() > 0);
        this.name.setEnabled(enabled);
    }
}
