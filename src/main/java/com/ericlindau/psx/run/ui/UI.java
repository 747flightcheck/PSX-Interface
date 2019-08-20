package com.ericlindau.psx.run.ui;

import com.ericlindau.psx.core.processing.Mapper;
import net.java.games.input.Component;
import net.java.games.input.Event;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class UI {
  private Map<Component, Mapping> componentToMapping;

  public UI(Component[] components, Object[] options, Mapper mapper) {
    this.componentToMapping = new HashMap<Component, Mapping>();

    // TODO: Move Pollable -> Component mapping out (how to ensure 1-1?)
    JFrame frame = new JFrame();
    frame.setSize(600, 800);
    frame.setLayout(new GridLayout(0, 1));

    // Populate mappings
    JPanel container = new JPanel();
    container.setLayout(new GridLayout(0, 1));
    MappingListener listener = new MappingListener(mapper);
    for (Component component : components) {
      Mapping mapping = new Mapping(component, options, listener);
      componentToMapping.put(component, mapping);
      container.add(mapping);
    }

    JScrollPane scroll = new JScrollPane(container,
        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scroll.getVerticalScrollBar().setUnitIncrement(16);

    frame.add(scroll);
    frame.pack();
    frame.setVisible(true);
  }

  public void update(Event event) {
    componentToMapping.get(event.getComponent()).update();
  }
}
