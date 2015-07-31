package com.twilio.appointmentreminders.controllers;

import com.twilio.appointmentreminders.Server;
import com.twilio.appointmentreminders.models.Appointment;
import com.twilio.appointmentreminders.models.AppointmentService;
import com.twilio.appointmentreminders.util.EntityManagerBuilder;
import com.twilio.appointmentreminders.util.RequestHandler;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import spark.Spark;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class AppointmentControllerTest {
    private static EntityManagerFactory emFactory;
    private static EntityManager em;
    private static AppointmentService service;

    @BeforeClass public static void beforeClass() {
        Server.main(null);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        emFactory = EntityManagerBuilder.getFactory();
        em = emFactory.createEntityManager();
        service = new AppointmentService(em);
    }

    @AfterClass public static void afterClass() {
        Spark.stop();
        if (em != null) {
            em.close();
        }
        if (emFactory != null) {
            emFactory.close();
        }
    }

    @Before public void cleanUp() {
        service.deleteAll();
    }

    @Test public void testIndex() {
        Appointment appointment =
            new Appointment("Appointment to find", "+593999031123", 1000, "08-07-2015 12:00AM",
                "America/Guayaquil");
        service.create(appointment);

        RequestHandler request = new RequestHandler();
        String response = request.makeServiceCall("http://localhost:4567/", RequestHandler.GET);
        assertTrue(response.contains("Appointment to find"));
    }

    @Test public void testCreatePage() {
        RequestHandler request = new RequestHandler();
        String response = request.makeServiceCall("http://localhost:4567/new", RequestHandler.GET);
        assertTrue(response.contains("Create New Appointment"));
    }

    @Test public void testCreate() {
        assertThat(service.count(), is(0L));

        RequestHandler request = new RequestHandler();
        List<NameValuePair> params = new ArrayList<>(5);
        params.add(new BasicNameValuePair("name", "Appointment Name"));
        params.add(new BasicNameValuePair("phoneNumber", "+593999012345"));
        params.add(new BasicNameValuePair("date", "07-31-2015 03:39pm"));
        params.add(new BasicNameValuePair("delta", "15"));
        params.add(new BasicNameValuePair("timeZone", "America/Guayaquil"));

        request.makeServiceCall("http://localhost:4567/create", RequestHandler.POST, params);

        assertThat(service.count(), is(1L));
    }

    @Test public void testDelete() {
        Appointment appointment =
            new Appointment("Appointment Test", "+593999012345", 1000, "08-07-2015 12:00AM",
                "America/Guayaquil");
        service.create(appointment);
        Long id = appointment.getId();

        assertThat(service.count(), is(1L));

        RequestHandler request = new RequestHandler();
        List<NameValuePair> params = new ArrayList<>(1);
        params.add(new BasicNameValuePair("id", id.toString()));

        request.makeServiceCall("http://localhost:4567/delete", RequestHandler.POST, params);

        assertThat(service.count(), is(0L));
    }
}
