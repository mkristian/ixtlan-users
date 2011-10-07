package org.dhamma.users.client.editors;

import org.dhamma.users.client.models.Group;

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
import com.google.gwt.user.datepicker.client.DateBox;

import de.mkristian.gwt.rails.editors.DoubleBox;
import de.mkristian.gwt.rails.editors.IntegerBox;
import de.mkristian.gwt.rails.editors.LongBox;
import de.mkristian.gwt.rails.editors.IdentifyableListBox;

public class GroupEditor extends Composite implements Editor<Group>{
    
    interface Binder extends UiBinder<Widget, GroupEditor> {}

    private static final Binder BINDER = GWT.create(Binder.class);
    
    @Ignore @UiField FlowPanel signature;

    @UiField public NumberLabel<Integer> id;
    @UiField DateLabel createdAt;
    @UiField DateLabel updatedAt;

    @UiField TextBox name;

    public GroupEditor() {
        initWidget(BINDER.createAndBindUi(this));
    }

    public void setEnabled(boolean enabled) {
        this.signature.setVisible(id.getValue() != null && id.getValue() > 0);
        this.name.setEnabled(enabled);
    }
}