package com.twilio.appointmentreminders.models;

import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AppointmentServiceTest {

    private AppointmentService service;

    @Before
    public void createService() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Appointments-Persistence");
        service = new AppointmentService(factory.createEntityManager());
        service.deleteAll();
    }

    @Test
    public void testCreate() {
        assertThat(service.count(), is(0L));

        Appointment appointment = new Appointment();
        appointment.setName("Mario");
        appointment.setPhoneNumber("+593999031619");
        appointment.setTime(new Date());
        appointment.setDelta(10000);
        service.create(appointment);

        assertThat(service.count(), is(1L));
    }

}