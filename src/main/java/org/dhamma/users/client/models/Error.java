package org.dhamma.users.client.models;


import java.util.Date;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.fusesource.restygwt.client.Json;
import org.fusesource.restygwt.client.Json.Style;

import de.mkristian.gwt.rails.models.HasToDisplay;
import de.mkristian.gwt.rails.models.Identifyable;

@Json(style = Style.RAILS)
public class Error implements HasToDisplay, Identifyable {

  public final int id;

  @Json(name = "created_at")
  private final Date createdAt;

  @Json(name = "updated_at")
  private final Date updatedAt;

  private String message;

  private String request;

  private String response;

  private String session;

  private String parameters;

  private String clazz;

  private String backtrace;

  public Error(){
    this(0, null, null);
  }
  
  @JsonCreator
  public Error(@JsonProperty("id") int id, 
          @JsonProperty("createdAt") Date createdAt, 
          @JsonProperty("updatedAt") Date updatedAt){
    this.id = id;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
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

  public String getMessage(){
    return message;
  }

  public void setMessage(String value){
    message = value;
  }

  public String getRequest(){
    return request;
  }

  public void setRequest(String value){
    request = value;
  }

  public String getResponse(){
    return response;
  }

  public void setResponse(String value){
    response = value;
  }

  public String getSession(){
    return session;
  }

  public void setSession(String value){
    session = value;
  }

  public String getParameters(){
    return parameters;
  }

  public void setParameters(String value){
    parameters = value;
  }

  public String getClazz(){
    return clazz;
  }

  public void setClazz(String value){
    clazz = value;
  }

  public String getBacktrace(){
    return backtrace;
  }

  public void setBacktrace(String value){
    backtrace = value;
  }

  public int hashCode(){
    return id;
  }

  public boolean equals(Object other){
    return (other instanceof Error) && 
        ((Error)other).id == id;
  }

  public String toDisplay() {
    return message;
  }
}
