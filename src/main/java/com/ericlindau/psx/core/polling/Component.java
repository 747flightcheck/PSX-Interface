package com.ericlindau.psx.core.polling;

import net.java.games.input.Controller;

/**
 * Container for JInput Component with inversion and future property extension.
 */
public class Component implements net.java.games.input.Component {
  private Controller controller;
  private net.java.games.input.Component component;
  private boolean inverted;

  public Component(Controller controller, net.java.games.input.Component component) {
    this.controller = controller;
    this.component = component;
    this.inverted = false;
  }

  @Override
  public float getPollData() {
    return this.component.getPollData() * (this.inverted ? -1 : 1);
  }

  @Override
  public boolean isAnalog() {
    return this.component.isAnalog();
  }

  @Override
  public boolean isRelative() {
    return this.component.isRelative();
  }

  @Override
  public Identifier getIdentifier() {
    return this.component.getIdentifier();
  }

  @Override
  public float getDeadZone() {
    return this.component.getDeadZone();
  }

  @Override
  public String getName() {
    return this.component.getName();
  }

  @Override
  public String toString() {
    return this.controller.toString() + ": " + this.component.toString();
  }

  public net.java.games.input.Component getInputComponent() {
    return this.component;
  }
}
