package com.ericlindau.psx.run;

import com.ericlindau.psx.core.polling.Pollable;
import net.java.games.input.Component;

import java.util.Map;

public class HardwareMapper {
  private Map<Component, Pollable> componentPollableMap;
  private Map<Pollable, Component> pollableComponentMap;


  public HardwareMapper() {

  }
}
