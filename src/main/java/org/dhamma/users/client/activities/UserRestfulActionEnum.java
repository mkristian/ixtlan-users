package org.dhamma.users.client.activities;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulActionEnum;

public enum UserRestfulActionEnum implements RestfulAction{
    NEW("new", false), SHOW("", true), AT("", true), EDIT("edit", false), INDEX("", true), DESTROY("", false);

    private final String token;

    private final boolean viewOnly;

    static Map<String, RestfulActionEnum> map = new HashMap<String, RestfulActionEnum>();
    static {
        for(RestfulActionEnum s : EnumSet.allOf(RestfulActionEnum.class)) {
             map.put(s.name(), s);
        }
    }

    private UserRestfulActionEnum(String token, boolean viewOnly){
        this.token = token;
        this.viewOnly = viewOnly;
    }
    
    public static UserRestfulActionEnum valueOf(RestfulAction action){
        return valueOf(action.name());
    }

    public static RestfulAction toRestfulAction(String name){
        RestfulActionEnum action = map.get(name.toUpperCase());
        if (action == null){
            return null;
        }
        else {
            return action;
        }
    }

    public String token(){
        return token;
    }

    public boolean viewOnly() {
        return viewOnly;
    }
    
    public boolean equals(RestfulAction action){
        return action != null && name().equals(action.name());
    }
}