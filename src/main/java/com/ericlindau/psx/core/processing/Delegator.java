package com.ericlindau.psx.core.processing;

import net.java.games.input.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Delegates controller state changes to destinations.
 */
public class Delegator {
  private Controller[] controllers;
  private Component[] components;
  private List<Controller> activeControllers;
  private HashMap<Component, Variable> updateMappings;
  private HashMap<Variable, String> variableStates;

  // TODO: Refactor
  public Delegator() {
    // TODO: Ignored controllers (e.g. mouse/keyboard)
    this.controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
    List<Component> components = new ArrayList<Component>();
    for (Controller controller : this.controllers) {
      components.addAll(Arrays.asList(controller.getComponents()));
    }
    this.components = (Component[]) components.toArray(new Component[components.size()]);
    this.updateMappings = new HashMap<Component, Variable>();

    Event container = new Event();
    // TODO: Only loop through active controllers
    // TODO: ... 'active' <- has enough defined components to function
    for (Controller controller : this.controllers) {
      EventQueue queue = controller.getEventQueue();
      if (queue.getNextEvent(container)) {
        updateVariables(controller);
      }
    }
  }

//  public Controller[] getControllers() {
//    return controllers;
//  }

  public Component[] getComponents() {
    return components;
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
