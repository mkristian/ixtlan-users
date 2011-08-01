package org.dhamma.users.client.activities;


import org.dhamma.users.client.models.User;
import org.dhamma.users.client.places.UserPlace;
import org.dhamma.users.client.restservices.UsersRestService;
import org.dhamma.users.client.views.UserView;

import de.mkristian.gwt.rails.Notice;
import de.mkristian.gwt.rails.RestfulActionEnum;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class UserActivity extends AbstractActivity implements UserView.Presenter{

    private final UserPlace place;
    private final UsersRestService service;
    private final Notice notice;
    private final PlaceController placeController;
    private final UserView view;
    
    @Inject
    public UserActivity(@Assisted UserPlace place, Notice notice, UserView view,
            UsersRestService service, PlaceController placeController) {
	this.place = place;
        this.notice = notice;
        this.view = view;
        this.service = service;
        this.placeController = placeController;
    }

    public void start(AcceptsOneWidget display, EventBus eventBus) {
        display.setWidget(view.asWidget());
        view.setPresenter(this);
        switch(RestfulActionEnum.valueOf(place.action.name())){
            case EDIT: 
            case SHOW:
                load(place.id);
                break;
            case INDEX:
                //TODO
            default:
            case NEW: 
                notice.setText(null);
                view.reset(new User());
                break;
        }
        view.reset(place.action);
    }

    public void goTo(Place place) {
        placeController.goTo(place);
    }

    public void load(int id) {
        view.setEnabled(false);
        service.show(id, new MethodCallback<User>() {

            public void onFailure(Method method, Throwable exception) {
                notice.setText("error loading User: "
                        + exception.getMessage());
            }

            public void onSuccess(Method method, User response) {
                view.reset(response);
                notice.setText(null);
                view.reset(place.action);
            }
        });
        if(!notice.isVisible()){
            notice.setText("loading User . . .");
        }
    }
    public void create() {
        User model = view.retrieveUser();
        view.setEnabled(false);
        service.create(model, new MethodCallback<User>() {

            public void onFailure(Method method, Throwable exception) {
                notice.setText("error creating User: "
                        + exception.getMessage());
            }

            public void onSuccess(Method method, User response) {
                goTo(new UserPlace(response.id, 
                        RestfulActionEnum.EDIT));
            }
        });
        notice.setText("creating User . . .");                
    }

    public void delete() {
        User model = view.retrieveUser();
        view.setEnabled(false);
        service.destroy(model, new MethodCallback<Void>() {

            public void onFailure(Method method, Throwable exception) {
                notice.setText("error deleting User: "
                        + exception.getMessage());
            }

            public void onSuccess(Method method, Void response) {
                goTo(new UserPlace(RestfulActionEnum.INDEX));
            }
        });
        notice.setText("deleting User . . .");                
    }
    public void save() {
        User model = view.retrieveUser();
        view.setEnabled(false);
        service.update(model, new MethodCallback<User>() {

            public void onFailure(Method method, Throwable exception) {
                notice.setText("error loading User: "
                        + exception.getMessage());
            }

            public void onSuccess(Method method, User response) {
                goTo(new UserPlace(response.id, 
                        RestfulActionEnum.EDIT));
            }
        });
        notice.setText("saving User . . .");        
    }
}