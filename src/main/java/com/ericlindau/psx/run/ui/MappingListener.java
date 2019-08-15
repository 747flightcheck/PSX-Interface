package com.ericlindau.psx.run.ui;

import com.ericlindau.psx.run.PSXInterface;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

class MappingListener implements ItemListener {
  private final Map<Object, Mapping> optionToMapping;
//  private final Map<Mapping, Component> mappingToComponent;

  MappingListener() {
    this.optionToMapping = new HashMap<Object, Mapping>();
//    this.mappingToComponent = new HashMap<Mapping, Component>();
  }

  @Override
  public void itemStateChanged(ItemEvent e) {
    Object target = e.getItem();
    Mapping currentMapping = this.optionToMapping.get(target);
    Mapping source = ((Choice) e.getItemSelectable()).getMapping();
    if (e.getStateChange() == ItemEvent.SELECTED) {
      if (currentMapping != null) {
        currentMapping.resetChoice();
      }
//      Component comp = this.mappingToComponent.get(source);
      if (target != PSXInterface.nonePollable) {
        this.optionToMapping.put(target, source);
      }
      // TODO: Abstract this into non-UI Mapper that's passed in
//      pollableToComponent.put(target, comp);
    } else {  // ItemEvent.DESELECTED
      if (target != PSXInterface.nonePollable) {
        this.optionToMapping.put(target, null);
      }
    }
  }
}
