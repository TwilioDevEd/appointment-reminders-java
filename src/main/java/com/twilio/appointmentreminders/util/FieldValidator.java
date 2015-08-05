package com.twilio.appointmentreminders.util;

import spark.Request;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FieldValidator {
  private List<String> fields;

  public FieldValidator(String[] fields) {
    this.fields = Arrays.asList(fields);
  }

  public boolean valid(Request request) {
    Predicate<String> p1 = e -> e == "INVALID";

    Stream<String> validations = fields.stream().map(p -> {
      if (request.queryParams(p).length() == 0) {
        return "INVALID";
      } else {
        return "VALID";
      }
    });

    if (validations.anyMatch(p1)) {
      return false;
    } else {
      return true;
    }
  }
}
