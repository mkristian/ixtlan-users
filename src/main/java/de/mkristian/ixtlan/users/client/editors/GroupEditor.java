package de.mkristian.ixtlan.users.client.editors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.NumberLabel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.mkristian.gwt.rails.editors.UserLabel;
import de.mkristian.ixtlan.users.client.models.Group;
import de.mkristian.ixtlan.users.client.models.User;

public class GroupEditor extends Composite implements Editor<Group>{
    
    interface Binder extends UiBinder<Widget, GroupEditor> {}

    private static final Binder BINDER = GWT.create(Binder.class);
    
    @Ignore @UiField FlowPanel signature;

    NumberLabel<Integer> id = new NumberLabel<Integer>();
    @UiField DateLabel createdAt;
    @UiField DateLabel updatedAt;
    @UiField UserLabel<User> modifiedBy;

    @UiField FlexTable table;
    
    TextBox name = new TextBox();
    CheckBox hasRegions = new CheckBox();
    CheckBox hasLocales = new CheckBox();
    CheckBox hasDomains = new CheckBox();

    @UiField TextArea description;
    
    public GroupEditor() {
        initWidget(BINDER.createAndBindUi(this));
        this.id.setVisible( false );
        table.setWidget( 0, 0, name );
        table.setWidget( 0, 1, hasRegions );
        table.setWidget( 0, 2, hasLocales );
        table.setWidget( 0, 3, hasDomains );
    }

    
    public void setEnabled(boolean enabled) {
        this.id.setVisible( false );
        this.signature.setVisible(id.getValue() != null && id.getValue() > 0);
        this.name.setEnabled(enabled);
        this.description.setEnabled(enabled);
        this.hasRegions.setEnabled(enabled);
    }
}
