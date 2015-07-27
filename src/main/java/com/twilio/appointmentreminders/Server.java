package com.twilio.appointmentreminders;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;

public class Server {

    // Bootstrap our Java application
    public static void main(String[] args) {
        get("/:name", (request, response) -> {
            Map map = new HashMap();
            map.put("name", request.params(":name"));
            return new ModelAndView(map, "index.mustache");
        }, new MustacheTemplateEngine());

        get("/hello/:name", (request, response) -> {
            return "Hello: " + request.params(":name");
        });
    }
}
