package com.twilio.appointmentreminders.models;

import com.twilio.appointmentreminders.util.EntityManagerBuilder;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import static org.junit.Assert.assertTrue;

public class AppointmentTest {
    private static EntityManagerFactory emFactory;
    private static EntityManager em;

    @BeforeClass
    public static void setUp() throws Exception {
        try {
            emFactory = EntityManagerBuilder.getFactory();
            em = emFactory.createEntityManager();
        } catch (Exception e) {
            System.out.println("Unable to create Entity Manager");
        }
    }

    @AfterClass
    public static void tearDown() throws Exception {
        if (em != null) {
            em.close();
        }
        if (emFactory != null) {
            emFactory.close();
        }
    }

    @Before
    public void cleanUp() {
        getTransaction().begin();
        Query query = em.createQuery("DELETE FROM Appointment");
        query.executeUpdate();
        getTransaction().commit();
    }

    @Test
    public void testPersistence() {
        try {
            em.getTransaction().begin();
            Appointment appointment = new Appointment("Mario", "+593999031619", 1000, "08-07-2015 12:00AM", "America/Guayaquil");

            em.persist(appointment);
            assertTrue(em.contains(appointment));

            em.getTransaction().rollback();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            ex.printStackTrace();
        }
    }

    private EntityTransaction getTransaction() {
        return em.getTransaction();
    }
}