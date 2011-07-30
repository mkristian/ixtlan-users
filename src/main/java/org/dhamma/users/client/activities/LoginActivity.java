package org.dhamma.users.client.activities;

import javax.inject.Inject;

import org.dhamma.users.client.models.User;
import org.dhamma.users.client.places.LoginPlace;
import org.dhamma.users.client.restservices.SessionRestService;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.assistedinject.Assisted;

import de.mkristian.gwt.rails.session.Authentication;
import de.mkristian.gwt.rails.session.LoginView;
import de.mkristian.gwt.rails.session.Session;
import de.mkristian.gwt.rails.session.SessionManager;

public class LoginActivity extends AbstractActivity implements LoginView.Presenter{

    private final SessionRestService service;
    private final LoginView view;
    private final SessionManager<User> sessionManager;

    @Inject
    public LoginActivity(@Assisted LoginPlace place,
            LoginView view,
            SessionRestService service,
            SessionManager<User> sessionManager) {
        this.view = view;
        this.service = service;
        this.sessionManager = sessionManager;
    }

    public void start(AcceptsOneWidget display, EventBus eventBus) {
        display.setWidget(view.asWidget());
        view.setPresenter(this);
    }

    public void login(final String login, String password) {
        Authentication authentication = new Authentication(login, password);
        service.create(authentication, new MethodCallback<Session<User>>() {

            public void onSuccess(Method method, Session<User> session) {
                GWT.log("logged in: " + login);
                sessionManager.login(session);
            }

            public void onFailure(Method method, Throwable exception) {
                GWT.log("login failed: " + exception.getMessage(), exception);
                sessionManager.accessDenied();
            }
        });
    }
}