package com.ericlindau.psx.core.polling;

import com.ericlindau.psx.config.Configurable;
import com.ericlindau.psx.config.Configured;
import net.java.games.input.Component;

abstract class AbstractPollable extends Configurable implements Pollable {
  @Configured
  private String name;

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
}
