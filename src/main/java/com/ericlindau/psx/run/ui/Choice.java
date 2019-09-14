package com.ericlindau.psx.run.ui;

import javax.swing.*;

class Choice extends JComboBox {
  private final Mapping mapping;

  Choice(Mapping mapping, Object[] options) {
    super(options);
    this.mapping = mapping;
  }

  Mapping getMapping() {
    return mapping;
  }
}
