package com.ericlindau.psx.run;

import com.ericlindau.psx.config.Configure;
import com.ericlindau.psx.core.polling.Pollable;
import com.ericlindau.psx.core.processing.Mapper;
import com.ericlindau.psx.core.processing.Publisher;
import com.ericlindau.psx.core.events.EventSource;
import com.ericlindau.psx.core.events.Poller;
import com.ericlindau.psx.core.processing.Variable;
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
    final Configure config = new Configure();
    final Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

    final List<Pollable> pollables = config.pollables();

    final List<Variable> variables = config.variables();
    final Mapper mapper = new Mapper(variables);

    // TODO: Inject Controller source for testing - no need for real components
    final Publisher data = new Publisher(variables, mapper);
    final UI ui = new UI(getComponents(controllers), pollables.toArray(), mapper);

    EventSource e = new Poller(controllers);
    for (Event event : e) {
      ui.update(event);
      data.update(event);
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
