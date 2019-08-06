package com.ericlindau.psx.core.polling;

import com.ericlindau.psx.config.Configurable;
import net.java.games.input.Component;

abstract class AbstractPollable extends Configurable implements Pollable {

  private Component component;

  public Component getComponent() {
    return component;
  }

  public void setComponent(Component component) {
    if (this.component != null) {
      this.component = component;
    }
    // TODO: Else - Exception / eventually error for UI to present
  }

}
