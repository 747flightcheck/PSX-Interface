package com.ericlindau.psx.core.processing;

import com.ericlindau.psx.core.polling.Pollable;
import net.java.games.input.Component;

import java.util.HashMap;
import java.util.Map;

public class Mapper {
  private Map<Pollable, Component> pollableToComponent;

  public Mapper() {
    this.pollableToComponent = new HashMap<Pollable, Component>();
  }

  void set(Pollable pollable, Component component) {
    Component current = this.pollableToComponent.get(pollable);
    if (current != null) {
      pollable.setComponent(component);
    }
    this.pollableToComponent.put(pollable, component);
  }
}
