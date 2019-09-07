package com.ericlindau.psx.config;

import com.ericlindau.psx.core.polling.GenericPollable;
import com.ericlindau.psx.core.polling.Pollable;
import com.ericlindau.psx.core.processing.AnalogValue;
import com.ericlindau.psx.core.processing.DigitalValue;
import com.ericlindau.psx.core.processing.Value;
import com.ericlindau.psx.core.processing.Variable;
import com.ericlindau.psx.run.PSXInterface;
import net.consensys.cava.toml.Toml;
import net.consensys.cava.toml.TomlArray;
import net.consensys.cava.toml.TomlParseResult;
import net.consensys.cava.toml.TomlTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Eric Lindau
 * <p>
 * Constructs Objects with necessary hierarchy for PSX data processing.
 * <p>
 * Documentation and default.toml should be referred to as descriptors
 * for expected configuration structure.
 */
public class Configure {
  // TODO: Test extensively with invalid configurations
  // TODO: Better exception handling across whole class
  // TODO: Consider nested reflection
  private List<Variable> variables;
  private List<Pollable> pollables;

  // TODO: Exception handling
  // TODO: Allow alternative configuration files
  public Configure() throws Exception {
    URL resource = this.getClass().getClassLoader().getResource("default.toml");
    if (resource == null) {
      throw new Exception();
    }

    BufferedReader br = new BufferedReader(new FileReader(resource.getPath()));
    TomlParseResult toml = Toml.parse(br);

    TomlArray categories = toml.getArrayOrEmpty("category");

    this.variables = new ArrayList<Variable>(); // Accumulator
    for (Object o : categories.toList()) {                // Populate accumulator
      variables.addAll(processCategory((TomlTable) o));
    }

    this.pollables = new ArrayList<Pollable>();
    for (Variable variable : this.variables()) {
      for (Value value : variable.getValues()) {
        this.pollables.addAll(value.components);
      }
    }
    this.pollables.add(0, PSXInterface.nonePollable);
  }

  /**
   * Returns an array of concrete variable objects with parameters from default.toml.
   */
  public List<Variable> variables() {
    return this.variables;
  }

  public List<Pollable> pollables() {
    return this.pollables;
  }

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
    return new Variable(values, variableTable);
  }

  private Value processValue(TomlTable valueTable, TomlTable propertiesTable, boolean digital) {
    List<Pollable> components = new ArrayList<Pollable>();
    TomlArray componentsArray = valueTable.getArrayOrEmpty("component");

    Map<Pollable, String> activeMappings = new HashMap<Pollable, String>();
    for (Object o : componentsArray.toList()) {
      TomlTable componentTable = (TomlTable) o;

      Pollable component = new GenericPollable();
      ((Configurable) component).setFieldsFromTable(propertiesTable);
      ((Configurable) component).setFieldsFromTable(componentTable);
      components.add(component);
      activeMappings.put(component, componentTable.getString("active"));
    }

    Value value = digital ?
        new DigitalValue(components, activeMappings, null, propertiesTable, valueTable) : new AnalogValue(components, propertiesTable, valueTable);
    // TODO: Then call it again here
    // ... with what?
    // precedence: Defaults->properties->specified
    //    value.setFieldsFromTable(valueTable);

    return value;
  }
}
