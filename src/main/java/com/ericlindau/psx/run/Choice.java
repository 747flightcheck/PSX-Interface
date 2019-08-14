package com.ericlindau.psx.run;

import javax.swing.*;

class Choice extends JComboBox {
  private final Mapping mapping;

  Choice(Mapping mapping, Object[] options) {
    super(options);
    this.mapping = mapping;
  }

  public Mapping getMapping() {
    return mapping;
  }
}
