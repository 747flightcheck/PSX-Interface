package com.ericlindau.psx.run.ui;

import net.java.games.input.Component;

import javax.swing.*;
import java.awt.*;

public class UI {
  public UI(Component[] components, Object[] options) {
    // TODO: Move Pollable -> Component mapping out (how to ensure 1-1?)
    JFrame frame = new JFrame();
    frame.setSize(600, 800);
    frame.setLayout(new GridLayout(0, 1));

    // Populate mappings
    JPanel container = new JPanel();
    container.setLayout(new GridLayout(0, 1));
    MappingListener listener = new MappingListener();
    for (Component comp : components) {
      container.add(new Mapping(comp, options, listener));
    }

    JScrollPane scroll = new JScrollPane(container,
        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scroll.getVerticalScrollBar().setUnitIncrement(16);

    frame.add(scroll);
    frame.pack();
    frame.setVisible(true);
  }
}
