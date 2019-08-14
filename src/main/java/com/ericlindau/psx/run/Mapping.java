package com.ericlindau.psx.run;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemListener;

class Mapping extends JPanel {
  private final JComboBox choice;

  // TODO: New interface instead of Object component & Object[] options (Pollables)?
  Mapping(Object component, Object[] options, ItemListener comboListener) {
    this.setLayout(new GridLayout(1, 2));

    this.choice = new Choice(this, options);
    this.choice.addItemListener(comboListener);

    this.add(new JLabel(component.toString()));
    this.add(this.choice);
  }

  void resetChoice() {
    this.choice.setSelectedIndex(0);
  }
}
