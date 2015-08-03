# Twilio's Appointment Reminders with Java

[![Build Status](https://travis-ci.org/TwilioDevEd/appointment-reminders-java.svg)](https://travis-ci.org/TwilioDevEd/appointment-reminders-java)

## Installing dependencies

This app runs on JAVA 8, and has been tested on version 1.8.0_51

You can install dependencies and build the project using:
```
mvn clean install
```

## Database migrations

First, make sure [Postgres](http://www.postgresql.org/) is running on your system.

You'll need to set the environment variables specified in `.env.example`
to match your local configuration and `source` that file, or set the
environment variables manually.

The database schema is managed using [Flyway](https://github.com/flyway/flyway).

Migrate the database:
```
mvn compile flyway:migrate
```
## Running the application

The application can be run with:

```
mvn compile exec:java -Dexec.mainClass="com.twilio.appointmentreminders.Server"
```
First, you have set up the environment variables and run the database migrations for the
previous command to work

## Run the tests

Assuming you have configured the application for your local test
environment, you can then use Flyway to migrate the test database
(by setting the correct `DB_URL`) and then use Maven
to run the tests:

```
mvn compile test
```
