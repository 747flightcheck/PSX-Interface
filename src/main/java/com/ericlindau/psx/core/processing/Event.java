package com.ericlindau.psx.core.processing;

public class Event {
  public Variable variable;
  public String pollData;

  public Event(Variable variable, String pollData) {
    this.variable = variable;
    this.pollData = pollData;
  }
}
