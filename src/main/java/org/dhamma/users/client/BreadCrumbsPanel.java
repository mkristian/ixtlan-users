package org.dhamma.users.client;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.dhamma.users.client.models.User;
import org.dhamma.users.client.restservices.SessionRestService;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

import de.mkristian.gwt.rails.Notice;
import de.mkristian.gwt.rails.session.SessionHandler;
import de.mkristian.gwt.rails.session.SessionManager;

@Singleton
public class BreadCrumbsPanel extends FlowPanel {

    private final Button logout;

    @Inject
    public BreadCrumbsPanel(final SessionManager<User> sessionManager, final SessionRestService service,
            final Notice notice){
        setVisible(false);
        sessionManager.addSessionHandler(new SessionHandler<User>() {

            public void timeout() {
                notice.setText("timeout");
                setName(null);
                History.fireCurrentHistoryState();
            }

            public void logout() {
                service.destroy(new MethodCallback<Void>() {
                    public void onSuccess(Method method, Void response) {
                    }
                    public void onFailure(Method method, Throwable exception) {
                    }
                });
                setName(null);
                History.fireCurrentHistoryState();
            }

            public void login(User user) {
                setName(user.name);
                History.fireCurrentHistoryState();
            }

            public void accessDenied() {
                notice.setText("access denied");
            }
        });
        logout = new Button("logout");
        logout.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                sessionManager.logout();
            }
        });
    }

    private void setName(String name){
        clear();
        if(name != null){
            add(new Label("Welcome " + name));
            add(logout);
            setVisible(true);
        }
        else {
            setVisible(false);
        }
    }
}