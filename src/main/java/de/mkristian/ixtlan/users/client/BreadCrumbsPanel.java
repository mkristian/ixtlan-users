package de.mkristian.ixtlan.users.client;

import javax.inject.Inject;
import javax.inject.Singleton;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

import de.mkristian.gwt.rails.Notice;
import de.mkristian.gwt.rails.models.IsUser;
import de.mkristian.gwt.rails.session.SessionManager;
import de.mkristian.ixtlan.users.client.models.User;
import de.mkristian.ixtlan.users.client.restservices.SessionRestService;

@Singleton
public class BreadCrumbsPanel extends FlowPanel {

    private final Button logout;

    @Inject
    public BreadCrumbsPanel(final SessionManager<User> sessionManager, final SessionRestService service,
            final Notice notice){
        setStyleName("gwt-rails-breadcrumbs");
        setVisible(false);
        logout = new Button("logout");
        logout.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                GWT.log(History.getToken());
                sessionManager.logout();
            }
        });
    }

    void initUser(IsUser user){
        clear();
        if(user != null){
            add(new Label("Welcome " + user.getName()));
            add(logout);
            setVisible(true);
        }
        else {
            clear();
            setVisible(false);
        }
    }
}
