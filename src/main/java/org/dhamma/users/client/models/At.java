package org.dhamma.users.client.models;


import java.util.Date;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.fusesource.restygwt.client.Json;
import org.fusesource.restygwt.client.Json.Style;

import de.mkristian.gwt.rails.models.HasToDisplay;
import de.mkristian.gwt.rails.models.Identifyable;
import de.mkristian.gwt.rails.models.IsUser;

@Json(style = Style.RAILS)
public class At implements HasToDisplay, Identifyable, IsUser {

  public final int id;

  @Json(name = "created_at")
  private final Date createdAt;

  @Json(name = "updated_at")
  private final Date updatedAt;

  @Json(name = "modified_by")
  private final User modifiedBy;

  private String login;

  private String email;

  private String name;

  private String hashed;

  private String hashed2;

  @Json(name = "at_token")
  private String atToken;

  public At(){
    this(0, null, null, null);
  }
  
  @JsonCreator
  public At(@JsonProperty("id") int id, 
          @JsonProperty("createdAt") Date createdAt, 
          @JsonProperty("updatedAt") Date updatedAt,
          @JsonProperty("modifiedBy") User modifiedBy){
    this.id = id;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.modifiedBy = modifiedBy;
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
  }

  public String getHashed(){
    return hashed;
  }

  public void setHashed(String value){
    hashed = value;
  }

  public String getHashed2(){
    return hashed2;
  }

  public void setHashed2(String value){
    hashed2 = value;
  }

  public String getAtToken(){
    return atToken;
  }

  public void setAtToken(String value){
    atToken = value;
  }

  public int hashCode(){
    return id;
  }

  public boolean equals(Object other){
    return (other instanceof At) && 
        ((At)other).id == id;
  }

  public String toDisplay() {
    return login;
  }
}
