<a href="https://www.twilio.com">
  <img src="https://static0.twilio.com/marketing/bundles/marketing/img/logos/wordmark-red.svg" alt="Twilio" width="250" />
</a>

# Appointment Reminders with Java

This sample application demonstrates how to send appointment reminders to customers when they have an upcoming appointment.

[Read the full tutorial here](https://www.twilio.com/docs/tutorials/walkthrough/appointment-reminders/java/spark)!

[![Build Status](https://travis-ci.org/TwilioDevEd/appointment-reminders-java.svg)](https://travis-ci.org/TwilioDevEd/appointment-reminders-java)

## Dependencies

This app runs on Java 8.

You will also need [Postgres](http://www.postgresql.org/) running on your system.

## Run the application


1. Copy the sample configuration file and edit it to match your configuration.

  ```bash
  $ cp .env.example .env
  ```

 You can find your `TWILIO_ACCOUNT_SID` and `TWILIO_AUTH_TOKEN` in your
 [Twilio Account Settings](https://www.twilio.com/user/account/settings).
 You will also need a `TWILIO_NUMBER`, which you may find [here](https://www.twilio.com/user/account/phone-numbers/incoming).
 `DATABASE_URL` should point to the local Postgres database you want to use for this project.

 The load the environment variables:

 ```bash
 $ source .env
 ```

1. The database schema is managed using [Flyway](https://github.com/flyway/flyway). Execute its migrations with:

  ```bash
  $ mvn compile exec:java -Dexec.mainClass="com.twilio.appointmentreminders.Migrator"
  ```

1. The application can be run with:

  ```bash
  mvn compile exec:java -Dexec.mainClass="com.twilio.appointmentreminders.Server"
  ```

## Run the tests

Assuming you have configured the application for your local test
environment, you can then use Flyway to migrate the test database
(by setting the correct `DATABASE_URL`) and then use Maven
to run the tests:

```
mvn compile test
```

## Meta

* No warranty expressed or implied. Software is as is. Diggity.
* [MIT License](http://www.opensource.org/licenses/mit-license.html)
* Lovingly crafted by Twilio Developer Education.
