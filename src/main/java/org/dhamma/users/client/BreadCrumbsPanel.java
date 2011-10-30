package org.dhamma.users.client;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.dhamma.users.client.models.User;
import org.dhamma.users.client.restservices.SessionRestService;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
        setStyleName("gwt-rails-breadcrumbs");
        setVisible(false);
        sessionManager.addSessionHandler(new SessionHandler<User>() {

            public void timeout() {
                logout();
                Scheduler.get().scheduleDeferred(new ScheduledCommand() {
                    
                    public void execute() {
                        notice.info("timeout");  
                    }
                });
            }

            public void logout() {
                service.destroy(new MethodCallback<Void>() {
                    public void onSuccess(Method method, Void response) {
                    }
                    public void onFailure(Method method, Throwable exception) {
                    }
                });
                setName(null);
            }

            public void login(User user) {
                setName(user.getName());
            }

            public void accessDenied() {
                notice.warn("access denied");
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
            add(logout);
            add(new Label("Welcome " + name));
            setVisible(true);
        }
        else {
            setVisible(false);
        }
    }
}
