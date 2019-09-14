package com.ericlindau.psx.core.processing;

import com.ericlindau.psx.core.polling.Pollable;
import net.java.games.input.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mapper {
  private final Map<Pollable, Component> pollableToComponent;

  private final Map<Component, Variable> componentToVariable;
  private final Map<Pollable, Variable> pollableToVariable;

  public Mapper(List<Variable> variables) {
    this.pollableToComponent = new HashMap<Pollable, Component>();
    this.componentToVariable = new HashMap<Component, Variable>();
    this.pollableToVariable = new HashMap<Pollable, Variable>();

    for (Variable variable : variables) {
      for (Value value : variable.getValues()) {
        for (Pollable pollable : value.components) {
          pollableToVariable.put(pollable, variable);
        }
      }
    }
  }

  // TODO: Change to boolean, verify in UI -> error if false
  public void set(Pollable pollable, Component component) {
    Component current = this.pollableToComponent.get(pollable);
//    if (current != null) {
    pollable.setComponent(component);
//    }
    this.pollableToComponent.put(pollable, component);

    Variable variable = pollableToVariable.get(pollable);
    this.componentToVariable.put(component, variable);
    variable.refreshActive();
  }

  Variable getVariable(Component component) {
    return componentToVariable.get(component);
  }
}
