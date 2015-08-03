package com.twilio.appointmentreminders.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class AppSetup {
    private Map<String, String> env;
    String port = env.get("PORT");

    public AppSetup() {
        this.env = System.getenv();
    }

    public EntityManagerFactory getEntityManagerFactory() {
        Map<String, String> configOverrides = new HashMap<>();
        URI dbUri = null;

        try {
            dbUri = new URI(env.get("DATABASE_URL"));
        } catch (URISyntaxException e) {
            System.out.println("Unable to parse DB URL");
        }

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl =
            "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

        configOverrides.put("javax.persistence.jdbc.url", dbUrl);
        configOverrides.put("javax.persistence.jdbc.user", username);
        configOverrides.put("javax.persistence.jdbc.password", password);

        return Persistence.createEntityManagerFactory("Appointments-Persistence", configOverrides);
    }

    public int getPortNumber() {
        String port = env.get("PORT");

        if (port != null) {
            return Integer.parseInt(port);
        } else {
            return 4567;
        }
    }

    public String getACCOUNT_SID() {
        return env.get("TWILIO_ACCOUNT_SID");
    }

    public String getAUTH_TOKEN() {
        return env.get("TWILIO_AUTH_TOKEN");
    }

    public String getTWILIO_PHONE_NUMBER() {
        return env.get("TWILIO_PHONE_NUMBER");
    }
}
