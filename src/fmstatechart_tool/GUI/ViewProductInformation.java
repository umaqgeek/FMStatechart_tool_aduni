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
import fmstatechart_tool.GUI.AdapterProducts;
import fmstatechart_tool.Interface.Model;
import fmstatechart_tool.Interface.Home;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JList;
import javax.swing.SwingConstants;
/**
 *
 * @author user
 */
public class ViewProductInformation extends JPanel implements Observer {
 
    private static final String TITLE = "Statechart Information Loaded";
    public static final String NAME = "Name:";
    public static final String FORMAT = "Format:";
    public static final String STATES = "Total number of states :";
    public static final String TRANSITIONS = "Total number of transition :";
    //private static final int D_WIDTH = 800;
//    public static final String[] COLUMN_TITLES = {"State List", "Transition List", "Type"};
    private static final Color GRAY = new Color(220, 220, 220);
    private Model model;
    private AdapterProducts modelAdapter;
    private JTable statechartTable;
    private JList productsList;
    private JScrollPane scrollPane;
    private JLabel nameLabel, name;
    private JLabel formatLabel, format;
    private JLabel TotalStates, states;
    private JLabel TotalTransition, transitions;


    public ViewProductInformation (Model model) {
         super(new GridBagLayout());
        this.model = model;
        model.addObserver(this);
        setBorder(BorderFactory.createTitledBorder(TITLE));
        nameLabel   = new JLabel(NAME); 
        name = new JLabel("-", SwingConstants.LEFT);
        formatLabel   = new JLabel(FORMAT); 
        format = new JLabel("-", SwingConstants.LEFT);
        TotalStates   = new JLabel(STATES); 
        states = new JLabel("-", SwingConstants.LEFT);
        TotalTransition   = new JLabel(TRANSITIONS); 
        transitions = new JLabel("-", SwingConstants.LEFT);
        GridBagConstraints c = new GridBagConstraints();
        
                Insets i = new Insets(20, 15, 0, 0);
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = i;
        c.anchor = GridBagConstraints.BASELINE_TRAILING;
        add(nameLabel, c);

        i = new Insets(20, 15, 0, 0);
        c.gridx = 1;
        c.insets = i;
        c.anchor = GridBagConstraints.BASELINE_LEADING;
        add(name, c);

        c.gridx = 2;
        c.anchor = GridBagConstraints.BASELINE_TRAILING;
        add(formatLabel, c);

        c.gridx = 3;
        c.anchor = GridBagConstraints.BASELINE_LEADING;
        add(format, c);

        c.gridx = 4;
        c.anchor = GridBagConstraints.BASELINE_TRAILING;
        add(TotalStates, c);

        c.gridx = 5;
        c.anchor = GridBagConstraints.BASELINE_LEADING;
        add(states, c);

        i = new Insets(10, 15, 0, 0);
        c.gridx = 0;
        c.gridy = 1;
        c.insets = i;
        c.anchor = GridBagConstraints.BASELINE_TRAILING;
        add(TotalTransition, c);

        i = new Insets(10, 15, 10, 0);
        c.gridx = 1;
        c.insets = i;
        c.anchor = GridBagConstraints.BASELINE_LEADING;
        add(transitions, c);
        
        
//        modelAdapter = new AdapterProducts(model);
//        productsList = new JList(modelAdapter);
//        scrollPane = new JScrollPane(productsList);
//        //scrollPane.setPreferredSize(new Dimension(D_WIDTH, getHeight()));
//        setBorder(BorderFactory.createTitledBorder(TITLE));
//        add(scrollPane);

                
//        super(new BorderLayout());
 //       this.model = model;
//        model.addObserver(this);
//        modelAdapter = new AdapterProducts(model);
//        statechartTable = new JTable(modelAdapter);
//        statechartTable.setAutoCreateRowSorter(true);
//        TableCellRenderer headerRenderer = statechartTable.getTableHeader().getDefaultRenderer();
//        ((DefaultTableCellRenderer) headerRenderer).setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
//        statechartTable.setOpaque(true);
//       Cell cell = new Cell();
//        for (int i = 0; i < statechartTable.getColumnModel().getColumnCount(); i++) {
//            statechartTable.getColumnModel().getColumn(i).setCellRenderer(cell);
//        }
//        scrollPane = new JScrollPane(statechartTable);
//        setBorder(BorderFactory.createTitledBorder(TITLE));
//        add(scrollPane, BorderLayout.CENTER);

    }

    @Override
    public void update(Observable o, final Object arg) {
        Runnable code = new Runnable() {

            @Override
            public void run() {
                if ("xml".equals(model.getStateChartFormat())) {
                   // modelAdapter.update();
                   name.setText(model.getStateChartName());
                    format.setText(model.getStateChartFormat());
                   // states.setText(""+model.getStateChartList().size());
                    transitions.setText(""+model.getTransitionList().size());
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
