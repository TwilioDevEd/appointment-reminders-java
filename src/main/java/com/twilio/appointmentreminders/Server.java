package com.twilio.appointmentreminders;

import static spark.Spark.get;
import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import com.twilio.appointmentreminders.models.Appointment;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class Server {

  // Bootstrap our Java application
  public static void main(String[] args) {
    String PERSISTENCE_UNIT_NAME = "Appointments-Persistence";
    EntityManagerFactory factory;

    factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    EntityManager em = factory.createEntityManager();

    Map map = new HashMap();
    map.put("name", "Mario");

    Appointment appointment = new Appointment();
    em.persist(appointment);
    get("/", (rq, rs) -> new ModelAndView(map, "index.mustache"), new MustacheTemplateEngine());
  }
}
