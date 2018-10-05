/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fmstatechart_tool.GUI;

import javax.swing.table.AbstractTableModel;
import fmstatechart_tool.Interface.Model;


/**
 *
 * @author user
 */
public class FeatureAdapter extends AbstractTableModel {

    private Model model;
    public static final int COLUMNS_COUNT = 3;

    public FeatureAdapter(Model model) {
        this.model = model;
    }

    @Override
    public int getRowCount() {
        return model.getFeaturesList().size();
    }

    @Override
    public int getColumnCount() {
        return ViewFeatures.COLUMN_TITLES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return rowIndex + 1;
        } else if (columnIndex == 1) {
            return model.getFeaturesList().get(rowIndex);
        } else {
           return model.getFeatureType(model.getFeaturesList().get(rowIndex));
        }
    }

    @Override
    public String getColumnName(int col) {
        return ViewFeatures.COLUMN_TITLES[col];
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public void update(){
        fireTableDataChanged() ;
    }
}
