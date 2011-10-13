package org.dhamma.users.client.managed;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.dhamma.users.client.models.User;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.place.shared.PlaceController;

import de.mkristian.gwt.rails.places.RestfulActionEnum;
import de.mkristian.gwt.rails.session.SessionManager;
import de.mkristian.gwt.rails.views.MenuPanel;

@Singleton
public class UsersMenuPanel extends MenuPanel<User> {

    @Inject
    UsersMenuPanel(final PlaceController placeController, SessionManager<User> sessionManager){
        super(sessionManager);
        addButton("Applications").addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                placeController.goTo(new org.dhamma.users.client.places.ApplicationPlace(RestfulActionEnum.INDEX));
            }
        });
        addButton("Remote permissions").addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                placeController.goTo(new org.dhamma.users.client.places.RemotePermissionPlace(RestfulActionEnum.INDEX));
            }
        });
        addButton("Groups").addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                placeController.goTo(new org.dhamma.users.client.places.GroupPlace(RestfulActionEnum.INDEX));
            }
        });
        addButton("Configuration").addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                placeController.goTo(new org.dhamma.users.client.places.ConfigurationPlace(RestfulActionEnum.SHOW));
            }
        });
        addButton("Profile").addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                placeController.goTo(new org.dhamma.users.client.places.ProfilePlace(RestfulActionEnum.SHOW));
            }
        });
        addButton("Users").addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                placeController.goTo(new org.dhamma.users.client.places.UserPlace(RestfulActionEnum.INDEX));
            }
        });
    }
}
