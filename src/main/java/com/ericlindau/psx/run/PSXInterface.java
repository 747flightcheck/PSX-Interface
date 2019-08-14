package com.ericlindau.psx.run;

import com.ericlindau.psx.config.Configure;
import com.ericlindau.psx.core.polling.Pollable;
import com.ericlindau.psx.core.processing.Delegator;

import java.util.List;

public class PSXInterface {
  public static void main(String[] args) throws Exception {
    // TODO: Inject Controller source for testing - no need for real components
    final Delegator data = new Delegator();
    Configure config = new Configure();
    final List<Pollable> pollables = config.pollables();
    pollables.set(0, new Pollable() {
      @Override
      public float getPollData() {
        return 0;
      }

      @Override
      public String toString() {
        return "NONE";
      }
    });

    // TODO: Pull out all Components from Controllers & pass here
    UI ui = new UI(data.getComponents(), (Object[]) pollables.toArray());
  }
}
