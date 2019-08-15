package com.ericlindau.psx.core.polling;

import net.java.games.input.Component;

public interface Pollable {

  float getPollData();

  void setComponent(Component component);

}
