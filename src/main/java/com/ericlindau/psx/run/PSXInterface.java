package com.ericlindau.psx.run;

import com.ericlindau.psx.config.Configure;
import com.ericlindau.psx.core.polling.Pollable;
import com.ericlindau.psx.core.processing.Delegator;
import com.ericlindau.psx.run.ui.UI;
import net.java.games.input.Component;

import java.util.List;

public class PSXInterface {
  public static final Pollable nonePollable = new Pollable() {
    @Override
    public float getPollData() {
      return 0;
    }

    @Override
    public String toString() {
      return "NONE";
    }

    @Override
    public void setComponent(Component component) {

    }
  };

  public static void main(String[] args) throws Exception {
    // TODO: Inject Controller source for testing - no need for real components
    final Delegator data = new Delegator();
    Configure config = new Configure();
    final List<Pollable> pollables = config.pollables();
    pollables.add(0, nonePollable);

    // TODO: Pull out all Components from Controllers & pass here
    UI ui = new UI(data.getComponents(), (Object[]) pollables.toArray());
  }
}
