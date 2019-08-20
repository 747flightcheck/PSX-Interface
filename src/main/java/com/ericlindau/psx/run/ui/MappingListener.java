package com.ericlindau.psx.run.ui;

import com.ericlindau.psx.core.polling.Pollable;
import com.ericlindau.psx.core.processing.Mapper;
import com.ericlindau.psx.run.PSXInterface;
import net.java.games.input.Component;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

class MappingListener implements ItemListener {
  private final Mapper mapper;
  private final Map<Object, Mapping> optionToMapping;

  MappingListener(Mapper mapper) {
    this.mapper = mapper;
    this.optionToMapping = new HashMap<Object, Mapping>();
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
      if (target != PSXInterface.nonePollable) {
        this.optionToMapping.put(target, source);
      }

      this.mapper.set((Pollable) target, (Component) source.getComponent());
    } else {  // ItemEvent.DESELECTED
      if (target != PSXInterface.nonePollable) {
        this.optionToMapping.put(target, null);
      }
    }
  }
}
