package com.twilio.appointmentreminders.models;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

/** Class that provides an abstraction to an Appointment's entity database access */
public class AppointmentService {
  private EntityManager entityManager;

  public AppointmentService(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public Appointment getAppointment(Long id) {
    return entityManager.find(Appointment.class, id);
  }

  public void create(Appointment appointment) {
    getTransaction().begin();
    entityManager.persist(appointment);
    getTransaction().commit();
  }

  public void delete(Appointment appointment) {
    getTransaction().begin();
    entityManager.remove(appointment);
    getTransaction().commit();
  }

  @SuppressWarnings("unchecked")
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
