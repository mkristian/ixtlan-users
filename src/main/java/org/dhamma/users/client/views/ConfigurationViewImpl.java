package org.dhamma.users.client.views;

import org.dhamma.users.client.editors.ConfigurationEditor;
import org.dhamma.users.client.models.Configuration;
import org.dhamma.users.client.places.ConfigurationPlace;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulActionEnum;

@Singleton
public class ConfigurationViewImpl extends Composite implements ConfigurationView {

    @UiTemplate("ConfigurationView.ui.xml")
    interface Binder extends UiBinder<Widget, ConfigurationViewImpl> {}
    
    private static Binder BINDER = GWT.create(Binder.class);

    interface EditorDriver extends SimpleBeanEditorDriver<Configuration, ConfigurationEditor> {}

    private final EditorDriver editorDriver = GWT.create(EditorDriver.class);

    @UiField Button editButton;
    @UiField Button showButton;

    @UiField Button saveButton;

    @UiField Panel model;

    @UiField ConfigurationEditor editor;

    private Presenter presenter;

    public ConfigurationViewImpl() {
        initWidget(BINDER.createAndBindUi(this));
        editorDriver.initialize(editor);
    }

    @UiHandler("showButton")
    void onClickShow(ClickEvent e) {
        presenter.goTo(new ConfigurationPlace(RestfulActionEnum.SHOW));
    }

    @UiHandler("editButton")
    void onClickEdit(ClickEvent e) {
        presenter.goTo(new ConfigurationPlace(RestfulActionEnum.EDIT));
    }

    @UiHandler("saveButton")
    void onClickSave(ClickEvent e) {
        presenter.save();
    }
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public void edit(Configuration model) {
        this.editorDriver.edit(model);
    }

    public Configuration flush() {
        return editorDriver.flush();
    }

    public void setEnabled(boolean enabled) {
        editor.setEnabled(enabled);
    }

    public void reset(RestfulAction action) {
        editButton.setVisible(action.name().equals(RestfulActionEnum.SHOW.name()) || 
                action.name().equals(RestfulActionEnum.INDEX.name()));
        saveButton.setVisible(action.name().equals(RestfulActionEnum.EDIT.name()));
        setEnabled(!action.viewOnly());
    }
}
