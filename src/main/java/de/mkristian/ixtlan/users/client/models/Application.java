package de.mkristian.ixtlan.users.client.models;


import java.util.Date;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.fusesource.restygwt.client.Json;
import org.fusesource.restygwt.client.Json.Style;

import de.mkristian.gwt.rails.models.HasToDisplay;
import de.mkristian.gwt.rails.models.Identifiable;
import de.mkristian.gwt.rails.views.ExternalApplication;

@Json(style = Style.RAILS)
public class Application implements HasToDisplay, Identifiable, ExternalApplication {

  public final int id;

  @Json(name = "created_at")
  private final Date createdAt;

  @Json(name = "updated_at")
  private final Date updatedAt;

  @Json(name = "modified_by")
  private final User modifiedBy;

  private String name;

  private String url;

  public Application(){
      this(0, null, null, null);
  }

  public Application(int id) {
      this(id, null, null, null);
  }
  
  @JsonCreator
  public Application(@JsonProperty("id") int id, 
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

  public String getName(){
    return name;
  }

  public void setName(String value){
    name = value;
  }

  public String getUrl(){
    return url;
  }

  public void setUrl(String value){
    url = value;
  }

  public int hashCode(){
    return id;
  }

  public boolean equals(Object other){
    return (other instanceof Application) && 
        ((Application)other).id == id;
  }

  public String toDisplay() {
    return name;
  }
}
