package com.ericlindau.psx.core.polling;

import com.ericlindau.psx.config.Configured;

public class AnalogComponent extends AbstractPollable {
  @Configured
  private boolean inverted;

  @Override
  public float getPollData() {
    return this.getComponent().getPollData();
  }

  public boolean isInverted() {
    return this.inverted;
  }
}
