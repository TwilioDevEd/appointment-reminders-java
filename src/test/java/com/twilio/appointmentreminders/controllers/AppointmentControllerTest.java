package com.twilio.appointmentreminders.controllers;

import com.google.gson.Gson;
import com.twilio.appointmentreminders.Server;
import com.twilio.appointmentreminders.models.AppointmentService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import spark.Spark;
import spark.utils.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class AppointmentControllerTest {

    private AppointmentService service;

    @BeforeClass
    public static void beforeClass() {
        Server.main(null);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void afterClass() {
        Spark.stop();
    }

    @Test
    public void testIndex() {
        TestResponse res = request("GET", "/");
        String body = res.body;
        assertTrue(body.contains("Name"));

    }

    private TestResponse request(String method, String path) {
        try {
            URL url = new URL("http://localhost:4567" + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.connect();
            String body = IOUtils.toString(connection.getInputStream());
            return new TestResponse(connection.getResponseCode(), body);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class TestResponse {

        public final String body;
        public final int status;

        public TestResponse(int status, String body) {
            this.status = status;
            this.body = body;
        }

        public Map<String, String> json() {
            return new Gson().fromJson(body, HashMap.class);
        }
    }
}
