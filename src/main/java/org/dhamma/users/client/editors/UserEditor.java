package org.dhamma.users.client.editors;

import java.util.Arrays;
import java.util.List;

import org.dhamma.users.client.models.Group;
import org.dhamma.users.client.models.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.NumberLabel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.mkristian.gwt.rails.editors.IdentifyableCheckBoxes;
import de.mkristian.gwt.rails.editors.UserLabel;

public class UserEditor extends Composite implements Editor<User>{
    
    interface Binder extends UiBinder<Widget, UserEditor> {}

    private static final Binder BINDER = GWT.create(Binder.class);
    
    @Ignore @UiField FlowPanel signature;

    @UiField public NumberLabel<Integer> id;
    @UiField DateLabel createdAt;
    @UiField DateLabel updatedAt;
    @UiField UserLabel<User> modifiedBy;

    @UiField TextBox login;

    @UiField TextBox email;

    @UiField TextBox name;

    @UiField IdentifyableCheckBoxes<Group> groups;
    
    public UserEditor() {
        initWidget(BINDER.createAndBindUi(this));
    }

    public void resetGroups(List<Group> models){
        if(models == null){
            Group model = new Group() {
                public String toDisplay() { return "loading..."; }
            };
            groups.setAcceptableValues(Arrays.asList(model));
        }
        else{
            groups.setAcceptableValues(models);
        }
    }

    public void setEnabled(boolean enabled) {
        this.signature.setVisible(id.getValue() != null && id.getValue() > 0);
        this.login.setEnabled(id.getValue() == null || id.getValue() == 0);
        this.email.setEnabled(id.getValue() == null || id.getValue() == 0);
        this.name.setEnabled(enabled);
        this.groups.setEnabled(enabled);
    }
}