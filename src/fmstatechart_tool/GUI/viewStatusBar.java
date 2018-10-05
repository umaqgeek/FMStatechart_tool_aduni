/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fmstatechart_tool.GUI;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import fmstatechart_tool.Interface.Model;

/**
 *
 * @author user
 */
public class viewStatusBar extends JPanel implements Observer {

    private static final String INIT_MESSAGE = "No file loaded";
    private static final String END_MESSAGE = "Completed";
    private Model model;
    private JLabel currentAction, globalAction;
    private JProgressBar progressBar;
    private JPanel action, status;
    private JSeparator separator;

    public viewStatusBar(Model model) {
        super(new BorderLayout());
        this.model = model;
        model.addObserver(this);
        globalAction = new JLabel(INIT_MESSAGE);
        currentAction = new JLabel();
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        action = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(2, 5, 2, 5);
        action.add(currentAction, c);
        c.gridx = 1;
        action.add(progressBar, c);
        status = new JPanel(new GridBagLayout());
        c.gridx = c.gridy = 0;
        c.insets = new Insets(5, 5, 4, 5);
        status.add(globalAction, c);
        separator = new JSeparator();
        action.setVisible(false);
        
        add(action, BorderLayout.EAST);
        add(status, BorderLayout.WEST);

    }
    
    public void addSeparator(){
        add(separator, BorderLayout.NORTH);
    }
    
    public void removeSeparator(){
        remove(separator);
    }

    @Override
    public void update(Observable o, Object arg) {
        Runnable code = new Runnable() {

            @Override
            public void run() {

                if (model.isRunning()) {
                    action.setVisible(true);
                    globalAction.setText(model.getGlobalAction());
                    currentAction.setText(model.getCurrentAction());
                    if (!model.isIndeterminate()) {
                        progressBar.setIndeterminate(false);
                        progressBar.setStringPainted(true);
                        int progress = model.getProgress();
                        progressBar.setValue(progress);
                    }
                    else
                    {
                        progressBar.setIndeterminate(true);
                        progressBar.setStringPainted(false);
                    }


                } else {
                    action.setVisible(false);
                    globalAction.setText(END_MESSAGE);
                    progressBar.setIndeterminate(true);
                    progressBar.setStringPainted(false);
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

