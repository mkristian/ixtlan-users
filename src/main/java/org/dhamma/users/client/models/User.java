package org.dhamma.users.client.models;


import java.util.Date;

import org.fusesource.restygwt.client.Json;
import org.fusesource.restygwt.client.Json.Style;

@Json(style = Style.RAILS)
public class User {

  public int id;

  public String login;
  public String name;
  public String email;

  public Date created_at;
  public Date updated_at;
}
