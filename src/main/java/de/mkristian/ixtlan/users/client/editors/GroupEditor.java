package de.mkristian.ixtlan.users.client.editors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

import de.mkristian.gwt.rails.editors.UserLabel;
import de.mkristian.gwt.rails.editors.IdentifyableListBox;
import de.mkristian.ixtlan.users.client.models.Application;
import de.mkristian.ixtlan.users.client.models.Group;
import de.mkristian.ixtlan.users.client.models.User;

public class GroupEditor extends Composite implements Editor<Group>{
    
    interface Binder extends UiBinder<Widget, GroupEditor> {}

    private static final Binder BINDER = GWT.create(Binder.class);
    
    @Ignore @UiField FlowPanel signature;

    @UiField public NumberLabel<Integer> id;
    @UiField DateLabel createdAt;
    @UiField DateLabel updatedAt;
    @UiField UserLabel<User> modifiedBy;

    @UiField TextBox name;

    @UiField TextArea description;

    @UiField IdentifyableListBox<Application> application;

    @UiField CheckBox hasRegions;

    public GroupEditor() {
        initWidget(BINDER.createAndBindUi(this));
    }

    public void resetApplications(List<Application> models){
        if(models == null){
            Application model = new Application() {
                public String toDisplay() { return "loading..."; }
            };
            application.setAcceptableValues(Arrays.asList(model));
        }
        else{
            Application model = new Application() {
                public String toDisplay() { return "please select..."; }
            };
            List<Application> list = new ArrayList<Application>();
            list.add(model);
            list.addAll(models);
            application.setAcceptableValues(list);
        }
    }

    public void resetVisibility() {
        this.signature.setVisible(id.getValue() != null && id.getValue() > 0);
    }

    public void setEnabled(boolean enabled) {
        resetVisibility();
        this.name.setEnabled(enabled);
        this.description.setEnabled(enabled);
        this.application.setEnabled(enabled);
        this.hasRegions.setEnabled(enabled);
    }
}
