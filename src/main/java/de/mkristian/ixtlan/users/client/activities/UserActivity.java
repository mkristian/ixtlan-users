package de.mkristian.ixtlan.users.client.activities;

import java.util.List;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.mkristian.gwt.rails.DisplayErrors;
import de.mkristian.gwt.rails.Notice;
import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEvent.Action;
import de.mkristian.gwt.rails.places.RestfulActionEnum;
import de.mkristian.ixtlan.users.client.caches.ApplicationCache;
import de.mkristian.ixtlan.users.client.caches.GroupCache;
import de.mkristian.ixtlan.users.client.caches.RegionCache;
import de.mkristian.ixtlan.users.client.caches.UserCache;
import de.mkristian.ixtlan.users.client.events.ApplicationEvent;
import de.mkristian.ixtlan.users.client.events.ApplicationEventHandler;
import de.mkristian.ixtlan.users.client.events.GroupEvent;
import de.mkristian.ixtlan.users.client.events.GroupEventHandler;
import de.mkristian.ixtlan.users.client.events.RegionEvent;
import de.mkristian.ixtlan.users.client.events.RegionEventHandler;
import de.mkristian.ixtlan.users.client.events.UserEvent;
import de.mkristian.ixtlan.users.client.events.UserEventHandler;
import de.mkristian.ixtlan.users.client.models.Application;
import de.mkristian.ixtlan.users.client.models.Group;
import de.mkristian.ixtlan.users.client.models.Region;
import de.mkristian.ixtlan.users.client.models.User;
import de.mkristian.ixtlan.users.client.models.UserQuery;
import de.mkristian.ixtlan.users.client.places.UserPlace;
import de.mkristian.ixtlan.users.client.restservices.UsersRestService;
import de.mkristian.ixtlan.users.client.views.UserView;

public class UserActivity extends AbstractActivity implements UserView.Presenter {

    private final UserPlace place;
    private final UsersRestService service;
    private final Notice notice;
    private final DisplayErrors errors;
    private final PlaceController placeController;
    private final UserView view;
    private final ApplicationCache applicationCache;
    private final GroupCache groupCache;
    private final RegionCache regionCache;
    private final UserCache cache;
    private EventBus eventBus;
       
    @Inject
    public UserActivity(@Assisted UserPlace place, final Notice notice, 
            DisplayErrors errors, final UserView view,
            UsersRestService service, PlaceController placeController,
            UserCache cache, ApplicationCache applicationCache, 
            GroupCache groupCache, RegionCache regionCache) {
        this.place = place;
        this.notice = notice;
        this.errors = errors;
        this.view = view;
        this.service = service;
        this.placeController = placeController; 
        this.applicationCache = applicationCache;
        this.regionCache = regionCache;
        this.groupCache = groupCache;
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
        view.resetGroups(groupCache.getOrLoadModels());

        this.eventBus.addHandler(ApplicationEvent.TYPE, new ApplicationEventHandler(){

            public void onModelEvent(ModelEvent<Application> event) {
                view.resetApplications(event.getModels());
            }
            
        });
        view.resetApplications(applicationCache.getOrLoadModels());
        
        this.eventBus.addHandler(UserEvent.TYPE, new UserEventHandler() {

            public void onModelEvent(ModelEvent<User> event) {
		notice.finishLoading();
                if (event.getModels() != null) {
                    view.reset(event.getModels());
                }
		else if (event.getModel() == null) {
                    // TODO maybe error message ?
		    notice.error("error loading list of User");
                }
            }
        });

        this.eventBus.addHandler(RegionEvent.TYPE, new RegionEventHandler(){

            public void onModelEvent(ModelEvent<Region> event) {
                view.resetRegions(event.getModels());
            }
            
        });
        view.resetRegions(regionCache.getOrLoadModels());

        display.setWidget(view.asWidget());

        view.edit(new UserQuery(place.query));

        switch(UserRestfulActionEnum.valueOf(place.action)){
            case EDIT:
            case SHOW:
                load(place.id);
                break;
            case AT:
                loadAt(place.id);
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
                switch (errors.showMessages(method, exception)) {
                case GENERAL:
                    notice.error("error creating User", exception);
		    break;
		default:
                    notice.error("some error creating User", exception);
                }
            }

            public void onSuccess(Method method, final User response) {
                notice.finishLoading();
                notice.info("sent info mail to " + response.getName() + " <" + response.getEmail() + ">");
                eventBus.fireEvent(new UserEvent(method, response, Action.CREATE));
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
                    eventBus.fireEvent(new UserEvent(method, response, Action.LOAD));
                    view.edit(response);
                }
            });
            notice.loading();
        }
    }

    public void loadAt(int id) {
        User model = cache.getModel(id);
        view.edit(model);
        if (model == null || model.getCreatedAt() == null) {
            service.showAt(id, new MethodCallback<User>() {

                public void onFailure(Method method, Throwable exception) {
                    notice.finishLoading();
                    notice.error("error loading User", exception);
                }

                public void onSuccess(Method method, User response) {
                    notice.finishLoading();
                    eventBus.fireEvent(new UserEvent(method, response, Action.LOAD));
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
                switch (errors.showMessages(method, exception)) {
                case CONFLICT:
                        //TODO
                case GENERAL:
                    notice.error("error saving User", exception);
                }
            }

            public void onSuccess(Method method, User response) {
                notice.finishLoading();
                eventBus.fireEvent(new UserEvent(method, response, Action.UPDATE));
                view.edit(response);
            }
        });
        notice.loading();
    }

    public void delete(final User model){
        service.destroy(model, new MethodCallback<Void>() {

            public void onFailure(Method method, Throwable exception) {
                notice.finishLoading();
                switch (errors.showMessages(method, exception)) {
                case CONFLICT:
                        //TODO
                case GENERAL:
                    notice.error("error deleting User", exception);
                }
            }

            public void onSuccess(Method method, Void response) {
                notice.finishLoading();
                eventBus.fireEvent(new UserEvent(method, model, Action.DESTROY));
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