package com.ericlindau.psx.run;

import net.java.games.input.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

class UI {
  private final Map<Object, Component> pollableToComponent;
  private final Map<Object, Mapping> pollableToMapping;
  private final Map<Mapping, Component> mappingToComponent;

  UI(Component[] components, Object[] options) {
    // TODO: Move Pollable -> Component mapping out (how to ensure 1-1?)
    this.pollableToComponent = new HashMap<Object, Component>();
    this.pollableToMapping = new HashMap<Object, Mapping>();
    this.mappingToComponent = new HashMap<Mapping, Component>();

    ItemListener genericListener = new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        Object target = e.getItem();
        if (e.getStateChange() == ItemEvent.SELECTED) {
          Mapping mapping = pollableToMapping.get(target);
          if (mapping != null) {
            mapping.resetChoice();
          }

          Mapping source = ((Choice) e.getItemSelectable()).getMapping();
          Component comp = mappingToComponent.get(source);
          pollableToMapping.put(target, source);
          pollableToComponent.put(target, comp);
        }
      }
    };

    JFrame frame = new JFrame();
    frame.setSize(600, 800);
    frame.setLayout(new GridLayout(0, 1));

    // Populate mappings
    JPanel container = new JPanel();
    container.setLayout(new GridLayout(0, 1));
    for (Component comp : components) {
      container.add(new Mapping(comp, options, genericListener));
    }

    JScrollPane scroll = new JScrollPane(container,
        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scroll.getVerticalScrollBar().setUnitIncrement(32);

    frame.add(scroll);
    frame.pack();
    frame.setVisible(true);
  }
}
