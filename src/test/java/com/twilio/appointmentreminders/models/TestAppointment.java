package com.twilio.appointmentreminders.models;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.Date;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

public class TestAppointment {
  private EntityManagerFactory emFactory;
  private EntityManager em;

  @Before
  public void setUp() throws Exception {
      try {
          emFactory = Persistence.createEntityManagerFactory("Appointments-Persistence");
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
      Appointment appointment = new Appointment();
      appointment.setName("Mario");
      appointment.setPhoneNumber("+593999031619");
      appointment.setTime(new Date());
      appointment.setDelta(10000);

      em.persist(appointment);
      assertTrue(em.contains(appointment));

      em.getTransaction().rollback();
    } catch (Exception ex) {
      em.getTransaction().rollback();
      ex.printStackTrace();
    }
  }
}