package com.ericlindau.psx.run.ui;

import com.ericlindau.psx.core.processing.Mapper;
import net.java.games.input.Component;
import net.java.games.input.Event;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class UI {
  private Map<Component, Mapping> componentToMapping;

  public UI(Collection<com.ericlindau.psx.core.polling.Component> components, Object[] options, Mapper mapper) {
    this.componentToMapping = new HashMap<Component, Mapping>();

    JFrame frame = new JFrame();
    frame.setSize(600, 800);
    frame.setLayout(new GridLayout(0, 1));

    // Populate mappings
    JPanel container = new JPanel();
    container.setLayout(new GridLayout(0, 1));
    MappingListener listener = new MappingListener(mapper);

    for (com.ericlindau.psx.core.polling.Component component : components) {
      Mapping mapping = new Mapping(component, options, listener, new InvertedListener());
      componentToMapping.put(component.getInputComponent(), mapping);
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
