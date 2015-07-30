package com.twilio.appointmentreminders;

import com.twilio.appointmentreminders.controllers.AppointmentController;
import com.twilio.appointmentreminders.models.AppointmentService;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static spark.Spark.get;
import static spark.Spark.post;

public class Server {

    public static void main(String[] args) {
        Spark.staticFileLocation("/public");

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Appointments-Persistence");
        AppointmentService service = new AppointmentService(factory.createEntityManager());

        Scheduler scheduler = null;
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();

            scheduler.start();

            //scheduler.shutdown();

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
