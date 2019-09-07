package com.ericlindau.psx.core.polling;

import com.ericlindau.psx.config.Configurable;
import com.ericlindau.psx.config.Configured;
import net.java.games.input.Component;

public class GenericPollable extends Configurable implements Pollable {
  @Configured
  private String name;
  @Configured
  private long min, max; // TODO: Figure out how to set default values

  private Component component;

  public void setComponent(Component component) {
    this.component = component;
  }

  public String getName() {
    return this.name;
  }

  @Override
  public String toString() {
    return this.getName();
  }

  @Override
  public float getPollData() {
    try {
      float poll = component.getPollData();
      return (max * poll) + (min * (1 - poll));
    } catch (NullPointerException n) {
      // TODO: Use centered option to return 0 or max / 2 (?)
      return 0;
    }
  }

  @Override
  public boolean hasComponent() {
    return this.component != null;
  }
}
