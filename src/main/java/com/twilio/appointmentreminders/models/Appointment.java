package com.twilio.appointmentreminders.models;

import javax.persistence.*;
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

  @Column(name = "time", columnDefinition= "TIMESTAMP WITH TIME ZONE")
  @Temporal(TemporalType.TIMESTAMP)
  private Date time;

  public String getName(){
    return this.name;
  }

  public void setName(String name){
    this.name = name;
  }

  public String getPhoneNumber(){
    return this.name;
  }

  public void setPhoneNumber(String phoneNumber){
    this.phoneNumber = phoneNumber;
  }

  public int getDelta(){
    return this.delta;
  }

  public void setDelta(int delta){
    this.delta = delta;
  }

  public Date getTime(){
    return this.time;
  }

  public void setTime(Date time){
    this.time = time;
  }
}
