package de.mkristian.ixtlan.users.client.editors;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.NumberLabel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.mkristian.gwt.rails.editors.UserLabel;
import de.mkristian.ixtlan.users.client.models.Application;
import de.mkristian.ixtlan.users.client.models.Group;
import de.mkristian.ixtlan.users.client.models.Region;
import de.mkristian.ixtlan.users.client.models.User;

public class UserEditor extends Composite implements Editor<User>{
    
    List<Group> EMPTY_GROUPS = Collections.emptyList();
    
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

    @UiField HTMLPanel atTokenPanel;
    
    @UiField TextBox atToken;
    
    @UiField HTMLPanel groupsPanel;
    
    @UiField GroupCheckBoxes groups;
    
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

    public void reset(){
        groups.setValue(EMPTY_GROUPS);
    }
    
    public enum Display { IS_AT_DISPLAY, SHOW_AT, HIDE_AT, OMIT  }
    public void resetVisibility(Display display) {
        this.signature.setVisible(id.getValue() != null && id.getValue() > 0);
        switch(display){
            case IS_AT_DISPLAY:         
                this.groups.setVisible(false);
                this.groupsPanel.setVisible(false);
                this.atTokenPanel.setVisible(true);
                break;
            case SHOW_AT:
                this.atTokenPanel.setVisible(true);
                break;
            case HIDE_AT:
                this.atTokenPanel.setVisible(false);
                break;
            case OMIT:
                // leave it as is
        }
    }
    
    public void setEnabled(Boolean enabled) {
        resetVisibility(this.groups.isVisible() ? 
                    Display.OMIT : 
                    Display.IS_AT_DISPLAY);
        
        boolean all = enabled == null;
        this.login.setEnabled(all);
        this.email.setEnabled(all);
        
        boolean others = enabled == Boolean.TRUE || enabled == null;
        this.name.setEnabled(others);
        this.atToken.setEnabled(others);
        this.groups.setEnabled(others);
    }

    public void resetApplications(List<Application> applications) {
        groups.resetApplications(applications);
    }

    public void resetRegions(List<Region> regions) {
        groups.resetRegions(regions);
    }
}