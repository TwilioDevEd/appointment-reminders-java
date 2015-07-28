package com.twilio.appointmentreminders;

import com.twilio.appointmentreminders.controllers.AppointmentController;
import com.twilio.appointmentreminders.util.TimeZones;
import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class Server {

    public static void main(String[] args) {
        Spark.staticFileLocation("/public");

        get("/", (request, response) -> {
            Map map = new HashMap();
            return new ModelAndView(map, "index.mustache");
        }, new MustacheTemplateEngine());

        get("/new", (request, response) -> {
            Map map = new HashMap();

            TimeZones tz = new TimeZones();
            List<String> zones = tz.getTimeZones();

            map.put("zones", zones);
            return new ModelAndView(map, "new.mustache");
        }, new MustacheTemplateEngine());

        post("/create", AppointmentController.create);
    }
}
