package com.twilio.appointmentreminders.controllers;

import com.twilio.appointmentreminders.models.Appointment;
import com.twilio.appointmentreminders.models.AppointmentService;
import com.twilio.appointmentreminders.util.TimeZones;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import spark.ModelAndView;
import spark.Route;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppointmentController {
    private AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    public Route create = (request, response) -> {
        String name = request.queryParams("name");
        String phoneNumber = request.queryParams("phoneNumber");
        String date = request.queryParams("date");
        int delta = Integer.parseInt(request.queryParams("delta"));
        String timeZone = request.queryParams("timeZone");

        DateTimeZone zone = DateTimeZone.forID(timeZone);
        DateTimeZone zoneUTC = DateTimeZone.UTC;

        DateTime dt;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-dd-yyyy hh:mma");
        formatter = formatter.withZone(zone);
        dt = formatter.parseDateTime(date);
        formatter = formatter.withZone(zoneUTC);
        String dateUTC = dt.toString(formatter);

        Appointment appointment = new Appointment(name, phoneNumber, delta, dateUTC, timeZone);
        service.create(appointment);

        response.redirect("/");
        return response;
    };

    public TemplateViewRoute index = (request, response) -> {
        Map map = new HashMap();

        List<Appointment> appointments = service.findAll();
        map.put("appointments", appointments);

        return new ModelAndView(map, "index.mustache");
    };

    public TemplateViewRoute renderCreatePage = (request, response) -> {
        Map map = new HashMap();

        TimeZones tz = new TimeZones();
        List<String> zones = tz.getTimeZones();

        map.put("zones", zones);
        return new ModelAndView(map, "new.mustache");
    };

    public Route delete = (request, response) -> {
        String id = request.queryParams("id");
        Long idLong = Long.parseLong(id, 10);

        Appointment appointment = service.getAppointment(idLong);
        service.delete(appointment);

        response.redirect("/");
        return response;
    };
}
