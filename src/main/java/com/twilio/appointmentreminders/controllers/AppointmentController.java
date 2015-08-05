package com.twilio.appointmentreminders.controllers;

import com.twilio.appointmentreminders.models.Appointment;
import com.twilio.appointmentreminders.models.AppointmentService;
import com.twilio.appointmentreminders.util.AppointmentScheduler;
import com.twilio.appointmentreminders.util.FieldValidator;
import com.twilio.appointmentreminders.util.TimeZones;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import spark.ModelAndView;
import spark.Route;
import spark.TemplateViewRoute;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@SuppressWarnings({"rawtypes", "unchecked"})
public class AppointmentController {
  private Scheduler scheduler;
  private AppointmentService service;

  public AppointmentController(AppointmentService service, Scheduler scheduler) {
    this.service = service;
    this.scheduler = scheduler;
  }

  public TemplateViewRoute renderCreatePage = (request, response) -> {
    Map map = new HashMap();

    map.put("zones", timeZones());
    return new ModelAndView(map, "new.mustache");
  };

  public TemplateViewRoute index = (request, response) -> {
    Map map = new HashMap();

    List<Appointment> appointments = service.findAll();
    map.put("appointments", appointments);

    return new ModelAndView(map, "index.mustache");
  };

  public Route delete = (request, response) -> {
    String id = request.queryParams("id");
    Long idLong = Long.parseLong(id, 10);

    Appointment appointment = service.getAppointment(idLong);
    service.delete(appointment);

    response.redirect("/");
    return response;
  };

  public TemplateViewRoute create = (request, response) -> {
    FieldValidator validator =
        new FieldValidator(new String[] {"name", "phoneNumber", "date", "delta", "timeZone"});

    if (validator.valid(request)) {
      String name = request.queryParams("name");
      String phoneNumber = request.queryParams("phoneNumber");
      String date = request.queryParams("date");
      int delta = 0;
      try {
        delta = Integer.parseInt(request.queryParams("delta"));
      } catch (NumberFormatException e) {
        System.out.println("Invalid format number for appointment delta");
      }
      String timeZone = request.queryParams("timeZone");

      DateTimeZone zone = DateTimeZone.forID(timeZone);
      DateTimeZone zoneUTC = DateTimeZone.UTC;

      DateTime dt;
      DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-dd-yyyy hh:mma");
      formatter = formatter.withZone(zone);
      dt = formatter.parseDateTime(date);
      formatter = formatter.withZone(zoneUTC);
      String dateUTC = dt.toString(formatter);

      Appointment appointment = new Appointment(name, phoneNumber, delta, dateUTC, timeZone);
      service.create(appointment);

      scheduleJob(appointment);

      response.redirect("/");
    }

    Map map = new HashMap();

    map.put("zones", timeZones());
    return new ModelAndView(map, "new.mustache");
  };

  private void scheduleJob(Appointment appointment) {
    String appointmentId = appointment.getId().toString();

    DateTimeZone zone = DateTimeZone.forID(appointment.getTimeZone());
    DateTime dt;
    DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-dd-yyyy hh:mma");
    formatter = formatter.withZone(zone);
    dt = formatter.parseDateTime(appointment.getDate());
    Date finalDate = dt.minusMinutes(appointment.getDelta()).toDate();

    JobDetail job =
        newJob(AppointmentScheduler.class).withIdentity("Appointment_J_" + appointmentId)
            .usingJobData("appointmentId", appointmentId).build();

    Trigger trigger =
        newTrigger().withIdentity("Appointment_T_" + appointmentId).startAt(finalDate).build();

    try {
      scheduler.scheduleJob(job, trigger);
    } catch (SchedulerException e) {
      System.out.println("Unable to schedule the Job");
    }
  }

  private List<String> timeZones() {
    TimeZones tz = new TimeZones();

    return tz.getTimeZones();
  }
}
