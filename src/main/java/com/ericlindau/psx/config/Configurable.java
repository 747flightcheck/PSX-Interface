package com.ericlindau.psx.config;

import net.consensys.cava.toml.TomlTable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author Eric Lindau
 * <p>
 * Configuration utilities for Reflection-based automated field setting from TomlTables.
 * <p>
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

  /**
   * Propagates up Class tree, settings @Configured fields while class is subclass of Configurable.
   *
   * @param table TomlTable used to fill values.
   * @return true if all fields were set from the table.
   */
  public boolean setFieldsFromTable(TomlTable table) {
    Class toTraverse = this.getClass();
    while (Configurable.class.isAssignableFrom(toTraverse)) {
      if (!setFieldsForClass(toTraverse, table)) {
        return false;
      }
      toTraverse = toTraverse.getSuperclass();
    }
    return true;
  }

  private boolean setFieldsForClass(Class c, TomlTable table) {
    for (Field field : c.getDeclaredFields()) {
      if (field.getAnnotation(Configured.class) != null &&
          !this.setFieldFromTable(field, table)) {
        System.out.println("Field set: " + field.toString());
        // TODO: Better (not sysout) logging & return false on failure (e.g. no value in table to populate)
      } else {
        return false;
      }
    }

    return true;
  }

}
