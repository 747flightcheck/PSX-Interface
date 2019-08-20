package com.ericlindau.psx.core.processing;

import com.ericlindau.psx.config.Configurable;
import com.ericlindau.psx.config.Configured;

import java.util.List;

public class Variable extends Configurable {
  @Configured
  private String name = "Unnamed";
  @Configured
  private String delimiter = "";
  @Configured
  private String psx = "";

  private List<Value> values;
  private int startingIndex; // TODO: Initialization
  private StringBuffer compiled;
  private boolean isActive;

  public Variable(List<Value> values) {
    // TODO: Initialize with specified length
    compiled = new StringBuffer(psx + "=");
    startingIndex = compiled.length();
    this.values = values;
  }

  public boolean isActive() {
    return this.isActive;
  }

  public void refreshActive() {
    boolean isActive = true;
    for (Value value : this.getValues()) {
      isActive &= value.isActive();
    }
    this.isActive = isActive;
  }

  public String getPollData() {
    compiled.delete(startingIndex, compiled.length());

    for (int i = 0; i < values.size(); i++) {
      Value value = values.get(i);

      String data = value.getPollData();
      compiled.append(data);

      // Split compiled values by delimiter
      if (i != values.size() - 1) {
        compiled.append(this.delimiter);
      }
    }

    return compiled.toString();
  }

  public String getName() {
    return name;
  }

  public String getDelimiter() {
    return delimiter;
  }

  public String getPsx() {
    return psx;
  }

  public List<Value> getValues() {
    return values;
  }
}
