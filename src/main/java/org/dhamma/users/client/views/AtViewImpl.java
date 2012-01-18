package org.dhamma.users.client.views;

import static de.mkristian.gwt.rails.places.RestfulActionEnum.EDIT;
import static de.mkristian.gwt.rails.places.RestfulActionEnum.INDEX;
import static de.mkristian.gwt.rails.places.RestfulActionEnum.SHOW;

import java.util.List;

import javax.inject.Inject;

import org.dhamma.users.client.caches.UsersCache;
import org.dhamma.users.client.editors.AtQueryEditor;
import org.dhamma.users.client.editors.UserEditor;
import org.dhamma.users.client.models.User;
import org.dhamma.users.client.models.UserQuery;
import org.dhamma.users.client.places.AtPlace;
import org.dhamma.users.client.places.AtPlaceTokenizer;
import org.dhamma.users.client.places.UserPlace;
import org.dhamma.users.client.places.UserPlaceTokenizer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

import de.mkristian.gwt.rails.events.QueryEvent;
import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulActionEnum;
import de.mkristian.gwt.rails.session.SessionManager;
import de.mkristian.gwt.rails.views.ModelButton;

@Singleton
public class AtViewImpl extends Composite implements AtView {

    @UiTemplate("AtView.ui.xml")
    interface Binder extends UiBinder<Widget, AtViewImpl> {}
    
    private static Binder BINDER = GWT.create(Binder.class);

    interface EditorDriver extends SimpleBeanEditorDriver<User, UserEditor> {}
    interface QueryEditorDriver extends SimpleBeanEditorDriver<UserQuery, AtQueryEditor> {}

    private final EditorDriver editorDriver = GWT.create(EditorDriver.class);
    private final QueryEditorDriver queryEditorDriver = GWT.create(QueryEditorDriver.class);

    @UiField Button editButton;
    
    @UiField Panel model;
    @UiField FlexTable list;

    @UiField UserEditor editor;

    @UiField AtQueryEditor queryEditor;
    @UiField Button searchButton;

    @UiField HTMLPanel otherAtPanel;

    @UiField Hyperlink otherAtLink;
    @UiField Label otherAtLabel;

    private Presenter presenter;

    private UserQuery query;

    private final UsersCache cache;
    private final SessionManager<User> session;    

    private List<User> users;

    @Inject
    public AtViewImpl(UsersCache cache, SessionManager<User> session) {
        initWidget(BINDER.createAndBindUi(this));
        editorDriver.initialize(editor);
        queryEditorDriver.initialize(queryEditor);
        this.cache = cache;
        this.session = session;
    }

    private boolean isAllowed(RestfulActionEnum action){
        return session == null || session.isAllowed(UserPlace.NAME, action);
    }

    public void setup(Presenter presenter, RestfulAction a) {
        RestfulActionEnum action = RestfulActionEnum.valueOf(a);
        this.presenter = presenter;
        if(action == INDEX){   
            editButton.setVisible(false);
            list.setVisible(true);
            model.setVisible(false);
        }
        else {
            editButton.setVisible(isAllowed(EDIT));
            list.setVisible(false);
            model.setVisible(true);
        }
        editor.setEnabled(!action.viewOnly());
    }

    public void edit(User model) {
        this.editorDriver.edit(model);
        this.editor.resetVisibility(true);
        setupOther(model);
    }

    public void edit(UserQuery query){
        queryEditorDriver.edit(query);
        this.query = query;
        doSearch();
    }

    @UiHandler("editButton")
    void onClickEdit(ClickEvent e) {
        presenter.goTo(new UserPlace(editor.id.getValue(), RestfulActionEnum.EDIT));
    }

    @UiHandler("searchButton")
    void onClickSearch(ClickEvent e) {
        presenter.goTo(new AtPlace(queryEditorDriver.flush().toQuery()));
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

    private void doSearch(){
        if(this.users != null && query != null){
            List<User> models = query.filter(this.users);
        
            list.removeAllRows();
            list.setText(0, 0, "Id");
            list.setText(0, 1, "Login");
            list.setText(0, 2, "Email");
            list.setText(0, 3, "Name");
            list.setText(0, 4, "AT token");
            list.getRowFormatter().addStyleName(0, "gwt-rails-model-list-header");
            int row = 1;
            for(User model: models){
                setRow(row, model);
                row++;
            }
        }
    }


    public void setupOther(User user){
            User other = cache.otherAt(user);
            if (other != null){
                if(other.id == 0){
                    otherAtLink.setVisible(false);
                    otherAtLabel.setVisible(true);
                    otherAtLabel.setText("none");
                }
                else {
                    otherAtLink.setVisible(true);
                    otherAtLabel.setVisible(false);
                    String token = new AtPlaceTokenizer().getToken(new AtPlace(other, RestfulActionEnum.SHOW));
                    otherAtLink.setTargetHistoryToken(token);
                    otherAtLink.setHTML(SafeHtmlUtils.fromString(other.getName()));
                }
            }
            else {
                otherAtLink.setVisible(false);
                otherAtLabel.setVisible(true);
                otherAtLabel.setText("pending . . .");
            }
    }

    public User flush() {
        return editorDriver.flush();
    }

    private final ClickHandler clickHandler = new ClickHandler() {
        
        @SuppressWarnings("unchecked")
        public void onClick(ClickEvent event) {
            ModelButton<User> button = (ModelButton<User>)event.getSource();
            switch(button.action){
                case EDIT:
                    presenter.goTo(new UserPlace(button.model, button.action));
                    break;
                default:
                    presenter.goTo(new AtPlace(button.model, button.action));
            }
        }
    };

    private Button newButton(RestfulActionEnum action, User model){
        ModelButton<User> button = new ModelButton<User>(action, model);
        button.addClickHandler(clickHandler);
        return button;
    }

    public void reset(List<User> models) {
        this.users = models;
        doSearch();
    }

    private void setRow(int row, User model) {
        list.setText(row, 0, model.getId() + "");
        list.setText(row, 1, model.getLogin() + "");
        list.setText(row, 2, model.getEmail() + "");
        list.setText(row, 3, model.getName() + "");
        list.setText(row, 4, model.getAtToken() + "");

        list.setWidget(row, 5, newButton(SHOW, model));
        if (isAllowed(EDIT)) {
            list.setWidget(row, 6, newButton(EDIT, model));
        }
     }
}
