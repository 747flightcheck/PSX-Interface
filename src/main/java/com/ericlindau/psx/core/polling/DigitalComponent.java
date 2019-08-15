package com.ericlindau.psx.core.polling;

public class DigitalComponent extends AbstractPollable {
  @Override
  public float getPollData() {
    return this.getComponent().getPollData();
  }
}
