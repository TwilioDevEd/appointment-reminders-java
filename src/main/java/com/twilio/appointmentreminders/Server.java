package com.twilio.appointmentreminders;

import com.twilio.appointmentreminders.controllers.AppointmentController;
import com.twilio.appointmentreminders.models.AppointmentService;
import com.twilio.appointmentreminders.util.AppSetup;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import javax.persistence.EntityManagerFactory;

import static spark.Spark.*;

/**
 * Main application class. The environment is set up here, and all necessary services are run.
 */
public class Server {
  public static void main(String[] args) {
    AppSetup appSetup = new AppSetup();

    /**
     * Sets the port in which the application will run. Takes the port value from PORT
     * environment variable, if not set, uses Spark default port 4567.
     */
    port(appSetup.getPortNumber());

    /**
     * Gets the entity manager based on environment variable DATABASE_URL and injects it into
     * AppointmentService which handles all DB operations.
     */
    EntityManagerFactory factory = appSetup.getEntityManagerFactory();
    AppointmentService service = new AppointmentService(factory.createEntityManager());

    /**
     * Specifies the directory within resources that will be publicly available when the
     * application is running. Place static web files in this directory (JS, CSS).
     */
    Spark.staticFileLocation("/public");

    /** Creates a new instance of Quartz Scheduler and starts it. */
    Scheduler scheduler = null;
    try {
      scheduler = StdSchedulerFactory.getDefaultScheduler();

      scheduler.start();

    } catch (SchedulerException se) {
      System.out.println("Unable to start scheduler service");
    }

    /** Injects AppointmentService and Scheduler into the controller. */
    AppointmentController controller = new AppointmentController(service, scheduler);

    /**
     * Defines all url paths for the application and assigns a controller method for each.
     * If the route renders a page, the templating engine must be specified, and the controller
     * should return the appropriate Route object.
     */
    get("/", controller.index, new MustacheTemplateEngine());
    get("/new", controller.renderCreatePage, new MustacheTemplateEngine());
    post("/create", controller.create, new MustacheTemplateEngine());
    post("/delete", controller.delete);
  }
}
