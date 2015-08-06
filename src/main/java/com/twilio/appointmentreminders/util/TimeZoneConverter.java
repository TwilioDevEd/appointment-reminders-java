package com.twilio.appointmentreminders.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Class that handles dates different time zones. Converts date strings from and into UTC format.
 */
public class TimeZoneConverter {
  public static String getDateFromUTC(String date, String timeZone) {
    DateTimeZone zone = DateTimeZone.forID(timeZone);
    DateTimeZone zoneUTC = DateTimeZone.UTC;

    DateTime dt;
    DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-dd-yyyy hh:mma");
    formatter = formatter.withZone(zoneUTC);
    dt = formatter.parseDateTime(date);
    formatter = formatter.withZone(zone);
    String adjustedDate = dt.toString(formatter);

    return adjustedDate;
  }

  public static String getDateToUTC(String date, String timeZone) {
    DateTimeZone zone = DateTimeZone.forID(timeZone);
    DateTimeZone zoneUTC = DateTimeZone.UTC;

    DateTime dt;
    DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-dd-yyyy hh:mma");
    formatter = formatter.withZone(zone);
    dt = formatter.parseDateTime(date);
    formatter = formatter.withZone(zoneUTC);
    String adjustedDate = dt.toString(formatter);

    return adjustedDate;
  }
}
