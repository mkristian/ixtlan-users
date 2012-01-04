package org.dhamma.users.client.models;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.fusesource.restygwt.client.Json;
import org.fusesource.restygwt.client.Json.Style;

import de.mkristian.gwt.rails.caches.FilterUtils;
import de.mkristian.gwt.rails.models.HasToDisplay;
import de.mkristian.gwt.rails.models.Identifyable;
import de.mkristian.gwt.rails.models.IsUser;

@Json(style = Style.RAILS)
public class User implements HasToDisplay, Identifyable, IsUser {

  public final int id;

  @Json(name = "created_at")
  private final Date createdAt;

  @Json(name = "updated_at")
  private final Date updatedAt;

  @Json(name = "modified_by")
  private User modifiedBy;

  private String login;

  private String email;

  private String name;

  private List<Group> groups;

  @Json(name = "application_ids")
  private final List<Integer> applicationIds;
  public final List<Application> applications;

  transient private String token;  

  public User(){
    this(0, null, null, null, null,null);
  }
  
  @JsonCreator
  public User(@JsonProperty("id") int id, 
          @JsonProperty("createdAt") Date createdAt, 
          @JsonProperty("updatedAt") Date updatedAt,
          @JsonProperty("modifiedBy") User modifiedBy,
          @JsonProperty("applicationIds") List<Integer> applicationIds, 
          @JsonProperty("applications") List<Application> applications){
    this.id = id;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.modifiedBy = modifiedBy;
    this.applicationIds = applicationIds == null ? new ArrayList<Integer>() : applicationIds;
    this.applications = applications == null ? null : Collections.unmodifiableList(applications);
  }

  public int getId(){
    return id;
  }

  public Date getCreatedAt(){
    return createdAt;
  }

  public Date getUpdatedAt(){
    return updatedAt;
  }

  public User getModifiedBy(){
    return modifiedBy;
  }

  public String getLogin(){
    return login;
  }

  public void setLogin(String value){
    login = value;
  }

  public String getEmail(){
    return email;
  }

  public void setEmail(String value){
    email = value;
  }

  public String getName(){
    return name;
  }

  public void setName(String value){
    name = value;
    createToken();
  }

  private void createToken() {
      this.token = name == null ? "" : FilterUtils.normalize(name);
  }

  public List<Group> getGroups() {
    return groups;
  }

  public void setGroups(List<Group> groups) {
    this.groups = groups;
    this.applicationIds.clear();
    for(Group g: groups){
        this.applicationIds.add(g.getApplicationId());
    }
  }

  public User minimalClone() {
      User clone = new User(id, null, updatedAt, null, null, null);
      List<Group> minimalGroups = new ArrayList<Group>(groups.size());
      for(Group g: groups){
          minimalGroups.add(g.minimalClone());
      }
      clone.setGroups(minimalGroups);
      clone.setName(name);
      clone.setLogin(login);
      clone.setEmail(email);
      return clone;
  }

  public int hashCode(){
    return id;
  }

  public boolean equals(Object other){
    return (other instanceof User) && 
        ((User)other).id == id;
  }

  public String toDisplay() {
    return login;
  }
  
  public String searchToken(){
      if(this.token == null){
          createToken();
      }
      return this.token;
  }

  List<Integer> getApplicationIds() {
      return this.applicationIds;
  }
}
