package org.dhamma.users.client.views;

import static de.mkristian.gwt.rails.places.RestfulActionEnum.DESTROY;
import static de.mkristian.gwt.rails.places.RestfulActionEnum.EDIT;
import static de.mkristian.gwt.rails.places.RestfulActionEnum.INDEX;
import static de.mkristian.gwt.rails.places.RestfulActionEnum.NEW;
import static de.mkristian.gwt.rails.places.RestfulActionEnum.SHOW;

import java.util.List;

import javax.inject.Inject;

import org.dhamma.users.client.editors.UserEditor;
import org.dhamma.users.client.editors.UserQueryEditor;
import org.dhamma.users.client.models.Application;
import org.dhamma.users.client.models.Group;
import org.dhamma.users.client.models.Region;
import org.dhamma.users.client.models.User;
import org.dhamma.users.client.models.UserQuery;
import org.dhamma.users.client.places.AtPlace;
import org.dhamma.users.client.places.UserPlace;
import org.dhamma.users.client.places.UserPlaceTokenizer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

import de.mkristian.gwt.rails.events.QueryEvent;
import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulActionEnum;
import de.mkristian.gwt.rails.session.SessionManager;
import de.mkristian.gwt.rails.views.GeneralModelButton;

@Singleton
public class UserViewImpl extends Composite implements UserView {

    @UiTemplate("UserView.ui.xml")
    interface Binder extends UiBinder<Widget, UserViewImpl> {}
    
    private static Binder BINDER = GWT.create(Binder.class);

    interface EditorDriver extends SimpleBeanEditorDriver<User, UserEditor> {}
    interface QueryEditorDriver extends SimpleBeanEditorDriver<UserQuery, UserQueryEditor> {}

    private final EditorDriver editorDriver = GWT.create(EditorDriver.class);
    private final QueryEditorDriver queryEditorDriver = GWT.create(QueryEditorDriver.class);

    @UiField Button newButton;
    @UiField Button editButton;
    @UiField Button showButton;
    @UiField Button showAtButton;

    @UiField Button createButton;
    @UiField Button saveButton;
    @UiField Button deleteButton;

    @UiField Panel model;
    @UiField FlexTable list;

    @UiField UserEditor editor;
    
    @UiField UserQueryEditor queryEditor;
    @UiField Button searchButton;
    
    private Presenter presenter;

    private UserQuery query;

    private List<User> users;    

    private final SessionManager<User> session;

    public UserViewImpl() {
        this(null);
    }

    @Inject
    public UserViewImpl(SessionManager<User> session) {
        initWidget(BINDER.createAndBindUi(this));
        editorDriver.initialize(editor);
        queryEditorDriver.initialize(queryEditor);
        this.session = session;
    }

    @UiHandler("newButton")
    void onClickNew(ClickEvent e) {
        presenter.goTo(new UserPlace(RestfulActionEnum.NEW));
    }

    @UiHandler("showButton")
    void onClickShow(ClickEvent e) {
        presenter.goTo(new UserPlace(editor.id.getValue(), RestfulActionEnum.SHOW));
    }

    @UiHandler("showAtButton")
    void onClickShowAt(ClickEvent e) {
        presenter.goTo(new AtPlace(editor.id.getValue(), RestfulActionEnum.SHOW));
    }

    @UiHandler("editButton")
    void onClickEdit(ClickEvent e) {
        presenter.goTo(new UserPlace(editor.id.getValue(), RestfulActionEnum.EDIT));
    }

    @UiHandler("createButton")
    void onClickCreate(ClickEvent e) {
        presenter.create();
    }

    @UiHandler("saveButton")
    void onClickSave(ClickEvent e) {
        presenter.save();
    }

    @UiHandler("deleteButton")
    void onClickDelete(ClickEvent e) {
        presenter.delete(flush());
    }

    @UiHandler("searchButton")
    void onClickSearch(ClickEvent e) {
        presenter.goTo(new UserPlace(queryEditorDriver.flush().toQuery()));
    }

    @UiHandler("queryEditor")
    void onQuery(QueryEvent e){
        search();
    }
    
    private void search(){
        query  = queryEditorDriver.flush();
        String token = query.toQuery();
        if(!History.getToken().equals(token)){
            History.newItem(UserPlace.NAME + 
                UserPlaceTokenizer.QUERY_SEPARATOR + 
                token, false);
            doSearch();
        }
    }
    
