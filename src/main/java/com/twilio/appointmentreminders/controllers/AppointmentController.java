package com.twilio.appointmentreminders.controllers;

import spark.Route;

public class AppointmentController {
    public static Route create = (request, response) -> {

        return request.params().toString();
    };
}
