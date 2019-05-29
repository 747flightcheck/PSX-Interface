package com.ericlindau.psx.core.processing;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class DataStore {

  private Controller[] controllers;

  public DataStore() {
    // TODO: Ignored controllers (e.g. mouse/keyboard)
    this.controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
  }

}
