package com.twilio.appointmentreminders.controllers;

import spark.Route;

public class AppointmentController {
    public static Route sayHello = (request, response) -> {
        return "hello functional stuff";
    };
}
