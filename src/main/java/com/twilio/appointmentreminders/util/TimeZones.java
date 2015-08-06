package com.twilio.appointmentreminders.util;

import java.util.*;

/** Class that returns all the local system's available time zones. */
public class TimeZones {
  private static final String TIMEZONE_ID_PREFIXES =
      "^(Africa|America|Asia|Atlantic|Australia|Europe|Indian|Pacific)/.*";

  private List<String> timeZones = null;

  public List<String> getTimeZones() {
    if (timeZones == null) {
      initTimeZones();
    }
    return timeZones;
  }

  private void initTimeZones() {
    timeZones = new ArrayList<String>();
    final String[] timeZoneIds = TimeZone.getAvailableIDs();
    for (final String id : timeZoneIds) {
      if (id.matches(TIMEZONE_ID_PREFIXES)) {
        timeZones.add(id);
      }
    }
    Collections.sort(timeZones, new Comparator<String>() {
      public int compare(final String a, final String b) {
        return a.compareTo(b);
      }
    });
  }
}
