package com.ericlindau.psx.core.polling;

import com.ericlindau.psx.config.Configured;

public class AnalogComponent extends AbstractPollable {
  @Configured
  private boolean inverted;

  @Override
  public float getPollData() {
    try {
      return this.getComponent().getPollData();
    } catch (NullPointerException n) {
      System.out.println("null component");
      return 0;
    }
  }

  public boolean isInverted() {
    return this.inverted;
  }
}
