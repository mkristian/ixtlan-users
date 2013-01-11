package de.mkristian.ixtlan.users.client.places;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulPlace;
import de.mkristian.ixtlan.users.client.managed.ActivityFactory;
import de.mkristian.ixtlan.users.client.models.Region;


import com.google.gwt.activity.shared.Activity;

public class RegionPlace extends RestfulPlace<Region, ActivityFactory> {
    
    public static final String NAME = "regions";

    public Activity create(ActivityFactory factory){
        return null;//factory.create(this);
    }
    
    public RegionPlace(RestfulAction restfulAction) {
        super(restfulAction, NAME);
    }

    public RegionPlace(Region model, RestfulAction restfulAction) {
        super(model.getId(), model, restfulAction, NAME);
    }

    public RegionPlace(int id, RestfulAction restfulAction) {
        super(id, restfulAction, NAME);
    }    
    
    public RegionPlace(String id, RestfulAction restfulAction) {
        super(id, restfulAction, NAME);
    }
}