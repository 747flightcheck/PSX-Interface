package com.ericlindau.psx.core.processing;

import com.ericlindau.psx.config.Configurable;
import com.ericlindau.psx.core.polling.Pollable;

import java.util.List;

public abstract class Value extends Configurable {

  public List<Pollable> components;

  Value(List<Pollable> components) {
    this.components = components;
  }

  public abstract String getPollData();

}
