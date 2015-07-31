package com.twilio.appointmentreminders.controllers;

import com.twilio.appointmentreminders.Server;
import com.twilio.appointmentreminders.models.AppointmentService;
import com.twilio.appointmentreminders.util.RequestHandler;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import spark.Spark;

import static org.junit.Assert.assertTrue;

public class AppointmentControllerTest {

    private AppointmentService service;

    @BeforeClass public static void beforeClass() {
        Server.main(null);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterClass public static void afterClass() {
        Spark.stop();
    }

    @Test public void testIndex() {
        RequestHandler request = new RequestHandler();
        String response = request.makeServiceCall("http://localhost:4567/", RequestHandler.GET);
        String body = response;
        assertTrue(body.contains("Name"));

    }
}
