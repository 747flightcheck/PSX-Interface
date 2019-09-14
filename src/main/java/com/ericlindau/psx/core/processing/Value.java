package com.ericlindau.psx.core.processing;

import com.ericlindau.psx.config.Configurable;
import com.ericlindau.psx.core.polling.Pollable;
import net.consensys.cava.toml.TomlTable;

import java.util.List;

public abstract class Value extends Configurable {
  public List<Pollable> components;

  Value(List<Pollable> components, TomlTable... tables) {
    super(tables);
    this.components = components;
  }

  public abstract String getPollData();

  /** Synchronously determines if Value is active (i.e. has all components set) */
  boolean isActive() {
    boolean isActive = true;
    for (Pollable pollable : components) {
      isActive &= pollable.hasComponent();
    }
    return isActive;
  }
}
