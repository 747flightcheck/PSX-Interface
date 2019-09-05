package com.ericlindau.psx.core.processing;

import net.java.games.input.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Delegates controller state changes to destinations.
 */
public class Publisher {
  private final Mapper mapper;
  private final HashMap<Variable, String> variableStates;

  // TODO: Map component from event to something... what? Variable would be fine.
  public Publisher(List<Variable> variables, Mapper mapper) {
    this.mapper = mapper;
    // TODO: Configurable ignored controllers (e.g. mouse/keyboard)
    this.variableStates = new HashMap<Variable, String>();
    for (Variable variable : variables) {
      this.variableStates.put(variable, variable.getPollData());
    }
  }

  public void update(Event event) {
    Component c = event.getComponent();
    Variable toUpdate = this.mapper.getVariable(c); // TODO: Why toUpdate is always null?
    if (toUpdate != null && toUpdate.isActive()) {
      String currentState = variableStates.get(toUpdate);
      String newState = toUpdate.getPollData();

      System.out.println(variableStates.put(toUpdate, newState)); // TODO: Send this ret. val. to subs
//      if (!currentState.equals(newState)) {
//        System.out.println(variableStates.put(toUpdate, newState)); // TODO: Send this ret. val. to subs
//      }
    }
  }
}
