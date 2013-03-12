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
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.NumberLabel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.mkristian.gwt.rails.editors.UserLabel;
import de.mkristian.ixtlan.users.client.models.Group;
import de.mkristian.ixtlan.users.client.models.User;

public class GroupEditor extends Composite implements Editor<Group>{
    
    interface Binder extends UiBinder<Widget, GroupEditor> {}

    private static final Binder BINDER = GWT.create(Binder.class);
    
    @Ignore @UiField FlowPanel signature;

    @UiField NumberLabel<Integer> id = new NumberLabel<Integer>();
    @UiField DateLabel createdAt;
    @UiField DateLabel updatedAt;
    @UiField UserLabel<User> modifiedBy;

    @UiField FlexTable table;
    
    TextBox name = new TextBox();
    CheckBox hasRegions = new CheckBox();
    CheckBox hasLocales = new CheckBox();
    CheckBox hasDomains = new CheckBox();
    
    public GroupEditor() {
        initWidget(BINDER.createAndBindUi(this));
        this.id.setVisible( false );
        table.setWidget( 0, 0, name );
        table.setText( 1, 0, "has regions" );
        table.setWidget( 1, 1, hasRegions );
        table.setText( 1, 2, "has locales" );
        table.setWidget( 1, 3, hasLocales );
        table.setText( 1, 4, "has domains" );
        table.setWidget( 1, 5, hasDomains );
        table.getFlexCellFormatter().setColSpan( 0, 0, 5 );
        table.getFlexCellFormatter().setVerticalAlignment(1, 0, HasAlignment.ALIGN_TOP );
        table.getFlexCellFormatter().setVerticalAlignment(1, 1, HasAlignment.ALIGN_TOP );
        table.getFlexCellFormatter().setVerticalAlignment(1, 2, HasAlignment.ALIGN_TOP );
        table.getFlexCellFormatter().setVerticalAlignment(1, 3, HasAlignment.ALIGN_TOP );
        table.getFlexCellFormatter().setVerticalAlignment(1, 4, HasAlignment.ALIGN_TOP );
        table.getFlexCellFormatter().setVerticalAlignment(1, 5, HasAlignment.ALIGN_TOP );
    }

    public void setIsNew( boolean isNew ) {
        this.signature.setVisible( ! isNew );
    }
}
