package org.dhamma.users.client.activities;

import java.util.List;

import org.dhamma.users.client.events.RemotePermissionEvent;
import org.dhamma.users.client.events.RemotePermissionEventHandler;
import org.dhamma.users.client.caches.RemotePermissionsCache;
import org.dhamma.users.client.models.Application;
import org.dhamma.users.client.models.RemotePermission;
import org.dhamma.users.client.places.RemotePermissionPlace;
import org.dhamma.users.client.caches.ApplicationsCache;
import org.dhamma.users.client.events.ApplicationEvent;
import org.dhamma.users.client.events.ApplicationEventHandler;
import org.dhamma.users.client.restservices.RemotePermissionsRestService;
import org.dhamma.users.client.views.RemotePermissionView;

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

public class RemotePermissionActivity extends AbstractActivity implements RemotePermissionView.Presenter{

    private final RemotePermissionPlace place;
    private final RemotePermissionsRestService service;
    private final Notice notice;
    private final PlaceController placeController;
    private final RemotePermissionView view;
    private final RemotePermissionsCache cache;
    private final ApplicationsCache applicationsCache;
    private EventBus eventBus;
    
    @Inject
    public RemotePermissionActivity(@Assisted RemotePermissionPlace place, final Notice notice, final RemotePermissionView view,
            RemotePermissionsRestService service, PlaceController placeController,
            RemotePermissionsCache cache, ApplicationsCache applicationsCache) {
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

        this.eventBus.addHandler(RemotePermissionEvent.TYPE, new RemotePermissionEventHandler() {

            public void onModelEvent(ModelEvent<RemotePermission> event) {
                notice.finishLoading();
                if (event.getModels() != null) {
                    view.reset(event.getModels());
                } else if (event.getModel() == null) {
                    // TODO maybe error message ?
                    notice.error("error loading list of Remote permission");
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
                view.edit(new RemotePermission());
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
        List<RemotePermission> models = cache.getOrLoadModels();
        if (models != null){
            view.reset(models);
        }
        else {
            // loading the event callback fills the resets the models
            notice.loading();
        }
    }

    public void create() {
        RemotePermission model = view.flush();
        service.create(model.minimalClone(), new MethodCallback<RemotePermission>() {

            public void onFailure(Method method, Throwable exception) {
                notice.finishLoading();
                notice.error("error creating Remote permission", exception);
            }

            public void onSuccess(Method method, RemotePermission response) {
                notice.finishLoading();
                eventBus.fireEvent(new RemotePermissionEvent(response, Action.CREATE));
                goTo(new RemotePermissionPlace(response.getId(), RestfulActionEnum.EDIT));
            }
        });
        notice.loading();
    }

    public void load(int id) {
        RemotePermission model = cache.getModel(id);
        view.edit(model);
        if (model == null || model.getCreatedAt() == null) {
            service.show(id, new MethodCallback<RemotePermission>() {

                public void onFailure(Method method, Throwable exception) {
                    notice.finishLoading();
                    notice.error("error loading Remote permission", exception);
                }

                public void onSuccess(Method method, RemotePermission response) {
                    notice.finishLoading();
                    eventBus.fireEvent(new RemotePermissionEvent(response, Action.LOAD));
                    view.edit(response);
                }
            });
            notice.loading();
        }
    }

    public void save() {
        RemotePermission model = view.flush();
        service.update(model.minimalClone(), new MethodCallback<RemotePermission>() {

            public void onFailure(Method method, Throwable exception) {
                notice.finishLoading();
                notice.error("error saving Remote permission", exception);
            }

            public void onSuccess(Method method, RemotePermission response) {
                notice.finishLoading();
                eventBus.fireEvent(new RemotePermissionEvent(response, Action.UPDATE));
                view.edit(response);
            }
        });
        notice.loading();
    }

    public void delete(final RemotePermission model){
        service.destroy(model, new MethodCallback<Void>() {

            public void onFailure(Method method, Throwable exception) {
                notice.finishLoading();
                notice.error("error deleting Remote permission", exception);
            }

            public void onSuccess(Method method, Void response) {
                notice.finishLoading();
                eventBus.fireEvent(new RemotePermissionEvent(model, Action.DESTROY));
                RemotePermissionPlace next = new RemotePermissionPlace(RestfulActionEnum.INDEX);
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