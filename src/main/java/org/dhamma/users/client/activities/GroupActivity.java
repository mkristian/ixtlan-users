package org.dhamma.users.client.activities;

import java.util.List;

import org.dhamma.users.client.events.GroupEvent;
import org.dhamma.users.client.events.GroupEventHandler;
import org.dhamma.users.client.caches.GroupsCache;
import org.dhamma.users.client.models.Application;
import org.dhamma.users.client.models.Group;
import org.dhamma.users.client.places.GroupPlace;
import org.dhamma.users.client.caches.ApplicationsCache;
import org.dhamma.users.client.events.ApplicationEvent;
import org.dhamma.users.client.events.ApplicationEventHandler;
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
import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEvent.Action;
import de.mkristian.gwt.rails.places.RestfulActionEnum;

public class GroupActivity extends AbstractActivity implements GroupView.Presenter{

    private final GroupPlace place;
    private final GroupsRestService service;
    private final Notice notice;
    private final PlaceController placeController;
    private final GroupView view;
    private final GroupsCache cache;
    private final ApplicationsCache applicationsCache;
    private EventBus eventBus;
    
    @Inject
    public GroupActivity(@Assisted GroupPlace place, final Notice notice, final GroupView view,
            GroupsRestService service, PlaceController placeController,
            GroupsCache cache, ApplicationsCache applicationsCache) {
        this.place = place;
        this.notice = notice;
        this.view = view;
        this.service = service;
        this.placeController = placeController;
        this.cache = cache;
        this.applicationsCache = applicationsCache;

        notice.hide();
        view.setup(this, place.action);
    }

    public void start(AcceptsOneWidget display, EventBus eventBus) {
        this.eventBus = eventBus;

        this.eventBus.addHandler(ApplicationEvent.TYPE, new ApplicationEventHandler() {
            
            public void onModelEvent(ModelEvent<Application> event) {
                if(event.getModels() != null) {
                    view.resetApplications(event.getModels());
                }
            }
        });
        view.resetApplications(applicationsCache.getOrLoadModels());

        this.eventBus.addHandler(GroupEvent.TYPE, new GroupEventHandler() {

            public void onModelEvent(ModelEvent<Group> event) {
                notice.finishLoading();
                if (event.getModels() != null) {
                    view.reset(event.getModels());
                } else if (event.getModel() == null) {
                    // TODO maybe error message ?
                    notice.error("error loading list of Group");
                }
            }
        });

        display.setWidget(view.asWidget());

        switch(RestfulActionEnum.valueOf(place.action)){
            case EDIT: 
            case SHOW:
                load(place.id);
                break;
            case NEW:
                view.edit(new Group());
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

    public void load(){
        List<Group> models = cache.getOrLoadModels();
        if (models != null){
            view.reset(models);
        }
        else {
            // loading the event callback fills the resets the models
            notice.loading();
        }
    }

    public void create() {
        Group model = view.flush();
        service.create(model.minimalClone(), new MethodCallback<Group>() {

            public void onFailure(Method method, Throwable exception) {
                notice.finishLoading();
                notice.error("error creating Group", exception);
            }

            public void onSuccess(Method method, Group response) {
                notice.finishLoading();
                eventBus.fireEvent(new GroupEvent(response, Action.CREATE));
                goTo(new GroupPlace(response.getId(), RestfulActionEnum.EDIT));
            }
        });
        notice.loading();
    }

    public void load(int id) {
        Group model = cache.getModel(id);
        view.edit(model);
        if (model.getCreatedAt() == null) {
            service.show(id, new MethodCallback<Group>() {

                public void onFailure(Method method, Throwable exception) {
                    notice.finishLoading();
                    notice.error("error loading Group", exception);
                }

                public void onSuccess(Method method, Group response) {
                    notice.finishLoading();
                    eventBus.fireEvent(new GroupEvent(response, Action.LOAD));
                    view.edit(response);
                }
            });
            notice.loading();
        }
    }

    public void save() {
        Group model = view.flush();
        service.update(model.minimalClone(), new MethodCallback<Group>() {

            public void onFailure(Method method, Throwable exception) {
                notice.finishLoading();
                notice.error("error saving Group", exception);
            }

            public void onSuccess(Method method, Group response) {
                notice.finishLoading();
                eventBus.fireEvent(new GroupEvent(response, Action.UPDATE));
                view.edit(response);
            }
        });
        notice.loading();
    }

    public void delete(final Group model){
        service.destroy(model, new MethodCallback<Void>() {

            public void onFailure(Method method, Throwable exception) {
                notice.finishLoading();
                notice.error("error deleting Group", exception);
            }

            public void onSuccess(Method method, Void response) {
                notice.finishLoading();
                eventBus.fireEvent(new GroupEvent(model, Action.DESTROY));
                GroupPlace next = new GroupPlace(RestfulActionEnum.INDEX);
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