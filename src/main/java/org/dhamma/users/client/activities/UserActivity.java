package org.dhamma.users.client.activities;

import java.util.List;

import org.dhamma.users.client.caches.ApplicationsCache;
import org.dhamma.users.client.caches.GroupsCache;
import org.dhamma.users.client.caches.UsersCache;
import org.dhamma.users.client.events.ApplicationEvent;
import org.dhamma.users.client.events.ApplicationEventHandler;
import org.dhamma.users.client.events.GroupEvent;
import org.dhamma.users.client.events.GroupEventHandler;
import org.dhamma.users.client.events.UserEvent;
import org.dhamma.users.client.events.UserEventHandler;
import org.dhamma.users.client.models.Application;
import org.dhamma.users.client.models.Group;
import org.dhamma.users.client.models.User;
import org.dhamma.users.client.models.UserQuery;
import org.dhamma.users.client.places.UserPlace;
import org.dhamma.users.client.restservices.UsersRestService;
import org.dhamma.users.client.views.UserView;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.mkristian.gwt.rails.Notice;
import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEvent.Action;
import de.mkristian.gwt.rails.places.RestfulActionEnum;

public class UserActivity extends AbstractActivity implements UserView.Presenter {

    private final UserPlace place;
    private final UsersRestService service;
    private final Notice notice;
    private final PlaceController placeController;
    private final UserView view;
    private final GroupsCache groupsCache;
    private final ApplicationsCache applicationsCache;
    private final UsersCache cache;
    private EventBus eventBus;
    
    @Inject
    public UserActivity(@Assisted UserPlace place, final Notice notice, final UserView view,
            UsersRestService service, PlaceController placeController,
            UsersCache cache, GroupsCache groupsCache, ApplicationsCache applicationsCache) {
        this.place = place;
        this.notice = notice;
        this.view = view;
        this.service = service;
        this.placeController = placeController;  
        this.groupsCache = groupsCache;
        this.applicationsCache = applicationsCache;
        this.cache = cache;

        notice.hide();
        view.setup(this, place.action);
    }

    public void start(AcceptsOneWidget display, EventBus eventBus) {
        this.eventBus = eventBus;        
        
        this.eventBus.addHandler(GroupEvent.TYPE, new GroupEventHandler(){

            public void onModelEvent(ModelEvent<Group> event) {
                view.resetGroups(event.getModels());
            }
            
        });
        view.resetGroups(groupsCache.getOrLoadModels());

        this.eventBus.addHandler(ApplicationEvent.TYPE, new ApplicationEventHandler(){

            public void onModelEvent(ModelEvent<Application> event) {
                view.resetApplications(event.getModels());
            }
            
        });
        view.resetApplications(applicationsCache.getOrLoadModels());
        
        this.eventBus.addHandler(UserEvent.TYPE, new UserEventHandler() {

            public void onModelEvent(ModelEvent<User> event) {
                notice.finishLoading();
                if (event.getModels() != null) {
                    view.reset(event.getModels());
                } else if (event.getModel() == null) {
                    // TODO maybe error message ?
                    notice.error("error loading list of User");
                }
            }
        });

        display.setWidget(view.asWidget());

        view.edit(new UserQuery(place.query));

        switch(RestfulActionEnum.valueOf(place.action)){
            case EDIT: 
            case SHOW:
                load(place.id);
                break;
            case NEW:
                view.edit(new User());
                break;
            case INDEX:
            default:
                load();
                break;
        }
    }

    public void goTo(Place place) {
        placeController.goTo(place);
    }

    public void load() {
        List<User> models = cache.getOrLoadModels();
        if (models != null) {
            view.reset(models);
        }
        else {
            // loading the event callback fills the resets the models
            notice.loading();
        }
    }

    public void create() {
        User model = view.flush();
        service.create(model.minimalClone(), new MethodCallback<User>() {

            public void onFailure(Method method, Throwable exception) {
                notice.finishLoading();
                notice.error("error creating User", exception);
            }

            public void onSuccess(Method method, User response) {
                notice.finishLoading();
                notice.info("sent info mail to " + response.getName() + " <" + response.getEmail() + ">");
                GWT.log("sent info mail to " + response.getName() + " <" + response.getEmail() + ">");
                eventBus.fireEvent(new UserEvent(response, Action.CREATE));
                goTo(new UserPlace(response.getId(), RestfulActionEnum.EDIT));
            }
        });
        notice.loading();
    }

    public void load(int id) {
        User model = cache.getModel(id);
        view.edit(model);
        if (model == null || model.getCreatedAt() == null) {
            service.show(id, new MethodCallback<User>() {

                public void onFailure(Method method, Throwable exception) {
                    notice.finishLoading();
                    notice.error("error loading User", exception);
                }

                public void onSuccess(Method method, User response) {
                    notice.finishLoading();
                    eventBus.fireEvent(new UserEvent(response, Action.LOAD));
                    view.edit(response);
                }
            });
            notice.loading();
        }
    }

    public void save() {
        User model = view.flush();
        service.update(model.minimalClone(), new MethodCallback<User>() {

            public void onFailure(Method method, Throwable exception) {
                notice.finishLoading();
                notice.error("error saving User", exception);
            }

            public void onSuccess(Method method, User response) {
                notice.finishLoading();
                eventBus.fireEvent(new UserEvent(response, Action.UPDATE));
                view.edit(response);
            }
        });
        notice.loading();
    }

    public void delete(final User model){
        service.destroy(model, new MethodCallback<Void>() {

            public void onFailure(Method method, Throwable exception) {
                notice.finishLoading();
                notice.error("error deleting User", exception);
            }

            public void onSuccess(Method method, Void response) {
                notice.finishLoading();
                eventBus.fireEvent(new UserEvent(model, Action.DESTROY));
                UserPlace next = new UserPlace(RestfulActionEnum.INDEX);
                if(placeController.getWhere().equals(next)){
                    view.removeFromList(model);
                }
                else{
                    goTo(next);
                }
            }
        });
        notice.loading();
    }
}