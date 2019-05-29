package com.ericlindau.psx.core.polling;

import com.ericlindau.psx.config.Configured;

public class DigitalComponent extends AbstractPollable {

  @Configured
  private String name;

  @Override
  public float getPollData() {
    return this.component.getPollData();
  }

  public String getName() {
    return name;
  }

}
