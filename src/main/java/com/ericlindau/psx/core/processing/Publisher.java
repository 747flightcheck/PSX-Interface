package com.ericlindau.psx.core.processing;

import com.ericlindau.psx.out.Output;
import net.java.games.input.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Delegates controller state changes to destinations.
 */
public class Publisher {
  private final Output out;
  private final Mapper mapper;
//  private final HashMap<Variable, String> variableStates;

  // TODO: Map component from event to something... what? Variable would be fine.
  public Publisher(Output out, List<Variable> variables, Mapper mapper) {
    this.out = out;
    this.mapper = mapper;
    // TODO: Configurable ignored controllers (e.g. mouse/keyboard)
//    this.variableStates = new HashMap<Variable, String>();
    for (Variable variable : variables) {
      pushVariable(variable);
    }
  }

  private void pushVariable(Variable variable) {
    String newState = variable.getPollData();
    out.push(newState);
  }

  public void update(Event event) {
    Component c = event.getComponent();
    Variable toUpdate = this.mapper.getVariable(c);
    if (toUpdate != null && toUpdate.isActive()) {
      pushVariable(toUpdate);
    }
  }
}
