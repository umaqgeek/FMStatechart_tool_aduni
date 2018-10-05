/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fmstatechart_tool.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import fmstatechart_tool.Interface.Model;
import java.awt.Color;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import fmstatechart_tool.Interface.Model;
import fmstatechart_tool.GUI.AdapterConstraints;

/**
 *
 * @author user
 */
public class ViewConstraints extends JPanel implements Observer {
    
    private static final String TITLE = "CNF Constraints";
    private static final int D_WIDTH = 300;
    private static final Color GRAY = new Color(220, 220, 220);
    private Model model;
    private AdapterConstraints modelAdapter;
    private JList constraintsList;
    private JScrollPane scrollPane;
    
    public ViewConstraints(final Model model) {
        super(new BorderLayout());
        this.model = model;
        model.addObserver(this);
        modelAdapter = new AdapterConstraints(model);
        constraintsList = new JList(modelAdapter);
        scrollPane = new JScrollPane(constraintsList);
        scrollPane.setPreferredSize(new Dimension(300,10));
        constraintsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        constraintsList.addListSelectionListener(new ListSelectionListener() {
            
            @Override
            public void valueChanged(ListSelectionEvent arg) {
                if (!arg.getValueIsAdjusting()) {
                   model.setCurrentConstraint(constraintsList.getSelectedIndex());
                }
            }
        });


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
}
