package com.ericlindau.psx.core.processing;

import com.ericlindau.psx.config.Configured;
import com.ericlindau.psx.core.polling.Pollable;

import java.util.HashMap;
import java.util.List;

public class DigitalValue extends Value {

  @Configured
  private boolean singular;
  @Configured
  private String inactive;
  @Configured
  private boolean toggle;
  @Configured
  private boolean unpressed;

  HashMap<Pollable, String> actives; // TODO: Set initial capacities when injecting
  HashMap<Pollable, String> inactives;

  public DigitalValue(List<Pollable> components) {
    super(components);
  }

  // TODO: Memory/GC optimizations
  public String getPollData() {
    for (Pollable component : components) {
      if (component.getPollData() > 0) {
        return actives.get(component);
      }
    }

    return inactive;
//    } else {
//      StringBuilder literal = new StringBuilder();
//
//      for (Component component : components) {
//        if (component.getPollData() > 0) {
//          literal.append(actives.get(component));
//        } else {
//          literal.append(inactives.get(component));
//        }
//      }
//
//      return literal.toString();
//    }
  }

}
