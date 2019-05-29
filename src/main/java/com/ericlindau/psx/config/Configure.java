package com.ericlindau.psx.config;

import com.ericlindau.psx.core.polling.AnalogComponent;
import com.ericlindau.psx.core.polling.DigitalComponent;
import com.ericlindau.psx.core.polling.Pollable;
import com.ericlindau.psx.core.processing.AnalogValue;
import com.ericlindau.psx.core.processing.DigitalValue;
import com.ericlindau.psx.core.processing.Value;
import com.ericlindau.psx.core.processing.Variable;
import com.sun.istack.internal.NotNull;
import net.consensys.cava.toml.Toml;
import net.consensys.cava.toml.TomlArray;
import net.consensys.cava.toml.TomlParseResult;
import net.consensys.cava.toml.TomlTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Eric Lindau
 *
 * Constructs Objects with necessary hierarchy for PSX data processing.
 *
 * Documentation and default.toml should be referred to as descriptors
 * for expected configuration structure.
 */
public class Configure {
  // TODO: Test extensively with invalid configurations
  // TODO: Better exception handling across whole class
  // TODO: Consider nested reflection

  /**
   * Returns an array of concrete variable objects with parameters from default.toml.
   *
   * @throws Exception when something goes wrong
   */
  // TODO: Exception handling
  // TODO: Allow alternative configuration files
  public List<Variable> variables() throws Exception {
    URL resource = this.getClass().getClassLoader().getResource("default.toml");
    if (resource == null) {
      throw new Exception();
    }

    BufferedReader br = new BufferedReader(new FileReader(resource.getPath()));
    TomlParseResult toml = Toml.parse(br);

    TomlArray categories = toml.getArrayOrEmpty("category");

    List<Variable> variables = new ArrayList<Variable>(); // Accumulator
    for (Object o : categories.toList()) {                // Populate accumulator
      variables.addAll(processCategory((TomlTable) o));
    }

    return variables;
  }

  @NotNull
  private List<Variable> processCategory(TomlTable category) {
    List<Variable> variables = new ArrayList<Variable>();
    Properties properties = new Properties(); // TODO: Make Properties obsolete

    TomlTable propertiesTable = (TomlTable) category.get("properties");
    boolean digital = false;
    if (category.contains("properties")) { // TODO: Change to check if table is null
      // TODO: Null check
      if (propertiesTable.contains("digital")) {
        digital = propertiesTable.getBoolean("digital");
      }
      properties.setFieldsFromTable(propertiesTable); // TODO: Use ret val or maybe Exception
    }

    // TODO: Necessary to check .contains("variable")?
    TomlArray vars = category.getArrayOrEmpty("variable");
    for (Object o : vars.toList()) {
      variables.add(processVariable((TomlTable) o, propertiesTable, digital));
    }

    return variables;
  }

  private Variable processVariable(TomlTable variableTable, TomlTable propertiesTable, boolean digital) {
    List<Value> values = new ArrayList<Value>();

    TomlArray valuesArray = variableTable.getArrayOrEmpty("value");
    for (Object o : valuesArray.toList()) {
      values.add(processValue((TomlTable) o, propertiesTable, digital));
    }

    Variable variable = new Variable(null, values);
    variable.setFieldsFromTable(variableTable);

    return variable;
  }

  private Value processValue(TomlTable valueTable, TomlTable propertiesTable, boolean digital) {
    List<Pollable> components = new ArrayList<Pollable>();

    TomlArray componentsArray = valueTable.getArrayOrEmpty("component");

    for (Object o : componentsArray.toList()) {
      TomlTable componentTable = (TomlTable) o;

      Pollable component = digital ?
          new DigitalComponent() : new AnalogComponent();
      ((Configurable) component).setFieldsFromTable(componentTable);
      components.add(component);
    }

    Value value = digital ?
        new DigitalValue(components) : new AnalogValue(components);
    value.setFieldsFromTable(propertiesTable);
    value.setFieldsFromTable(valueTable);

    return value;
  }
}