    private boolean isAllowed(RestfulActionEnum action){
        return session == null || session.isAllowed(UserPlace.NAME, action);
    }

    public void setup(Presenter presenter, RestfulAction a) {
        RestfulActionEnum action = RestfulActionEnum.valueOf(a);
        this.presenter = presenter;
        newButton.setVisible(action != NEW && isAllowed(NEW));
        if(action == INDEX){
            editButton.setVisible(false);
            showButton.setVisible(false);
            showAtButton.setVisible(false);
            searchButton.setVisible(false);
            list.setVisible(true);
            model.setVisible(false);
        }
        else {
            createButton.setVisible(action == NEW);
            editButton.setVisible(action == SHOW && isAllowed(EDIT));
            showButton.setVisible(action == EDIT);
            showAtButton.setVisible(action != NEW);
            saveButton.setVisible(action == EDIT);
            deleteButton.setVisible(action == EDIT && isAllowed(DESTROY));
            searchButton.setVisible(true);
            list.setVisible(false);
            model.setVisible(true);
        }
        // Ternary value for the editor
        // false/true the usual boolean meaning, null which tells the editor to allow
        // to change ALL fields of model
        editor.setEnabled(action.viewOnly() ? Boolean.FALSE : 
            (session.isAllowed(UserPlace.NAME, "change") || action == NEW ? null : Boolean.TRUE));
    }
    
    public void edit(User model) {
        this.editor.reset();
        this.editorDriver.edit(model);
        boolean showAtToken = session.isAllowed(UserPlace.NAME, "show_at_token");
        this.editor.resetVisibility(showAtToken ? 
                UserEditor.Display.SHOW_AT : 
                UserEditor.Display.HIDE_AT);
        showAtButton.setVisible(model.isAt() && showAtToken);
    }

    public User flush() {
        return editorDriver.flush();
    }

    public void edit(UserQuery query){
        queryEditorDriver.edit(query);
        this.query = query;
        doSearch();
    }

    private final ClickHandler clickHandler = new ClickHandler() {
        
        @SuppressWarnings("unchecked")
        public void onClick(ClickEvent event) {
            GeneralModelButton<User, RestfulActionEnum> button = (GeneralModelButton<User, RestfulActionEnum>)event.getSource();
            switch(button.action){
                case DESTROY:
                    presenter.delete(button.model);
                    break;
                default:
                    
                    presenter.goTo(new UserPlace(button.model, button.action));
            }
        }
    };

    private Button newButton(RestfulActionEnum action, User model){
        GeneralModelButton<User, RestfulActionEnum> button = new GeneralModelButton<User, RestfulActionEnum>(action, model);
        button.addClickHandler(clickHandler);
        return button;
    }

    public void reset(List<User> models) {
        this.users = models;
        doSearch();
    }

    private void doSearch(){
        if(this.users != null && query != null){
            List<User> models = query.filter(this.users);
        
            list.removeAllRows();
            list.setText(0, 0, "Id");
            list.setText(0, 1, "Login");
            list.setText(0, 2, "Email");
            list.setText(0, 3, "Name");
            list.getRowFormatter().addStyleName(0, "model-list-header");
            int row = 1;
            for (User model : models) {
                setRow(row, model);
                row++;
            }
        }
    }

    private void setRow(int row, User model) {
        list.setText(row, 0, model.getId() + "");
        list.setText(row, 1, model.getLogin() + "");

        list.setText(row, 2, model.getEmail() + "");

        list.setText(row, 3, model.getName() + "");

        list.setWidget(row, 4, newButton(SHOW, model));
        if (isAllowed(EDIT)) {
            list.setWidget(row, 5, newButton(EDIT, model));
            // assume if edit is not allowed so is destroy !
            if (isAllowed(DESTROY)) {
                list.setWidget(row, 6, newButton(DESTROY, model));
            }
        }
    }

    public void removeFromList(User model) {
        String id = model.getId() + "";
        for(int i = 0; i < list.getRowCount(); i++){
            if(list.getText(i, 0).equals(id)){
                list.removeRow(i);
                return;
            }
        }
    }

    public void resetGroups(List<Group> groups) {
        this.editor.resetGroups(groups);
        this.queryEditor.resetGroups(groups);
    }

    public void resetApplications(List<Application> applications) {
        this.editor.resetApplications(applications);
        this.queryEditor.resetApplications(applications);
    }

    public void resetRegions(List<Region> regions) {
        GWT.log("regions: " + regions);

        this.editor.resetRegions(regions);
    }
}
