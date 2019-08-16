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
  private HashMap<Component, Variable> updateMappings;
  private HashMap<Variable, String> variableStates;

  // TODO: Refactor
  public Publisher() {
    // TODO: Ignored controllers (e.g. mouse/keyboard)
    this.updateMappings = new HashMap<Component, Variable>();

  }

  /** Update top-level variables related to the given Controller. */
  private void updateVariables(Controller controller) {
    Component[] components = controller.getComponents();
    for (Component component : components) {
      Variable toUpdate = updateMappings.get(component);
      if (toUpdate != null) {
        String currentState = variableStates.get(toUpdate);
        String newState = toUpdate.getPollData();

        if (!currentState.equals(newState)) {
          variableStates.put(toUpdate, newState); // TODO: Send this ret. val. on network
        }
      }
    }
  }

}
