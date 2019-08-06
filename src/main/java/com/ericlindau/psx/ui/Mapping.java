package com.ericlindau.psx.ui;

import javax.swing.*;

class Mapping extends JPanel {
  @SuppressWarnings("unchecked")
  Mapping() {
    super();
    JComboBox options = new JComboBox();
    options.addItem("None");
//    this.setLayout();
    this.add(new JLabel("Name"));
    this.add(new JComboBox());
  }
}
