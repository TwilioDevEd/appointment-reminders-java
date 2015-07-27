package com.twilio.appointmentreminders.models;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class AppointmentService {

    private EntityManager entityManager;

    public AppointmentService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void create(Appointment appointment) {
        getTransaction().begin();
        entityManager.persist(appointment);
        getTransaction().commit();
    }

    public List<Appointment> findAll() {
        Query query = entityManager.createQuery("SELECT a FROM Appointment a");
        return query.getResultList();
    }

    public void deleteAll() {
        getTransaction().begin();
        Query query = entityManager.createQuery("DELETE FROM Appointment");
        query.executeUpdate();
        getTransaction().commit();
    }

    public Long count() {
        Query query = entityManager.createQuery("SELECT COUNT(a) FROM Appointment a");
        return (Long) query.getSingleResult();
    }

    private EntityTransaction getTransaction() {
        return entityManager.getTransaction();
    }

}
