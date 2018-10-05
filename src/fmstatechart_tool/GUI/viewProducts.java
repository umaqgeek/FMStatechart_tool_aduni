/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fmstatechart_tool.GUI;

import fmstatechart_tool.Interface.Home;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import fmstatechart_tool.Interface.Model;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
/**
 *
 * @author user
 */
public class viewProducts extends JPanel implements Observer {
 private static final String TITLE = "Details List";
    public static final String[] COLUMN_TITLES = {"Item", "States", "Transition"};
    private Model model;
    private AdapterProducts productAdapter;
    private JTable UMLTable;
    private JScrollPane scrollPane;

    public viewProducts(Model model) {
        super(new BorderLayout());
        this.model = model;
        model.addObserver(this);
        productAdapter = new AdapterProducts(model);
        // System.out.print(productAdapter);
        UMLTable = new JTable(productAdapter);
        UMLTable.setAutoCreateRowSorter(true);
        TableCellRenderer headerRenderer = UMLTable.getTableHeader().getDefaultRenderer();
        ((DefaultTableCellRenderer) headerRenderer).setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        UMLTable.setOpaque(true);
        Cell cell = new Cell();
        for (int i = 0; i < UMLTable.getColumnModel().getColumnCount(); i++) {
            UMLTable.getColumnModel().getColumn(i).setCellRenderer(cell);
        }
        scrollPane = new JScrollPane(UMLTable);
        setBorder(BorderFactory.createTitledBorder(TITLE));
        add(scrollPane, BorderLayout.CENTER);
    }
    
    

    @Override
    public void update(Observable o, final Object arg) {
        Runnable code = new Runnable() {

            @Override
            public void run() {
                if (arg != null) {
                    productAdapter.update();
                }
            }
        };
        if (SwingUtilities.isEventDispatchThread()) {
            code.run();
        } else {
            SwingUtilities.invokeLater(code);
        }
    }
    
    private class Cell extends DefaultTableCellRenderer {

        /**
         * Construit une cellule.
         */
        public Cell() {
            setOpaque(true);
            setHorizontalAlignment(JLabel.CENTER);
            setBackground(Color.white);
        }

        /**
         * Retourne le composant d'affichage de la cellule.
         * @param table la jtable concernée.
         * @param value la valeur dans la cellule.
         * @param isSelected un booléen indiquant si la cellule est sélectionnée.
         * @param hasFocus un booléen indiquant si la cellule a le focus.
         * @param row la ligne concernée.
         * @param column la colonne concernée.
         * @return le composant d'affichage de la cellule.
         */
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (!isSelected) {

                if (row % 2 == 0) {
                    setBackground(Home.BLUE_COLOR);
                } else {
                    setBackground(Color.white);
                }
            }
            return this;
        }
    }
    
}
