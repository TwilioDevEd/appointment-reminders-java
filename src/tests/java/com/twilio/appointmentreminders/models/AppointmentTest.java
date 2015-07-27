String PERSISTENCE_UNIT_NAME = "Appointments-Persistence";
EntityManagerFactory factory;

factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
EntityManager em = factory.createEntityManager();

System.out.println("CONSOLE START");
em.getTransaction().begin();
Appointment appointment = new Appointment();
appointment.setName("Mario");
appointment.setPhoneNumber("+593999031619");
appointment.setTime(new Date());
appointment.setDelta(10000);

em.persist(appointment);
em.getTransaction().commit();
em.close();