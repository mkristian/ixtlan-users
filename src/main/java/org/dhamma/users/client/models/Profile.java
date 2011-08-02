package org.dhamma.users.client.models;


import java.util.Date;

import org.fusesource.restygwt.client.Json;
import org.fusesource.restygwt.client.Json.Style;

@Json(style = Style.RAILS)
public class Profile {


  public String email;

  public String name;

  @Json(name = "openid_identifier")
  public String openidIdentifier;

  public String password;

  @Json(name = "created_at")
  public Date createdAt;

  @Json(name = "updated_at")
  public Date updatedAt;
}