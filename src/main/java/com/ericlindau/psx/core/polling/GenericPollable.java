package com.ericlindau.psx.core.polling;

import com.ericlindau.psx.config.Configurable;
import com.ericlindau.psx.config.Configured;
import net.java.games.input.Component;

abstract class GenericPollable extends Configurable implements Pollable {
  @Configured
  private String name;

  private boolean inverted;
  private Component component;

  public Component getComponent() {
    return component;
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

  public void setInverted(boolean inverted) {
    this.inverted = inverted;
  }

  @Override
  public float getPollData() {
    return this.component.getPollData() * (this.inverted ? -1 : 1);
  }
}
