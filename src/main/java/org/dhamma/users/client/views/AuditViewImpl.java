package org.dhamma.users.client.views;

import java.util.List;


import org.dhamma.users.client.editors.AuditEditor;
import org.dhamma.users.client.models.Audit;
import org.dhamma.users.client.places.AuditPlace;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulActionEnum;
import static de.mkristian.gwt.rails.places.RestfulActionEnum.*;
import de.mkristian.gwt.rails.views.ModelButton;

@Singleton
public class AuditViewImpl extends Composite implements AuditView {

    @UiTemplate("AuditView.ui.xml")
    interface Binder extends UiBinder<Widget, AuditViewImpl> {}
    
    private static Binder BINDER = GWT.create(Binder.class);

    interface EditorDriver extends SimpleBeanEditorDriver<Audit, AuditEditor> {}

    private final EditorDriver editorDriver = GWT.create(EditorDriver.class);


    @UiField Panel model;
    @UiField FlexTable list;

    @UiField AuditEditor editor;

    private Presenter presenter;

    public AuditViewImpl() {
        initWidget(BINDER.createAndBindUi(this));
        editorDriver.initialize(editor);
    }

    public void setup(Presenter presenter, RestfulAction a) {
        RestfulActionEnum action = RestfulActionEnum.valueOf(a);
        this.presenter = presenter;
        if(action == INDEX){
            list.setVisible(true);
            model.setVisible(false);
        }
        else {
            list.setVisible(false);
            model.setVisible(true);
        }
        editor.setEnabled(!action.viewOnly());
    }

    public void edit(Audit model) {
        this.editorDriver.edit(model);
        this.editor.resetVisibility();
    }

    public Audit flush() {
        return editorDriver.flush();
    }

    private final ClickHandler clickHandler = new ClickHandler() {
        
        @SuppressWarnings("unchecked")
        public void onClick(ClickEvent event) {
            ModelButton<Audit> button = (ModelButton<Audit>)event.getSource();
            switch(button.action){
                default:
                    presenter.goTo(new AuditPlace(button.model, button.action));
            }
        }
    };

    private Button newButton(RestfulActionEnum action, Audit model){
        ModelButton<Audit> button = new ModelButton<Audit>(action, model);
        button.addClickHandler(clickHandler);
        return button;
    }

    public void reset(List<Audit> models) {
        list.removeAllRows();
        list.setText(0, 0, "Id");
        list.setText(0, 1, "Login");
        list.setText(0, 2, "Message");
        list.getRowFormatter().addStyleName(0, "gwt-rails-model-list-header");
        int row = 1;
        for(Audit model: models){
            setRow(row, model);
            row++;
        }
    }

    private void setRow(int row, Audit model) {
        list.setText(row, 0, model.getId() + "");
        list.setText(row, 1, model.getLogin() + "");
        list.setText(row, 2, model.getMessage() + "");

        list.setWidget(row, 3, newButton(RestfulActionEnum.SHOW, model));
    }
}
