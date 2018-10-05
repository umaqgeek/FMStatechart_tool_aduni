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
public class AdapterProducts extends AbstractTableModel {

    private Model model;
    public static final int COLUMNS_COUNT = 3;

    public AdapterProducts(Model model) {
        this.model = model;
    }
    
    public int getRowCount() {
        
        return model.getStateChartList().size();
       
    }
  
    public int getColumnCount() {
        return viewProducts.COLUMN_TITLES.length;
    }

    
    public Object getValueAt(int rowIndex, int columnIndex) {
        
       
        if (columnIndex == 0) 
        {
            return rowIndex + 1;
        }  
       
        else if (columnIndex == 1) 
        {
              return model.getStateChartList().get(rowIndex);
        }
         
        else 
        {
          return 0;
        }
    }
    

    @Override
    public String getColumnName(int col) {
        return viewProducts.COLUMN_TITLES[col];
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public void update(){
        fireTableDataChanged() ;
    }
   
}
