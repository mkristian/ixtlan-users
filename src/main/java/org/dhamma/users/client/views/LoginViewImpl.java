package org.dhamma.users.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

@Singleton
public class LoginViewImpl extends de.mkristian.gwt.rails.session.LoginViewImpl {

    @UiTemplate("LoginView.ui.xml")
    interface LoginViewUiBinder extends UiBinder<Widget,de.mkristian.gwt.rails.session.LoginViewImpl> {}

    private static LoginViewUiBinder uiBinder = GWT.create(LoginViewUiBinder.class);

    public LoginViewImpl() {
        super(uiBinder);
    }

}