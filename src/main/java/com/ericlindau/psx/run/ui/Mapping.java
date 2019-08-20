package com.ericlindau.psx.run.ui;

import net.java.games.input.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemListener;

class Mapping extends JPanel {
  private static final int FIELDS = 4;

  private final Object component;
  private final JLabel value;
  private final JComboBox choice;

  // TODO: New interface instead of Object component & Object[] options (Pollables)?
  Mapping(Object component, Object[] options, ItemListener comboListener) {
    this.setLayout(new GridLayout(1, FIELDS));

    this.component = component;
    this.value = new JLabel("0");
    JCheckBox inverted = new JCheckBox("Inverted");
    this.choice = new Choice(this, options);

    this.choice.addItemListener(comboListener);

    this.add(new JLabel(this.component.toString()));
    this.add(this.value);
    this.add(inverted);
    this.add(this.choice);
  }

  void resetChoice() {
    this.choice.setSelectedIndex(0);
  }

  void update() {
    this.setValue(String.valueOf(((Component) this.component).getPollData()));
  }

  void setValue(String text) {
    this.value.setText(text);
  }

  public Object getComponent() {
    return component;
  }
}
