package com.twilio.appointmentreminders.controllers;

import com.twilio.appointmentreminders.models.Appointment;
import com.twilio.appointmentreminders.models.AppointmentService;
import spark.Route;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Calendar;

public class AppointmentController {
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("Appointments-Persistence");
    private static AppointmentService service  = new AppointmentService(factory.createEntityManager());

    public static Route create = (request, response) -> {
        String name = request.queryParams("name");
        String phoneNumber = request.queryParams("phoneNumber");
        String date = request.queryParams("date");
        int delta = Integer.parseInt(request.queryParams("delta"));
        String timeZone = request.queryParams("timeZone");

        Appointment appointment = new Appointment(name, phoneNumber, delta, Calendar.getInstance(), timeZone);
        service.create(appointment);

        response.redirect("/");
        return response;
    };
}
