package com.twilio.appointmentreminders.util;

import com.twilio.appointmentreminders.models.Appointment;
import com.twilio.appointmentreminders.models.AppointmentService;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AppointmentScheduler implements Job {
    private static AppSetup appSetup = new AppSetup();

    public static final String ACCOUNT_SID = appSetup.getACCOUNT_SID();
    public static final String AUTH_TOKEN = appSetup.getAUTH_TOKEN();
    public static final String TWILIO_PHONE_NUMBER = appSetup.getTWILIO_PHONE_NUMBER();

    public AppointmentScheduler() {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        AppSetup appSetup = new AppSetup();

        EntityManagerFactory factory = appSetup.getEntityManagerFactory();
        AppointmentService service = new AppointmentService(factory.createEntityManager());

        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        String appointmentId = dataMap.getString("appointmentId");

        Appointment appointment = service.getAppointment(Long.parseLong(appointmentId, 10));
        if (appointment != null) {
            String name = appointment.getName();
            String phoneNumber = appointment.getPhoneNumber();
            String date = appointment.getDate();
            String timeZone = appointment.getTimeZone();
            String messageBody = "Remember: " + name + ", on " + date + " " + timeZone;

            // Build a filter for the MessageList
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("Body", messageBody));
            params.add(new BasicNameValuePair("To", phoneNumber));
            params.add(new BasicNameValuePair("From", TWILIO_PHONE_NUMBER));

            MessageFactory messageFactory = client.getAccount().getMessageFactory();
            Message message = null;
            try {
                message = messageFactory.create(params);
                System.out.println(message.getSid());
            } catch (TwilioRestException e) {
                System.out.println(
                    "An error occurred while trying to send the message: " + e.getMessage());
            }
        }
    }
}
