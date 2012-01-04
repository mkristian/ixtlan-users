package org.dhamma.users.client;

import javax.inject.Singleton;

import org.dhamma.users.client.models.Application;
import org.dhamma.users.client.models.User;

import com.google.inject.Inject;

import de.mkristian.gwt.rails.session.SessionManager;
import de.mkristian.gwt.rails.views.LinksPanel;

@Singleton
public class ApplicationLinksPanel extends LinksPanel<User> {

    @Inject
    ApplicationLinksPanel(SessionManager<User> sessionManager) {
        super(sessionManager);
    }

    @Override
    protected void initUser(User user) {
        for(Application app: user.applications){
            addLink(app.getName().equals("THIS") ? 
                    "users" : 
                    app.getName(), app.getUrl());
        }
    }

}