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
import de.mkristian.gwt.rails.editors.IdentifiableListBox;
import de.mkristian.ixtlan.users.client.models.Application;
import de.mkristian.ixtlan.users.client.models.RemotePermission;
import de.mkristian.ixtlan.users.client.models.User;

public class RemotePermissionEditor extends Composite implements Editor<RemotePermission>{
    
    interface Binder extends UiBinder<Widget, RemotePermissionEditor> {}

    private static final Binder BINDER = GWT.create(Binder.class);
    
    @Ignore @UiField FlowPanel signature;

    @UiField public NumberLabel<Integer> id;
    @UiField DateLabel createdAt;
    @UiField DateLabel updatedAt;
    @UiField UserLabel<User> modifiedBy;

    @UiField TextBox ip;

    @UiField TextBox token;

    @UiField IdentifiableListBox<Application> application;

    public RemotePermissionEditor() {
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
        this.ip.setEnabled(enabled);
        this.token.setEnabled(enabled);
        this.application.setEnabled(enabled);
    }
}
