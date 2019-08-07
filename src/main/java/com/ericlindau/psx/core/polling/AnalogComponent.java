package com.ericlindau.psx.core.polling;

import com.ericlindau.psx.config.Configured;

public class AnalogComponent extends AbstractPollable {

  @Configured
  private String name; // TODO: name in AbstractPollable - Configurable traverses tree

  @Configured
  private boolean inverted;

  @Override
  public float getPollData() {
    return this.getComponent().getPollData();
  }

  public boolean isInverted() {
    return this.inverted;
  }

  public String getName() {
    return name;
  }

}
