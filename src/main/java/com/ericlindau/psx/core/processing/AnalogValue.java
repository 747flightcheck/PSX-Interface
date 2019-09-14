package com.ericlindau.psx.core.processing;

import com.ericlindau.psx.config.Configured;
import com.ericlindau.psx.core.polling.Pollable;
import net.consensys.cava.toml.TomlTable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalogValue extends Value {
  @Configured
  private long min, max;
  @Configured
  private boolean centered, inverted;

  // TODO: Configure from TOML - type of field is HashMap -> look for KV
  HashMap<Integer, String> thresholds;

  public AnalogValue(List<Pollable> components, TomlTable... tables) {
    super(components, tables);
  }

  // TODO: Optimize for GC/memory
  public String getPollData() {
    Long literal = 0L;

    for (Pollable component : components) {
      float a = component.getPollData();
      literal += java.lang.Math.round(a);
//      literal += Math.round((max * a) + (min * (1 - a))); // Scaling (SHOULD TAKE PLACE IN POLLABLE)
    }

    // Bounds
    if (literal > max) {
      literal = max;
    }
    if (literal < min) {
      literal = min;
    }

    if (centered) {
      literal /= 2; // TODO: Verify correctness
    }

    if (inverted) {
      literal *= -1;
    }

    if (thresholds != null) {
      for (Map.Entry<Integer, String> threshold : thresholds.entrySet()) {
        if (literal < threshold.getKey()) {
          return threshold.getValue();
        }
      }
    }

    return literal.toString();
  }

  public long getMin() {
    return this.min;
  }

  public long getMax() {
    return this.max;
  }

}
