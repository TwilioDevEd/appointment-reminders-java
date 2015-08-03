package com.twilio.appointmentreminders;

import com.twilio.appointmentreminders.controllers.AppointmentController;
import com.twilio.appointmentreminders.models.AppointmentService;
import com.twilio.appointmentreminders.util.EntityManagerBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import spark.template.mustache.MustacheTemplateEngine;

import javax.persistence.EntityManagerFactory;

import static spark.Spark.*;
import spark.*;

import java.util.Map;

public class Server {

    public static void main(String[] args) {
        Map<String, String> env = System.getenv();
        String port = env.get("PORT");
        if (port != null) {
            port(Integer.parseInt(port));
        }

        EntityManagerFactory factory = EntityManagerBuilder.getFactory();
        AppointmentService service = new AppointmentService(factory.createEntityManager());

        Spark.staticFileLocation("/public");

        Scheduler scheduler = null;
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();

            scheduler.start();

        } catch (SchedulerException se) {
            System.out.println("Unable to start scheduler service");
        }

        AppointmentController controller = new AppointmentController(service, scheduler);

        get("/", controller.index, new MustacheTemplateEngine());

        get("/new", controller.renderCreatePage, new MustacheTemplateEngine());

        post("/create", controller.create, new MustacheTemplateEngine());

        post("/delete", controller.delete);
    }
}
