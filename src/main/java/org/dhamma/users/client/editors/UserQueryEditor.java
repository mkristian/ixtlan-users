package org.dhamma.users.client.editors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.dhamma.users.client.models.Application;
import org.dhamma.users.client.models.Group;
import org.dhamma.users.client.models.UserQuery;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.mkristian.gwt.rails.editors.IdentifyableListBox;
import de.mkristian.gwt.rails.events.QueryEvent;
import de.mkristian.gwt.rails.events.QueryEventHandler;

public class UserQueryEditor extends Composite implements Editor<UserQuery>{
    
    interface Binder extends UiBinder<Widget, UserQueryEditor> {}

    private static final Binder BINDER = GWT.create(Binder.class);
    
    @UiField TextBox name;

    @UiField IdentifyableListBox<Group> group;

    @UiField IdentifyableListBox<Application> application;
    
    public UserQueryEditor() {
        initWidget(BINDER.createAndBindUi(this));
    }

    public HandlerRegistration addQueryHandler(final QueryEventHandler handler){
        name.addKeyUpHandler(new KeyUpHandler() {
            
            private String name;
            
            public void onKeyUp(KeyUpEvent event) {
                if(!UserQueryEditor.this.name.getValue().equals(name)){
                    name = UserQueryEditor.this.name.getValue();
                    handler.onQueryEvent(new QueryEvent());
                }
            }
        });
        group.addValueChangeHandler(new ValueChangeHandler<Group>() {

            public void onValueChange(ValueChangeEvent<Group> event) {
                handler.onQueryEvent(new QueryEvent());
            }
        });
        application.addValueChangeHandler(new ValueChangeHandler<Application>() {

            public void onValueChange(ValueChangeEvent<Application> event) {
                handler.onQueryEvent(new QueryEvent());
            }
        });
        return addHandler(handler, QueryEvent.getType());
    }
    
    public void resetGroups(List<Group> models){
        if(models == null){
            Group model = new Group() {
                public String toDisplay() { return "loading..."; }
            };
            group.setAcceptableValues(Arrays.asList(model));
        }
        else{
            Group model = new Group() {
                public String toDisplay() { return "please select..."; }
            };
            List<Group> list = new ArrayList<Group>();
            list.add(model);
            list.addAll(models);

            group.setAcceptableValues(list);
        }
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
}