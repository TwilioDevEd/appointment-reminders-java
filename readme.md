# Appointment Reminders with Java

[![Build Status](https://travis-ci.org/TwilioDevEd/appointment-reminders-java.svg)](https://travis-ci.org/TwilioDevEd/appointment-reminders-java)

## Deploy to Heroku

[![Deploy](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)

## Installing dependencies

This app runs on Java 8, and has been tested on version 1.8.0_51. 

You can install dependencies and build the project using [Maven](https://maven.apache.org/):
```
mvn clean install -DskipTests
```

## Database migrations

First, make sure [Postgres](http://www.postgresql.org/) is running on your system.

You'll need to set the environment variables specified in `.env.example`
to match your local configuration and `source` that file, or set the
environment variables manually.

The database schema is managed using [Flyway](https://github.com/flyway/flyway).

Migrate the database:
```
mvn compile exec:java -Dexec.mainClass="com.twilio.appointmentreminders.Migrator"
```
## Running the application

The application can be run with:

```
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
