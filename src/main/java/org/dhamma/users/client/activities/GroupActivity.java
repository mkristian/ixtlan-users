package org.dhamma.users.client.activities;

import java.util.List;

import org.dhamma.users.client.events.GroupEvent;
import org.dhamma.users.client.models.Application;
import org.dhamma.users.client.models.Group;
import org.dhamma.users.client.models.User;
import org.dhamma.users.client.places.GroupPlace;
import org.dhamma.users.client.restservices.ApplicationsRestService;
import org.dhamma.users.client.restservices.GroupsRestService;
import org.dhamma.users.client.views.GroupView;
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

public class GroupActivity extends AbstractActivity implements GroupView.Presenter{

    private final GroupPlace place;
    private final GroupsRestService service;
    private final Notice notice;
    private final PlaceController placeController;
    private final GroupView view;
    private EventBus eventBus;
    
    @Inject
    public GroupActivity(@Assisted GroupPlace place, final Notice notice, final GroupView view,
            GroupsRestService service, PlaceController placeController, 
            ApplicationsRestService applicationRestService,
            final SimpleCache<User> cache) {
        this.place = place;
        this.notice = notice;
        this.view = view;
        this.service = service;
        
        this.placeController = placeController;
        
        List<Application> applications = cache.get("applications");
        view.resetApplications(applications);
        if (applications == null){
        
            applicationRestService.index(new MethodCallback<List<Application>>() {

                public void onSuccess(Method method,
                        List<Application> response) {
                    view.resetApplications(response);
                    cache.put("applications", response);
                }

                public void onFailure(Method method, Throwable exception) {
                    notice.setText("failed to load applications");
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
                view.edit(new Group());
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
        service.index(new MethodCallback<List<Group>>() {

            public void onFailure(Method method, Throwable exception) {
                notice.setText("error loading list of Group: "
                        + exception.getMessage());
                view.reset(place.action);
            }

            public void onSuccess(Method method, List<Group> response) {
                eventBus.fireEvent(new GroupEvent(response, Action.LOAD));
                notice.setText(null);
                view.reset(response);
                view.reset(place.action);
            }
        });
        if(!notice.isVisible()){
            notice.setText("loading list of Group . . .");
        }
    }

    public void create() {
        Group model = view.flush();
        view.setEnabled(false);
        service.create(model.minimalClone(), new MethodCallback<Group>() {

            public void onFailure(Method method, Throwable exception) {
                notice.setText("error creating Group: "
                        + exception.getMessage());
                view.reset(place.action);
            }

            public void onSuccess(Method method, Group response) {
                eventBus.fireEvent(new GroupEvent(response, Action.CREATE));
                notice.setText(null);
                view.addToList(response);
                goTo(new GroupPlace(response.getId(), RestfulActionEnum.EDIT));
            }
        });
        notice.setText("creating Group . . .");
    }

    public void load(int id) {
        view.setEnabled(false);
        service.show(id, new MethodCallback<Group>() {

            public void onFailure(Method method, Throwable exception) {
                notice.setText("error loading Group: "
                        + exception.getMessage());
                view.reset(place.action);
            }

            public void onSuccess(Method method, Group response) {
                eventBus.fireEvent(new GroupEvent(response, Action.LOAD));
                notice.setText(null);
                view.edit(response);
                view.reset(place.action);
            }
        });
        if(!notice.isVisible()){
            notice.setText("loading Group . . .");
        }
    }

    public void save() {
        Group model = view.flush();
        view.setEnabled(false);
        service.update(model.minimalClone(), new MethodCallback<Group>() {

            public void onFailure(Method method, Throwable exception) {
                notice.setText("error saving Group: "
                        + exception.getMessage());
                view.reset(place.action);
            }

            public void onSuccess(Method method, Group response) {
                eventBus.fireEvent(new GroupEvent(response, Action.UPDATE));
                notice.setText(null);
                view.updateInList(response);
                view.edit(response);
                view.reset(place.action);
            }
        });
        notice.setText("saving Group . . .");
    }

    public void delete(final Group model){
        view.setEnabled(false);
        service.destroy(model, new MethodCallback<Void>() {

            public void onFailure(Method method, Throwable exception) {
                notice.setText("error deleting Group: "
                        + exception.getMessage());
                view.reset(place.action);
            }

            public void onSuccess(Method method, Void response) {
                eventBus.fireEvent(new GroupEvent(model, Action.DESTROY));
                notice.setText(null);
                view.removeFromList(model);
                GroupPlace next = new GroupPlace(RestfulActionEnum.INDEX);
                if(placeController.getWhere().equals(next)){
                    view.reset(place.action);
                }
                else{
                    goTo(next);
                }
            }
        });
        notice.setText("deleting Group . . .");
    }
}