package com.twilio.appointmentreminders.controllers;

import com.twilio.appointmentreminders.models.AppointmentService;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import spark.Request;

import java.util.HashMap;
import java.util.Map;

public class AppointmentControllerTest {

    private AppointmentService service;

    @Before
    public void createService() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Appointments-Persistence-Test");
        service = new AppointmentService(factory.createEntityManager());
        service.deleteAll();
    }

    @Test
    public void testCreate() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", "New Appointment");

    }
}
