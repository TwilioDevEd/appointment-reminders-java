package com.twilio.appointmentreminders.models;

import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Calendar;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AppointmentServiceTest {

    private AppointmentService service;

    @Before
    public void createService() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Appointments-Persistence-Test");
        service = new AppointmentService(factory.createEntityManager());
        service.deleteAll();
    }

    @Test
    public void testCreate() {
        assertThat(service.count(), is(0L));

        Appointment appointment = new Appointment("Mario", "+593999031619", 1000, Calendar.getInstance(), "America/Guayaquil");
        service.create(appointment);

        assertThat(service.count(), is(1L));
    }

    @Test
    public void testDeleteAll() {
        Appointment appointment = new Appointment("Mario", "+593999031619", 1000, Calendar.getInstance(), "America/Guayaquil");
        service.create(appointment);

        Appointment appointment2 = new Appointment("Mario2", "+1234567890", 100, Calendar.getInstance(), "America/Guayaquil");
        service.create(appointment2);

        assertThat(service.count(), is(2L));

        service.deleteAll();

        assertThat(service.count(), is(0L));
    }

    @Test
    public void testFindAll() {
        Appointment appointment = new Appointment("Mario", "+593999031619", 1000, Calendar.getInstance(), "America/Guayaquil");
        service.create(appointment);

        Appointment appointment2 = new Appointment("Mario2", "+1234567890", 100, Calendar.getInstance(), "America/Guayaquil");
        service.create(appointment2);

        List<Appointment> result = service.findAll();

        assertThat(result.size(), is(2));
    }

    @Test
    public void testCount() {
        Appointment appointment = new Appointment("Mario", "+593999031619", 1000, Calendar.getInstance(), "America/Guayaquil");
        service.create(appointment);

        assertThat(service.count(), is(1L));
    }
}