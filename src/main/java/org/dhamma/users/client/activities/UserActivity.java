package org.dhamma.users.client.activities;

import java.util.List;

import org.dhamma.users.client.events.UserEvent;
import org.dhamma.users.client.models.Group;
import org.dhamma.users.client.models.User;
import org.dhamma.users.client.places.UserPlace;
import org.dhamma.users.client.restservices.SessionRestService;
import org.dhamma.users.client.restservices.UsersRestService;
import org.dhamma.users.client.views.UserView;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.mkristian.gwt.rails.Notice;
import de.mkristian.gwt.rails.events.ModelEvent.Action;
import de.mkristian.gwt.rails.places.RestfulActionEnum;

public class UserActivity extends AbstractActivity implements UserView.Presenter{

    private final UserPlace place;
    private final UsersRestService service;
    private final Notice notice;
    private final PlaceController placeController;
    private final UserView view;
    private EventBus eventBus;
    
    @Inject
    public UserActivity(@Assisted UserPlace place, final Notice notice, final UserView view,
            UsersRestService service, PlaceController placeController, SessionRestService sessionRestService, 
            final SimpleCache<User> cache) {
        this.place = place;
        this.notice = notice;
        this.view = view;
        this.service = service;
        this.placeController = placeController;
        
        List<Group> groups = cache.get("groups");
        view.resetGroups(groups);
        if(groups == null){
            sessionRestService.currentGroups(new MethodCallback<List<Group>>() {
            
                public void onSuccess(Method method, final List<Group> response) {
                    view.resetGroups(response);
                    cache.put("groups", response);
                }
            
                public void onFailure(Method method, Throwable exception) {
                    notice.setText("failed to load groups");
                }
            });
        }
    }

    public void start(AcceptsOneWidget display, EventBus eventBus) {
        this.eventBus = eventBus;
        display.setWidget(view.asWidget());
        view.setPresenter(this);
        switch(RestfulActionEnum.valueOf(place.action.name())){
            case EDIT: 
            case SHOW:
                load(place.model == null ? place.id : place.model.getId());
                break;
            case NEW:
                notice.setText(null);
                view.edit(new User());
                break;
            case INDEX:
            default:
                load();
                break;
        }
        view.reset(place.action);
    }

    public void goTo(Place place) {
        placeController.goTo(place);
    }

    public void load(){  
        view.setEnabled(false);
        service.index(new MethodCallback<List<User>>() {

            public void onFailure(Method method, Throwable exception) {
                notice.setText("error loading list of User: "
                        + exception.getMessage());
                view.reset(place.action);
            }

            public void onSuccess(Method method, List<User> response) {
                eventBus.fireEvent(new UserEvent(response, Action.LOAD));
                notice.setText(null);
                view.reset(response);
                view.reset(place.action);
            }
        });
        if(!notice.isVisible()){
            notice.setText("loading list of User . . .");
        }
    }

    public void create() {
        User model = view.flush();
        view.setEnabled(false);
        service.create(model.minimalClone(), new MethodCallback<User>() {

            public void onFailure(Method method, Throwable exception) {
                notice
                        .setText("error creating User: "
                                + exception.getMessage());
                view.reset(place.action);
            }

            public void onSuccess(Method method, User response) {
                eventBus.fireEvent(new UserEvent(response, Action.CREATE));
                notice.setText(null);
                view.addToList(response);
                goTo(new UserPlace(response.getId(), RestfulActionEnum.EDIT));
            }
        });
        notice.setText("creating User . . .");
    }

    public void load(int id) {
        view.setEnabled(false);
        service.show(id, new MethodCallback<User>() {

            public void onFailure(Method method, Throwable exception) {
                notice.setText("error loading User: "
                        + exception.getMessage());
                view.reset(place.action);
            }

            public void onSuccess(Method method, User response) {
                eventBus.fireEvent(new UserEvent(response, Action.LOAD));
                notice.setText(null);
                view.edit(response);
                view.reset(place.action);
            }
        });
        if(!notice.isVisible()){
            notice.setText("loading User . . .");
        }
    }

    public void save() {
        User model = view.flush();
        view.setEnabled(false);
        service.update(model.minimalClone(), new MethodCallback<User>() {

            public void onFailure(Method method, Throwable exception) {
                notice.setText("error saving User: " + exception.getMessage());
                view.reset(place.action);
            }

            public void onSuccess(Method method, User response) {
                eventBus.fireEvent(new UserEvent(response, Action.UPDATE));
                notice.setText(null);
                view.updateInList(response);
                view.edit(response);
                view.reset(place.action);
            }
        });
        notice.setText("saving User . . .");
    }

    public void delete(final User model){
        view.setEnabled(false);
        service.destroy(model, new MethodCallback<Void>() {

            public void onFailure(Method method, Throwable exception) {
                notice.setText("error deleting User: "
                        + exception.getMessage());
                view.reset(place.action);
            }

            public void onSuccess(Method method, Void response) {
                eventBus.fireEvent(new UserEvent(model, Action.DESTROY));
                notice.setText(null);
                view.removeFromList(model);
                UserPlace next = new UserPlace(RestfulActionEnum.INDEX);
                if(placeController.getWhere().equals(next)){
                    view.reset(place.action);
                }
                else{
                    goTo(next);
                }
            }
        });
        notice.setText("deleting User . . .");
    }
}