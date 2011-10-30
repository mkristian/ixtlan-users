package org.dhamma.users.client.models;


import java.util.ArrayList;
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

  @Json(name = "group_ids")
  private final List<Integer> groupIds;
  private List<Group> groups;

  @Json(name = "application_ids")
  private final List<Integer> applicationIds;

  transient private String token;  

  public User(){
    this(0, null, null, null, null, null);
  }
  
  @JsonCreator
  public User(@JsonProperty("id") int id, 
          @JsonProperty("createdAt") Date createdAt, 
          @JsonProperty("updatedAt") Date updatedAt,
          @JsonProperty("modifiedBy") User modifiedBy,
          @JsonProperty("groupIds") List<Integer> groupIds,
          @JsonProperty("applicationIds") List<Integer> applicationIds){
    this.id = id;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.modifiedBy = modifiedBy;
    this.groupIds = groupIds == null ? new ArrayList<Integer>(): groupIds;
    this.applicationIds = applicationIds == null ? new ArrayList<Integer>() : applicationIds;
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

  public List<Integer> getGroupIds() {
    return groupIds;
  }

  public List<Group> getGroups() {
    return groups;
  }

  public void setGroups(List<Group> groups) {
    this.groups = groups;
    updateGroupIds(groups);
  }

  private void updateGroupIds(List<Group> groups) {
    this.groupIds.clear();     
    this.applicationIds.clear();
    for(Group g: groups){
        this.groupIds.add(g.getId());
        this.applicationIds.add(g.getApplicationId());
    }
  }

  public User minimalClone() {
      User clone = new User(id, null, updatedAt, null, groupIds, null);
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

  public List<Integer> getApplicationIds() {
      return this.applicationIds;
  }
}
