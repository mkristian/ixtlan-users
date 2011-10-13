package org.dhamma.users.client.models;


import java.util.Date;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.fusesource.restygwt.client.Json;
import org.fusesource.restygwt.client.Json.Style;

import de.mkristian.gwt.rails.models.HasToDisplay;

@Json(style = Style.RAILS)
public class Profile implements HasToDisplay {


  @Json(name = "created_at")
  private final Date createdAt;

  @Json(name = "updated_at")
  private final Date updatedAt;

  private String name;

  private String email;

  private String password;

  public Profile(){
    this(null, null);
  }
  
  @JsonCreator
  public Profile(@JsonProperty("createdAt") Date createdAt, 
          @JsonProperty("updatedAt") Date updatedAt){
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Date getCreatedAt(){
    return createdAt;
  }

  public Date getUpdatedAt(){
    return updatedAt;
  }

  public String getName(){
    return name;
  }

  public void setName(String value){
    name = value;
  }

  public String getEmail(){
    return email;
  }

  public void setEmail(String value){
    email = value;
  }

  public String getPassword(){
    return password;
  }

  public void setPassword(String value){
    password = value;
  }

  public String toDisplay() {
    return name;
  }
}
