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

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Eric Lindau
 * <p>
 * Constructs Objects with necessary hierarchy for PSX data processing.
 * <p>
 * Documentation and PSX-Interface.toml should be referred to as descriptors
 * for expected configuration structure.
 */
public class Configure {
  // TODO: Test extensively with invalid configurations
  // TODO: Better exception handling across whole class
  // TODO: Consider nested reflection
  private List<Variable> variables;
  private List<Pollable> pollables;

  // Somehow no support for this in Java 6
  // Adapted from: https://stackoverflow.com/a/32652909
  private static void copy(InputStream source, File dest) throws IOException {
    OutputStream os = null;
    try {
      os = new FileOutputStream(dest);
      byte[] buffer = new byte[1024];
      int length;
      while ((length = source.read(buffer)) > 0) {
        os.write(buffer, 0, length);
      }
    } finally {
      os.close();
    }
  }

  // TODO: Exception handling
  public Configure() throws Exception {
    File config = new File("./PSX-Interface.toml");
    BufferedReader br;
    if (!config.exists() || !config.canRead()) {
      InputStream defaultConfig = this.getClass().getResourceAsStream("/PSX-Interface.toml");
      copy(defaultConfig, config);
      // TODO: Fallback on br below if file is not written
//      br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/PSX-Interface.toml")));
    }

    br = new BufferedReader(new FileReader(config));

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
   * Returns an array of concrete variable objects with parameters from PSX-Interface.toml.
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
      // TODO: Consider consolidating tables in constructor like other Configurables
      ((Configurable) component).setFieldsFromTable(propertiesTable);
      ((Configurable) component).setFieldsFromTable(componentTable);
      components.add(component);
      activeMappings.put(component, componentTable.getString("active"));
    }

    // precedence: Defaults->properties->specified **
    return digital ?
        new DigitalValue(components, activeMappings, null, propertiesTable, valueTable)
        : new AnalogValue(components, propertiesTable, valueTable);
  }
}
