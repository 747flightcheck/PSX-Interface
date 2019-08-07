package com.ericlindau.psx.run;

import com.ericlindau.psx.config.Configure;
import com.ericlindau.psx.core.polling.Pollable;
import com.ericlindau.psx.core.processing.Delegator;

import java.util.List;

public class PSXInterface {
  public static void main(String[] args) throws Exception {
    Delegator data = new Delegator();
    Configure config = new Configure();
    List<Pollable> pollables = config.pollables();
  }
}
