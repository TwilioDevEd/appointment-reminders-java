package com.twilio.appointmentreminders.controllers;

import com.twilio.appointmentreminders.models.Appointment;
import com.twilio.appointmentreminders.models.AppointmentService;
import com.twilio.appointmentreminders.util.FieldValidator;
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
    public TemplateViewRoute renderCreatePage = (request, response) -> {
        Map map = new HashMap();

        map.put("zones", timeZones());
        return new ModelAndView(map, "new.mustache");
    };
    private AppointmentService service;
    public TemplateViewRoute create = (request, response) -> {
        FieldValidator validator = new FieldValidator(
                new String[]{"name", "phoneNumber", "date", "delta", "timeZone"}
        );

        if (validator.valid(request)) {
            try {
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
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        Map map = new HashMap();

        map.put("zones", timeZones());
        return new ModelAndView(map, "new.mustache");
    };

    public TemplateViewRoute index = (request, response) -> {
        Map map = new HashMap();

        List<Appointment> appointments = service.findAll();
        map.put("appointments", appointments);

        return new ModelAndView(map, "index.mustache");
    };
    public Route delete = (request, response) -> {
        String id = request.queryParams("id");
        Long idLong = Long.parseLong(id, 10);

        Appointment appointment = service.getAppointment(idLong);
        service.delete(appointment);

        response.redirect("/");
        return response;
    };

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    private List<String> timeZones() {
        TimeZones tz = new TimeZones();

        return tz.getTimeZones();
    }
}
