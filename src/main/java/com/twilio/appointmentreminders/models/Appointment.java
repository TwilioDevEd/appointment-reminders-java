package com.twilio.appointmentreminders.models;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "delta")
    private int delta;

    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar time;

    @Column(name = "timeZone")
    private String timeZone;

    public Appointment() {

    }

    public Appointment(String name, String phoneNumber, int delta, Calendar time, String timeZone) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.delta = delta;
        this.time = time;
        this.timeZone = timeZone;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return this.name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getDelta() {
        return this.delta;
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }

    public Calendar getTime() {
        return this.time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
