package com.ericlindau.psx.core.events;

import com.ericlindau.psx.core.processing.Variable;

public class Event {
  public Variable variable;
  public String pollData;

  public Event(Variable variable, String pollData) {
    this.variable = variable;
    this.pollData = pollData;
  }
}
