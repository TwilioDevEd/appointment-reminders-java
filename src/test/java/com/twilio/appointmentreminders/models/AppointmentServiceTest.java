package com.twilio.appointmentreminders.models;

import com.twilio.appointmentreminders.util.AppSetup;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AppointmentServiceTest {
  private static EntityManagerFactory emFactory;
  private static EntityManager em;
  private static AppointmentService service;

  @BeforeClass
  public static void createService() {
    AppSetup appSetup = new AppSetup();
    emFactory = appSetup.getEntityManagerFactory();
    em = emFactory.createEntityManager();
    service = new AppointmentService(em);
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
    service.deleteAll();
  }

  @Test
  public void testCreate() {
    assertThat(service.count(), is(0L));

    Appointment appointment =
        new Appointment("Mario", "+593999012345", 1000, "08-07-2015 12:00AM", "America/Guayaquil");
    service.create(appointment);

    assertThat(service.count(), is(1L));
  }

  @Test
  public void testDeleteAll() {
    Appointment appointment =
        new Appointment("Mario", "+593999012345", 1000, "08-07-2015 12:00AM", "America/Guayaquil");
    service.create(appointment);

    Appointment appointment2 =
        new Appointment("Mario2", "+1234567890", 100, "08-07-2015 12:00AM", "America/Guayaquil");
    service.create(appointment2);

    assertThat(service.count(), is(2L));

    service.deleteAll();

    assertThat(service.count(), is(0L));
  }

  @Test
  public void testFindAll() {
    Appointment appointment =
        new Appointment("Mario", "+593999012345", 1000, "08-07-2015 12:00AM", "America/Guayaquil");
    service.create(appointment);

    Appointment appointment2 =
        new Appointment("Mario2", "+1234567890", 100, "08-07-2015 12:00AM", "America/Guayaquil");
    service.create(appointment2);

    List<Appointment> result = service.findAll();

    assertThat(result.size(), is(2));
  }

  @Test
  public void testCount() {
    Appointment appointment =
        new Appointment("Mario", "+593999012345", 1000, "08-07-2015 12:00AM", "America/Guayaquil");
    service.create(appointment);

    assertThat(service.count(), is(1L));
  }
}
