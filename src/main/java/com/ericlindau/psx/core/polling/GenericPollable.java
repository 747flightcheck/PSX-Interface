package com.ericlindau.psx.core.polling;

import com.ericlindau.psx.config.Configurable;
import com.ericlindau.psx.config.Configured;
import com.ericlindau.psx.core.processing.Math;
import net.java.games.input.Component;

public class GenericPollable extends Configurable implements Pollable {
  @Configured
  private String name;
  @Configured
  private long min, max;
  @Configured
  private boolean centered;

  private Component component;

  public GenericPollable() {
    this.name = "";
    this.min = -1;
    this.max = 1;
    this.centered = false;
  }

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
    if (component == null) {
      return centered ? (min + max) / 2 : 0;
    } else {
      float poll = component.getPollData();
      // TODO: Parametrize min/max args. in config
      return Math.rescale(poll, -1, 1, min, max);
    }
  }

  @Override
  public boolean hasComponent() {
    return this.component != null;
  }
}
