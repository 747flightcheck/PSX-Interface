package com.ericlindau.psx.config;

import net.consensys.cava.toml.TomlTable;

import java.lang.reflect.Field;

/**
 * @author Eric Lindau
 *
 * Configuration utilities for Reflection-based automated field setting from TomlTables.
 *
 * Any class extending this may have @Configured fields automatically populated.
 */
public class Configurable {

  // TODO: Throw explicit Exception for logging (incl. TOML info)
  private boolean setFieldFromTable(Field field, TomlTable table) {
    String key = field.getName();

    if (!table.contains(key)) {
      return false;
    }

    try {
      field.setAccessible(true);
      field.set(this, table.get(key));
      field.setAccessible(false);
    } catch (IllegalAccessException exception) {
      return false;
    }

    return true;
  }

  // TODO: Use return values
  public boolean setFieldsFromTable(TomlTable table) {
    for (Field field : this.getClass().getDeclaredFields()) {
      if (field.getAnnotation(Configured.class) != null &&
          !this.setFieldFromTable(field, table)) {
        continue;
        // TODO: Logging
      }
    }

    return true;
  }

}
