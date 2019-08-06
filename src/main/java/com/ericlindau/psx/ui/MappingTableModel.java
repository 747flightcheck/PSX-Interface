package com.ericlindau.psx.ui;

import javax.swing.table.AbstractTableModel;

public class MappingTableModel extends AbstractTableModel {
  private static final int COMPONENT_COL = 0;
  private static final int VALUE_COL = 1;
  private static final int MAPPING_COL = 2;

  private String[] columnNames;
  private final int rowCount;

  private String[] components;

  MappingTableModel(String[] columnNames, String[] components) {
    this.columnNames = columnNames;
    this.rowCount = components.length;
    this.components = components;
  }

  @Override
  public int getRowCount() {
    return this.rowCount;
  }

  @Override
  public int getColumnCount() {
    return this.columnNames.length;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    switch (columnIndex) {
      case COMPONENT_COL:
        return components[rowIndex];
      case VALUE_COL:
      case MAPPING_COL:

      default:
        return null;
    }
  }

  @Override
  public String getColumnName(int column) {
    return this.columnNames[column];
  }
}
