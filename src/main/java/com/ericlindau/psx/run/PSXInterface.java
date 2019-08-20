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

import java.util.*;

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
    final Map<Component, Controller> componentToController = generateComponents();
    final List<Pollable> pollables = config.pollables();  // TODO: Convert to []
    final List<Variable> variables = config.variables();
    final Mapper mapper = new Mapper(variables);

    // TODO: Inject Controller source for testing - no need for real components
    final Publisher data = new Publisher(variables, mapper);
    final UI ui = new UI(componentToController, pollables.toArray(), mapper);

    // TODO: Simplify
    final Controller[] controllers = componentToController.values().toArray(new Controller[componentToController.values().size()]);
    EventSource e = new Poller(controllers);
    for (Event event : e) {
      ui.update(event);
      data.update(event);
    }
  }

  private static Map<Component, Controller> generateComponents() {
    Map<Component, Controller> components = new HashMap<Component, Controller>();
    final Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
    for (Controller controller : controllers) {
      Component[] subComponents = controller.getComponents();
      for (Component component : subComponents) {
        components.put(component, controller);
      }
    }
    return components;
  }
}
