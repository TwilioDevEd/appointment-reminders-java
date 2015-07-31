package com.twilio.appointmentreminders;

import com.twilio.appointmentreminders.controllers.AppointmentController;
import com.twilio.appointmentreminders.models.AppointmentService;
import com.twilio.appointmentreminders.util.EntityManagerBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import javax.persistence.EntityManagerFactory;

import static spark.Spark.get;
import static spark.Spark.post;

public class Server {

    public static void main(String[] args) {
        EntityManagerFactory factory = EntityManagerBuilder.getFactory();
        AppointmentService service = new AppointmentService(factory.createEntityManager());

        Spark.staticFileLocation("/public");

        Scheduler scheduler = null;
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();

            scheduler.start();

        } catch (SchedulerException se) {
            se.printStackTrace();
        }

        AppointmentController controller = new AppointmentController(service, scheduler);

        get("/", controller.index, new MustacheTemplateEngine());

        get("/new", controller.renderCreatePage, new MustacheTemplateEngine());

        post("/create", controller.create, new MustacheTemplateEngine());

        post("/delete", controller.delete);
    }
}
