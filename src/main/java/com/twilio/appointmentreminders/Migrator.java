package com.twilio.appointmentreminders;

import com.twilio.appointmentreminders.util.AppSetup;
import org.flywaydb.core.Flyway;

import java.util.Map;

/**
 * Helper class used to run database migrations. This must be executed before the actual
 * application. For testing you must specify a different DATABASE_URL environment variable pointing
 * to your testing database.
 */
class Migrator {
  public static void main(String[] args) throws Exception {
    AppSetup appSetup = new AppSetup();

    /** Fetches database parameters form environment variables. */
    Map<String, String> params = appSetup.getParamsFromDBURL(appSetup.getDatabaseURL());

    /**
     * Uses Flyway to run database migrations. Migration files are located in
     * resources/db/migration directory.
     */
    Flyway flyway = new Flyway();
    flyway.setDataSource(params.get("url"), params.get("username"), params.get("password"));
    flyway.migrate();
  }
}
