package com.twilio.appointmentreminders;

import static spark.Spark.get;
import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import com.twilio.appointmentreminders.models.Appointment;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Server {

  // Bootstrap our Java application
  public static void main(String[] args) {
    Map map = new HashMap();
    map.put("name", "Mario");

    get("/", (rq, rs) -> new ModelAndView(map, "index.mustache"), new MustacheTemplateEngine());
  }
}
