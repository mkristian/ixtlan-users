package de.mkristian.ixtlan.users.client.managed;

import com.google.gwt.activity.shared.Activity;
import com.google.inject.name.Named;

import de.mkristian.ixtlan.users.client.places.LoginPlace;
public interface ActivityFactory {
    @Named("profiles") Activity create(de.mkristian.ixtlan.users.client.places.ProfilePlace place);
    @Named("configurations") Activity create(de.mkristian.ixtlan.users.client.places.ConfigurationPlace place);
    @Named("audits") Activity create(de.mkristian.ixtlan.users.client.places.AuditPlace place);
    @Named("errors") Activity create(de.mkristian.ixtlan.users.client.places.ErrorPlace place);
    @Named("domains") Activity create(de.mkristian.ixtlan.users.client.places.DomainPlace place);
//    @Named("regions") Activity create(de.mkristian.ixtlan.users.client.places.RegionPlace place);
    @Named("applications") Activity create(de.mkristian.ixtlan.users.client.places.ApplicationPlace place);
    @Named("users") Activity create(de.mkristian.ixtlan.users.client.places.UserPlace place);
//    @Named("ats") Activity create(de.mkristian.ixtlan.users.client.places.AtPlace place);
    @Named("login") Activity create(LoginPlace place);
}