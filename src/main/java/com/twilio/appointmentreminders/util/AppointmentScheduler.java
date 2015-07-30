package com.twilio.appointmentreminders.util;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.List;

public class AppointmentScheduler implements Job {
    public static final String ACCOUNT_SID = "ACe087e572e3dd63bce01ecf70e01cd7bb";
    public static final String AUTH_TOKEN = "5c011a34c945d6e68a2adbf74a2b071a";
    public static final String PHONE_NUMBER = "+12569071353";

    public AppointmentScheduler() {
    }
    public void execute(JobExecutionContext context) throws JobExecutionException {
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

        // Build a filter for the MessageList
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("Body", "Mensaje de prueba!"));
        params.add(new BasicNameValuePair("To", "+593999031619"));
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
