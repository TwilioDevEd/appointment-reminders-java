package com.twilio.appointmentreminders.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertTrue;

public class AppointmentTest {
    private EntityManagerFactory emFactory;
    private EntityManager em;

    @Before
    public void setUp() throws Exception {
        try {
            emFactory = Persistence.createEntityManagerFactory("Appointments-Persistence-Test");
            em = emFactory.createEntityManager();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        if (em != null) {
            em.close();
        }
        if (emFactory != null) {
            emFactory.close();
        }
    }

    @Test
    public void testPersistence() {
        try {
            em.getTransaction().begin();
            Appointment appointment = new Appointment("Mario", "+593999031619", 1000, Calendar.getInstance(), "America/Guayaquil");

            em.persist(appointment);
            assertTrue(em.contains(appointment));

            em.getTransaction().rollback();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            ex.printStackTrace();
        }
    }
}