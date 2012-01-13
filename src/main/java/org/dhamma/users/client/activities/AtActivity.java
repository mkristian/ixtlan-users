package org.dhamma.users.client.activities;

import java.util.List;

import org.dhamma.users.client.caches.UsersCache;
import org.dhamma.users.client.events.UserEvent;
import org.dhamma.users.client.events.UserEventHandler;
import org.dhamma.users.client.models.User;
import org.dhamma.users.client.models.UserQuery;
import org.dhamma.users.client.places.AtPlace;
import org.dhamma.users.client.restservices.UsersRestService;
import org.dhamma.users.client.views.AtView;
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

public class AtActivity extends AbstractActivity implements AtView.Presenter{

    private final AtPlace place;
    private final UsersRestService service;
    private final Notice notice;
    private final PlaceController placeController;
    private final AtView view;
    private final UsersCache cache;
    private EventBus eventBus;

    @Inject
    public AtActivity(@Assisted AtPlace place, final Notice notice, final AtView view,
            UsersRestService service, PlaceController placeController, UsersCache cache) {
        this.place = place;
        this.notice = notice;
        this.view = view;
        this.service = service;
        this.placeController = placeController;
        this.cache = cache;
        
        notice.hide();
        view.setup(this, place.action);
    }

    public void start(AcceptsOneWidget display, EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.addHandler(UserEvent.TYPE, new UserEventHandler() {

            public void onModelEvent(ModelEvent<User> event) {
                notice.finishLoading();
                if (event.getModels() != null) {
                    // assume that the cache has the recent users already
                    view.reset(cache.getOrLoadAts());
                } else if (event.getModel() == null) {
                    // TODO maybe error message ?
                    notice.error("error loading list of User");
                }
            }
        });

        display.setWidget(view.asWidget());

        view.edit(new UserQuery(place.query));

        switch(RestfulActionEnum.valueOf(place.action)){
            case SHOW:
                load(place.id);
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
        List<User> models = cache.getOrLoadAts();
        if (models != null) {
            view.reset(models);
        }
        else {
            // loading the event callback fills the resets the models
            notice.loading();
        }
    }

    public void load(final int id) {
        final User model = cache.getModel(id);
        view.edit(model);
        if (model != null && model.getCreatedAt() != null && !model.isAt()){
            GWT.log(model.toString());
            notice.warn("nothing to see !!");
        }
        else if (model == null || model.getCreatedAt() == null) {
            notice.loading();
            service.show(id, new MethodCallback<User>() {
    
                public void onFailure(Method method, Throwable exception) {
                    notice.finishLoading();
                    notice.error("error loading At", exception);
                }
    
                public void onSuccess(Method method, User response) {
                    notice.finishLoading();
                    if (response.isAt()){
                        eventBus.fireEvent(new UserEvent(response, Action.LOAD));
                        view.edit(response);
                    }
                    else {
                        notice.error("nothing to see");                    
                    }
                }
            });
            this.eventBus.addHandler(UserEvent.TYPE, new UserEventHandler() {
    
                public void onModelEvent(ModelEvent<User> event) {
                    if (event.getModels() != null) {
                        // assume that the cache has the recent users already
                        view.setupOther(cache.getModel(id));
                    } 
                }
            });
        }
    }
}