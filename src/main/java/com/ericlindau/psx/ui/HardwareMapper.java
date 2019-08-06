package com.ericlindau.psx.ui;

import com.ericlindau.psx.core.polling.Pollable;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;


/**
 * Interface for user-created mappings from hardware component
 * to logical PSX component.
 */
public class HardwareMapper {
  public HardwareMapper() {
    HashMap<Option, Pollable> optionPollableHashMap = new HashMap<Option, Pollable>();

    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    String[] columns = {"Component", "Value", "Mapping"};
    String[] components = {"Component1", "Component2"};

    MappingTableModel model = new MappingTableModel(columns, components);
    JTable table = new JTable(model);
    JScrollPane scroll = new JScrollPane(table,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    frame.setLayout(new GridLayout(0, 1));
    frame.add(scroll);
    frame.setSize(800, 600);
    frame.pack();
    frame.setVisible(true);
  }
}

