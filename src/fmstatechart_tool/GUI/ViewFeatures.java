/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fmstatechart_tool.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import fmstatechart_tool.Interface.Model;
import fmstatechart_tool.GUI.FeatureAdapter;
import fmstatechart_tool.Interface.Home;

/**
 *
 * @author user
 */
public class ViewFeatures extends JPanel implements Observer {
    
    private static final String TITLE = "Features";
    public static final String[] COLUMN_TITLES = {"Number", "Name", "Type"};
    private Model model;
    private FeatureAdapter modelAdapter;
    private JTable featuresTable;
    private JScrollPane scrollPane;

    public ViewFeatures(Model model) {
        super(new BorderLayout());
        this.model = model;
        model.addObserver(this);
        modelAdapter = new FeatureAdapter(model);
        featuresTable = new JTable(modelAdapter);
        featuresTable.setAutoCreateRowSorter(true);
        TableCellRenderer headerRenderer = featuresTable.getTableHeader().getDefaultRenderer();
        ((DefaultTableCellRenderer) headerRenderer).setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        featuresTable.setOpaque(true);
        Cell cell = new Cell();
        for (int i = 0; i < featuresTable.getColumnModel().getColumnCount(); i++) {
            featuresTable.getColumnModel().getColumn(i).setCellRenderer(cell);
        }
        scrollPane = new JScrollPane(featuresTable);
        setBorder(BorderFactory.createTitledBorder(TITLE));
        add(scrollPane, BorderLayout.CENTER);
    }
    
    

    @Override
    public void update(Observable o, final Object arg) {
        Runnable code = new Runnable() {

            @Override
            public void run() {
                if (arg != null) {
                    modelAdapter.update();
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

