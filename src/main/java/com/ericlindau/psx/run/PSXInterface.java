package com.ericlindau.psx.run;

import com.ericlindau.psx.core.processing.Delegator;
import com.ericlindau.psx.ui.HardwareMapper;

public class PSXInterface {
  public static void main(String[] args) {
    Delegator data = new Delegator();
    // TODO: Populate panel with possible data mappings
    // TODO: Add listeners that modify data mappings according to selections
    HardwareMapper panel = new HardwareMapper();
  }
}
