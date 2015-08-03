package com.twilio.appointmentreminders;

import com.twilio.appointmentreminders.util.AppSetup;
import org.flywaydb.core.Flyway;

import java.util.Map;

class Migrator {
    public static void main(String[] args) throws Exception {
        AppSetup appSetup = new AppSetup();

        Map<String, String> params = appSetup.getParamsFromDBURL(appSetup.getDatabaseURL());

        Flyway flyway = new Flyway();
        flyway.setDataSource(params.get("url"), params.get("username"), params.get("password"));
        flyway.migrate();
    }
}
