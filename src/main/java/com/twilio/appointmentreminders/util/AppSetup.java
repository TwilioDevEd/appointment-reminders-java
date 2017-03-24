package com.twilio.appointmentreminders.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/** Class that holds methods to obtain configuration parameters from the environment. */
public class AppSetup {
  private Map<String, String> env;

  public AppSetup() {
    this.env = System.getenv();
  }

  public Map<String, String> getParamsFromDbUrl(String url) {
    Map<String, String> params = new HashMap<>();
    URI dbUri = null;

    try {
      dbUri = new URI(url);
    } catch (URISyntaxException e) {
      System.out.println("Unable to parse DB URL");
    }
    String[] userInfo = dbUri.getUserInfo().split(":");
    String username = userInfo[0];
    String password;
    if (userInfo.length > 1) {
      password = userInfo[1];
    } else {
      password = "";
    }

    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

    params.put("url", dbUrl);
    params.put("username", username);
    params.put("password", password);

    return params;
  }

  /** Returns an entity manager factory using the defined environment variables that this class
   * has access to.
   * @return EntityManagerFactory
   */
  public EntityManagerFactory getEntityManagerFactory() {
    AppSetup appSetup = new AppSetup();
    Map<String, String> configOverrides = new HashMap<>();

    Map<String, String> params = appSetup.getParamsFromDbUrl(getDatabaseURL());

    configOverrides.put("javax.persistence.jdbc.url", params.get("url"));
    configOverrides.put("javax.persistence.jdbc.user", params.get("username"));
    configOverrides.put("javax.persistence.jdbc.password", params.get("password"));

    return Persistence.createEntityManagerFactory("Appointments-Persistence", configOverrides);
  }

  public int getPortNumber() {
    String port = env.get("PORT");

    return port != null ? Integer.parseInt(port) : 4567;
  }

  public String getDatabaseURL() {
    return env.get("DATABASE_URL");
  }

  public String getAccountSid() {
    return env.get("TWILIO_ACCOUNT_SID");
  }

  public String getAuthToken() {
    return env.get("TWILIO_AUTH_TOKEN");
  }

  public String getTwilioPhoneNumber() {
    return env.get("TWILIO_NUMBER");
  }
}
