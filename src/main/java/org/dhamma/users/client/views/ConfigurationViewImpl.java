package org.dhamma.users.client.views;

import javax.inject.Inject;

import org.dhamma.users.client.editors.ConfigurationEditor;
import org.dhamma.users.client.models.User;
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
import static de.mkristian.gwt.rails.places.RestfulActionEnum.*;
import de.mkristian.gwt.rails.session.SessionManager;

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

    private final SessionManager<User> session;

    public ConfigurationViewImpl() {
        this(null);
    }

    @Inject
    public ConfigurationViewImpl(SessionManager<User> session) {
        initWidget(BINDER.createAndBindUi(this));
        editorDriver.initialize(editor);
        this.session = session;
    }

    private boolean isAllowed(RestfulActionEnum action){
        return session == null || session.isAllowed(ConfigurationPlace.NAME, action);
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

    public void setup(Presenter presenter, RestfulAction a) {
        RestfulActionEnum action = RestfulActionEnum.valueOf(a);
        this.presenter = presenter;
        editButton.setVisible((action == SHOW || action == INDEX) && isAllowed(EDIT));
        saveButton.setVisible(action == EDIT);
        showButton.setVisible(action == EDIT);
        editor.setEnabled(!action.viewOnly());
    }

    public void edit(Configuration model) {
        this.editorDriver.edit(model);
        this.editor.resetVisibility();
    }

    public Configuration flush() {
        return editorDriver.flush();
    }
}
