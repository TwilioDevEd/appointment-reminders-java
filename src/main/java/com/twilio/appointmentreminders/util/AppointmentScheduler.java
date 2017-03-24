package com.twilio.appointmentreminders.util;

import com.twilio.Twilio;
import com.twilio.appointmentreminders.models.Appointment;
import com.twilio.appointmentreminders.models.AppointmentService;
import com.twilio.exception.TwilioException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManagerFactory;

public class AppointmentScheduler implements Job {

  private static Logger logger = LoggerFactory.getLogger(AppointmentScheduler.class);

  private static AppSetup appSetup = new AppSetup();

  public static final String ACCOUNT_SID = appSetup.getAccountSid();
  public static final String AUTH_TOKEN = appSetup.getAuthToken();
  public static final String TWILIO_NUMBER = appSetup.getTwilioPhoneNumber();

  public AppointmentScheduler() {}

  public void execute(JobExecutionContext context) throws JobExecutionException {
    AppSetup appSetup = new AppSetup();

    EntityManagerFactory factory = appSetup.getEntityManagerFactory();
    AppointmentService service = new AppointmentService(factory.createEntityManager());

    // Initialize the Twilio client
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

    JobDataMap dataMap = context.getJobDetail().getJobDataMap();

    String appointmentId = dataMap.getString("appointmentId");

    Appointment appointment = service.getAppointment(Long.parseLong(appointmentId, 10));
    if (appointment != null) {
      String name = appointment.getName();
      String phoneNumber = appointment.getPhoneNumber();
      String date = appointment.getDate();
      String messageBody = "Remember: " + name + ", on " + date + " you have an appointment!";

      try {
        Message message = Message
                .creator(new PhoneNumber(phoneNumber), new PhoneNumber(TWILIO_NUMBER), messageBody)
                .create();
        System.out.println("Message sent! Message SID: " + message.getSid());
      } catch(TwilioException e) {
        logger.error("An exception occurred trying to send a message with Twilio: {] ", e.getMessage());
        throw e;
      }


    }
  }
}
