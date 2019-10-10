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

  private boolean unpressed;
  private boolean active;
  private Pollable previous;

  private final Map<Pollable, String> actives; // TODO: Set initial capacities when injecting
  private final Map<Pollable, String> inactives; // TODO: Remove?

  public DigitalValue(List<Pollable> components, Map<Pollable, String> actives, Map<Pollable, String> inactives, TomlTable... tables) {
    super(components, tables);
    unpressed = true;
    this.actives = actives;
    this.inactives = inactives;
  }

  // TODO: Memory/GC optimizations (i.e. use container String)
  public String getPollData() {
    for (Pollable component : components) {
      if (component.getPollData() > 0) {
        if (toggle && component == previous && unpressed) {
          unpressed = false;
          active = !active;
          return active ? actives.get(component) : inactive;
        }
        unpressed = false;
        previous = component;
        return actives.get(component);
      }
    }

    if (toggle) {
      unpressed = true;
      return active ? actives.get(previous) : inactive;
    }
    return inactive;
  }
}
