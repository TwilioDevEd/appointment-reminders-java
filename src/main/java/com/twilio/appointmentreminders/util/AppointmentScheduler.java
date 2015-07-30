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
import java.util.List;

public class AppointmentScheduler implements Job {
    public static final String ACCOUNT_SID = "ACe087e572e3dd63bce01ecf70e01cd7bb";
    public static final String AUTH_TOKEN = "5c011a34c945d6e68a2adbf74a2b071a";
    public static final String PHONE_NUMBER = "+12569071353";

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("Appointments-Persistence");
    AppointmentService service = new AppointmentService(factory.createEntityManager());

    public AppointmentScheduler() {
    }
    public void execute(JobExecutionContext context) throws JobExecutionException {
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        String appointmentId = dataMap.getString("appointmentId");

        Appointment appointment = service.getAppointment(Long.parseLong(appointmentId, 10));
        if (appointment != null) {
            String phoneNumber = appointment.getPhoneNumber();
            String date = TimeZoneConverter.getDateFromUTC(appointment.getDate(), appointment.getTimeZone());
            String timeZone = appointment.getTimeZone();

            // Build a filter for the MessageList
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("Body", "You have an appointment on " + date + " " + timeZone));
            params.add(new BasicNameValuePair("To", phoneNumber));
            params.add(new BasicNameValuePair("From", "+12569071353"));

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
