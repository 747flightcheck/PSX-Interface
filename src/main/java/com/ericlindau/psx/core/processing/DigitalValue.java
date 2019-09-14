package com.ericlindau.psx.core.processing;

import com.ericlindau.psx.config.Configured;
import com.ericlindau.psx.core.polling.Pollable;
import net.consensys.cava.toml.TomlTable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DigitalValue extends Value {
  @Configured
  private boolean singular;
  @Configured
  private String inactive;
  @Configured
  private boolean toggle;
  @Configured
  private boolean unpressed;

  private final Map<Pollable, String> actives; // TODO: Set initial capacities when injecting
//  private final Map<Pollable, String> inactives;

  public DigitalValue(List<Pollable> components, Map<Pollable, String> actives, Map<Pollable, String> inactives, TomlTable... tables) {
    super(components, tables);
    this.actives = actives;
//    this.inactives = inactives;
  }

  // TODO: Memory/GC optimizations
  public String getPollData() {
    // TODO: Initialize actives & other maps
    for (Pollable component : components) {
      if (component.getPollData() > 0) {
        return this.actives.get(component);
      }
    }

    return this.inactive;
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
