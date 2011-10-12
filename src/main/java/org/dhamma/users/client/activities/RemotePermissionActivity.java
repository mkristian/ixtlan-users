package org.dhamma.users.client.activities;

import java.util.List;

import org.dhamma.users.client.events.RemotePermissionEvent;
import org.dhamma.users.client.models.RemotePermission;
import org.dhamma.users.client.places.RemotePermissionPlace;
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
import de.mkristian.gwt.rails.events.ModelEvent.Action;
import de.mkristian.gwt.rails.places.RestfulActionEnum;

public class RemotePermissionActivity extends AbstractActivity implements RemotePermissionView.Presenter{

    private final RemotePermissionPlace place;
    private final RemotePermissionsRestService service;
    private final Notice notice;
    private final PlaceController placeController;
    private final RemotePermissionView view;
    private EventBus eventBus;
    
    @Inject
    public RemotePermissionActivity(@Assisted RemotePermissionPlace place, final Notice notice, final RemotePermissionView view,
            RemotePermissionsRestService service, PlaceController placeController) {
        this.place = place;
        this.notice = notice;
        this.view = view;
        this.service = service;
        this.placeController = placeController;
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
                view.edit(new RemotePermission());
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
        service.index(new MethodCallback<List<RemotePermission>>() {

            public void onFailure(Method method, Throwable exception) {
                notice.setText("error loading list of Remote permission: "
                        + exception.getMessage());
                view.reset(place.action);
            }

            public void onSuccess(Method method, List<RemotePermission> response) {
                eventBus.fireEvent(new RemotePermissionEvent(response, Action.LOAD));
                notice.setText(null);
                view.reset(response);
                view.reset(place.action);
            }
        });
        if(!notice.isVisible()){
            notice.setText("loading list of Remote permission . . .");
        }
    }

    public void create() {
        RemotePermission model = view.flush();
        view.setEnabled(false);
        service.create(model, new MethodCallback<RemotePermission>() {

            public void onFailure(Method method, Throwable exception) {
                notice.setText("error creating Remote permission: "
                        + exception.getMessage());
                view.reset(place.action);
            }

            public void onSuccess(Method method, RemotePermission response) {
                eventBus.fireEvent(new RemotePermissionEvent(response, Action.CREATE));
                notice.setText(null);
                view.addToList(response);
                goTo(new RemotePermissionPlace(response.getId(), RestfulActionEnum.EDIT));
            }
        });
        notice.setText("creating Remote permission . . .");
    }

    public void load(int id) {
        view.setEnabled(false);
        service.show(id, new MethodCallback<RemotePermission>() {

            public void onFailure(Method method, Throwable exception) {
                notice.setText("error loading Remote permission: "
                        + exception.getMessage());
                view.reset(place.action);
            }

            public void onSuccess(Method method, RemotePermission response) {
                eventBus.fireEvent(new RemotePermissionEvent(response, Action.LOAD));
                notice.setText(null);
                view.edit(response);
                view.reset(place.action);
            }
        });
        if(!notice.isVisible()){
            notice.setText("loading Remote permission . . .");
        }
    }

    public void save() {
        RemotePermission model = view.flush();
        view.setEnabled(false);
        service.update(model, new MethodCallback<RemotePermission>() {

            public void onFailure(Method method, Throwable exception) {
                notice.setText("error saving Remote permission: "
                        + exception.getMessage());
                view.reset(place.action);
            }

            public void onSuccess(Method method, RemotePermission response) {
                eventBus.fireEvent(new RemotePermissionEvent(response, Action.UPDATE));
                notice.setText(null);
                view.updateInList(response);
                view.edit(response);
                view.reset(place.action);
            }
        });
        notice.setText("saving Remote permission . . .");
    }

    public void delete(final RemotePermission model){
        view.setEnabled(false);
        service.destroy(model, new MethodCallback<Void>() {

            public void onFailure(Method method, Throwable exception) {
                notice.setText("error deleting Remote permission: "
                        + exception.getMessage());
                view.reset(place.action);
            }

            public void onSuccess(Method method, Void response) {
                eventBus.fireEvent(new RemotePermissionEvent(model, Action.DESTROY));
                notice.setText(null);
                view.removeFromList(model);
                RemotePermissionPlace next = new RemotePermissionPlace(RestfulActionEnum.INDEX);
                if(placeController.getWhere().equals(next)){
                    view.reset(place.action);
                }
                else{
                    goTo(next);
                }
            }
        });
        notice.setText("deleting Remote permission . . .");
    }
}