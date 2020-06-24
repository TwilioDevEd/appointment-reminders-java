package com.twilio.appointmentreminders.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

/** Class that holds methods to obtain configuration parameters from the environment. */
public class AppSetup {
  private Map<String, String> env;

  public AppSetup() {
    this.env = System.getenv();
  }

  /** Returns an entity manager factory using the defined environment variables that this class
   * has access to.
   * @return EntityManagerFactory
   */
  public EntityManagerFactory getEntityManagerFactory() {
    Map<String, String> configOverrides = new HashMap<>();

    configOverrides.put("javax.persistence.jdbc.url", getDatabaseURL());

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
