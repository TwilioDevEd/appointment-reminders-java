package com.twilio.appointmentreminders.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class EntityManagerBuilder {
    public static EntityManagerFactory getFactory() {
        Map<String, String> env = System.getenv();
        Map<String, String> configOverrides = new HashMap<>();

        configOverrides.put("javax.persistence.jdbc.url", env.get("DB_URL"));
        configOverrides.put("javax.persistence.jdbc.user", env.get("DB_USER"));
        configOverrides.put("javax.persistence.jdbc.password", env.get("DB_PASSWORD"));

        return Persistence.createEntityManagerFactory("Appointments-Persistence", configOverrides);
    }
}
