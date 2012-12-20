package de.mkristian.ixtlan.users.client.presenters;

import javax.inject.Inject;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.place.shared.PlaceController;

import de.mkristian.gwt.rails.RemoteNotifier;
import de.mkristian.gwt.rails.places.RestfulActionEnum;
import de.mkristian.gwt.rails.presenters.SingletonPresenterImpl;
import de.mkristian.ixtlan.users.client.UsersErrorHandler;
import de.mkristian.ixtlan.users.client.models.Profile;
import de.mkristian.ixtlan.users.client.places.ProfilePlace;
import de.mkristian.ixtlan.users.client.restservices.ProfileRestService;
import de.mkristian.ixtlan.users.client.views.ProfileView;

public class ProfilePresenter 
            extends SingletonPresenterImpl<Profile> {
    
    private final ProfileRestService service;
    private final PlaceController places;
    private final RemoteNotifier notifier;

    @Inject
    public ProfilePresenter(RemoteNotifier notifier,
                UsersErrorHandler errors,
                ProfileView view,
                ProfileRestService service,
                PlaceController places){
        super( errors, view, null );
        this.notifier = notifier;
        this.service = service;
        this.places = places;
    }

    public void save(final Profile model){
        notifier.saving();
        service.update(model, new MethodCallback<Profile>() {
            @Override
            public void onSuccess(Method method, Profile model) {
                notifier.finish();
                isEditing = false;
                places.goTo(new ProfilePlace(model, RestfulActionEnum.SHOW));
            }
            @Override
            public void onFailure(Method method, Throwable e) {
                onError(method, e);
            }
          });
    }
    
    public void show(){
        notifier.loading();
        isEditing = false;
        setWidget(view);
        service.show(new MethodCallback<Profile>() {
            @Override
            public void onSuccess(Method method, Profile model) {
                notifier.finish();
                view.show(model);
            }
            @Override
            public void onFailure(Method method, Throwable e) {
                onError(method, e);   
            }
        });
    }

    public void edit(){
        notifier.loading();
        isEditing = true;
        setWidget(view);
        service.show(new MethodCallback<Profile>() {
            @Override
            public void onSuccess(Method method, Profile model) {
                notifier.finish();
                view.edit(model);
            }
            @Override
            public void onFailure(Method method, Throwable e) {
                onError(method, e);   
            }
        });
    }

    public void onError(Method method, Throwable e) {
        notifier.finish();
        super.onError( method, e );
    }
 
    protected void unknownAction( String action ){
        notifier.finish();
        super.unknownAction( action );
    }
}
