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
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppointmentScheduler implements Job {

    static Map<String, String> env = System.getenv();

    public static final String ACCOUNT_SID = env.get("ACCOUNT_SID");
    public static final String AUTH_TOKEN = env.get("AUTH_TOKEN");
    public static final String TWILIO_PHONE_NUMBER = env.get("TWILIO_PHONE_NUMBER");

    public AppointmentScheduler() {
    }
    public void execute(JobExecutionContext context) throws JobExecutionException {
        EntityManagerFactory factory = EntityManagerBuilder.getFactory();
        AppointmentService service = new AppointmentService(factory.createEntityManager());

        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        String appointmentId = dataMap.getString("appointmentId");

        Appointment appointment = service.getAppointment(Long.parseLong(appointmentId, 10));
        if (appointment != null) {
            String phoneNumber = appointment.getPhoneNumber();
            String date = appointment.getDate();
            String timeZone = appointment.getTimeZone();

            // Build a filter for the MessageList
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("Body", "You have an appointment on " + date + " " + timeZone));
            params.add(new BasicNameValuePair("To", phoneNumber));
            params.add(new BasicNameValuePair("From", TWILIO_PHONE_NUMBER));

            MessageFactory messageFactory = client.getAccount().getMessageFactory();
            Message message = null;
            try {
                message = messageFactory.create(params);
                System.out.println(message.getSid());
            } catch (TwilioRestException e) {
                e.printStackTrace();
            }
        }
    }
}
