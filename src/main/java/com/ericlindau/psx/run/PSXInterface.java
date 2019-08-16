package com.ericlindau.psx.run;

import com.ericlindau.psx.config.Configure;
import com.ericlindau.psx.core.polling.Pollable;
import com.ericlindau.psx.core.processing.Publisher;
import com.ericlindau.psx.core.sources.EventSource;
import com.ericlindau.psx.core.sources.Poller;
import com.ericlindau.psx.run.ui.UI;
import net.java.games.input.*;

import java.util.ArrayList;
import java.util.Arrays;
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
    Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

    // TODO: Inject Controller source for testing - no need for real components
    final Publisher data = new Publisher();
    Configure config = new Configure();
    final List<Pollable> pollables = config.pollables();
    pollables.add(0, nonePollable);

    // TODO: Pull out all Components from Controllers & pass here
    UI ui = new UI(getComponents(controllers), pollables.toArray());

    // TODO: Pass Event both to Publisher & UI
    EventSource e = new Poller(controllers);
    for (Event event : e) {
      ui.update(event);
    }
  }

  private static Component[] getComponents(Controller[] controllers) {
    List<Component> components = new ArrayList<Component>();
    for (Controller controller : controllers) {
      components.addAll(Arrays.asList(controller.getComponents()));
    }
    return components.toArray(new Component[controllers.length]);
  }
}
