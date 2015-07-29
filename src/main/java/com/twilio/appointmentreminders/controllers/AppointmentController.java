package com.twilio.appointmentreminders.controllers;

import com.twilio.appointmentreminders.models.Appointment;
import com.twilio.appointmentreminders.models.AppointmentService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import spark.Route;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AppointmentController {
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("Appointments-Persistence");
    private static AppointmentService service = new AppointmentService(factory.createEntityManager());

    public static Route create = (request, response) -> {
        String name = request.queryParams("name");
        String phoneNumber = request.queryParams("phoneNumber");
        String date = request.queryParams("date");
        int delta = Integer.parseInt(request.queryParams("delta"));
        String timeZone = request.queryParams("timeZone");

        DateTimeZone zone = DateTimeZone.forID(timeZone);
        DateTimeZone zoneUTC = DateTimeZone.UTC;

        DateTime dt;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy hh:mma");
        formatter = formatter.withZone(zone);
        dt = formatter.parseDateTime(date);
        formatter = formatter.withZone(zoneUTC);
        String dateUTC = dt.toString(formatter);

        Appointment appointment = new Appointment(name, phoneNumber, delta, dateUTC, timeZone);
        service.create(appointment);

        response.redirect("/");
        return response;
    };
}
