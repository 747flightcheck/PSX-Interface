package com.ericlindau.psx.core.processing;

import net.java.games.input.*;
import net.java.games.input.Event;

import java.util.*;

/**
 * Delegates controller state changes to destinations.
 */
public class Publisher {
  private final Mapper mapper;
  private final Queue<com.ericlindau.psx.core.processing.Event> events;

  // TODO: Map component from event to something... what? Variable would be fine.
  public Publisher(List<Variable> variables, Mapper mapper) {
    this.mapper = mapper;
    this.events = new LinkedList<com.ericlindau.psx.core.processing.Event>();
    // TODO: Configurable ignored controllers (e.g. mouse/keyboard)
    for (Variable variable : variables) {
      pushVariable(variable);
    }
  }

  private void pushVariable(Variable variable) {
    events.add(new com.ericlindau.psx.core.processing.Event(variable, variable.getPollData()));
  }

  public void update(Event event) {
    Component c = event.getComponent();
    Variable toUpdate = this.mapper.getVariable(c);
    if (toUpdate != null && toUpdate.isActive()) {
      pushVariable(toUpdate);
    }
  }

  public Queue<com.ericlindau.psx.core.processing.Event> getQueue() {
    return events;
  }
}
