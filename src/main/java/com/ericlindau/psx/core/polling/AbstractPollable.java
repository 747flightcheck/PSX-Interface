package com.ericlindau.psx.core.polling;

import com.ericlindau.psx.config.Configurable;
import net.java.games.input.Component;

abstract class AbstractPollable extends Configurable implements Pollable {

  Component component;

  public Component getComponent() {
    return component;
  }

}
