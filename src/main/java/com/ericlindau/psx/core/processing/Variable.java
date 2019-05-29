package com.ericlindau.psx.core.processing;

import com.ericlindau.psx.config.Configurable;
import com.ericlindau.psx.config.Configured;

import java.util.List;

public class Variable extends Configurable {

  @Configured
  private String name;
  @Configured
  private String delimiter;
  @Configured
  private String psx;

  private List<Value> values; // TODO: Remove public
  private int startingIndex; // TODO: Initialization
  private StringBuffer compiled;

  public Variable(String psx, List<Value> values) {
    compiled = new StringBuffer(psx + "=");
    startingIndex = compiled.length();
    this.values = values;
    this.psx = psx;
  }

  public String getPollData() {
    compiled.delete(startingIndex, compiled.length());

    int index = startingIndex;
    for (Value value : values) {
      String data = value.getPollData();
      compiled.insert(index, data + delimiter);
      index += data.length() + delimiter.length();
    }

    if (index != startingIndex) { // Remove trailing delimiter
      compiled.delete(index, index + delimiter.length());
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
