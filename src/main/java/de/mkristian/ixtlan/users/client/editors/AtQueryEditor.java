package de.mkristian.ixtlan.users.client.editors;


import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.mkristian.gwt.rails.events.QueryEvent;
import de.mkristian.gwt.rails.events.QueryEventHandler;
import de.mkristian.ixtlan.users.client.models.UserQuery;

public class AtQueryEditor extends Composite implements Editor<UserQuery>{
    
    interface Binder extends UiBinder<Widget, AtQueryEditor> {}

    private static final Binder BINDER = GWT.create(Binder.class);
    
    @UiField TextBox name;
    
    public AtQueryEditor() {
        initWidget(BINDER.createAndBindUi(this));
    }

    public HandlerRegistration addQueryHandler(final QueryEventHandler handler){
        name.addKeyUpHandler(new KeyUpHandler() {
            
            private String name;
            
            public void onKeyUp(KeyUpEvent event) {
                if(!AtQueryEditor.this.name.getValue().equals(name)){
                    name = AtQueryEditor.this.name.getValue();
                    handler.onQueryEvent(new QueryEvent());
                }
            }
        });
        return addHandler(handler, QueryEvent.getType());
    }
    
}