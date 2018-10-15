/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fmstatechart_tool.GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import fmstatechart_tool.Interface.Model;

/**
 *
 * @author user
 */
public class viewFeatureModelInformation extends JPanel implements Observer {

    private Model model;
    private static final String TITLE = "Feature Model Information";
    public static final String NAME = "Name:";
    public static final String FORMAT = "Format:";
    public static final String CONSTRAINTS = "Number of CNF constraints:";
    public static final String FEATURES = "Number of features:";
    public static final String CORE = "Number of core features:";
    public static final String DEAD = "Number of dead features:";
    private JLabel nameLabel, name;
    private JLabel formatLabel, format;
    private JLabel constraintsLabel, constraints;
    private JLabel featuresLabel, features;
    private JLabel coreLabel, core;
    private JLabel deadLabel, dead;

    public viewFeatureModelInformation(Model model) {
        super(new GridBagLayout());
        this.model = model;
        model.addObserver(this);
        setBorder(BorderFactory.createTitledBorder(TITLE));
        nameLabel = new JLabel(NAME);
        name = new JLabel("-", SwingConstants.LEFT);
        formatLabel = new JLabel(FORMAT);
        format = new JLabel("-", SwingConstants.LEFT);
        constraintsLabel = new JLabel(CONSTRAINTS);
        constraints = new JLabel("-", SwingConstants.LEFT);
        featuresLabel = new JLabel(FEATURES);
        features = new JLabel("-", SwingConstants.LEFT);
        coreLabel = new JLabel(CORE);
        core = new JLabel("-", SwingConstants.LEFT);
        deadLabel = new JLabel(DEAD);
        dead = new JLabel("-", SwingConstants.LEFT);
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
        add(constraintsLabel, c);

        c.gridx = 3;
        c.anchor = GridBagConstraints.BASELINE_LEADING;
        add(constraints, c);

        c.gridx = 4;
        c.anchor = GridBagConstraints.BASELINE_TRAILING;
        add(coreLabel, c);

        c.gridx = 5;
        c.anchor = GridBagConstraints.BASELINE_LEADING;
        add(core, c);

        i = new Insets(10, 15, 0, 0);
        c.gridx = 0;
        c.gridy = 1;
        c.insets = i;
        c.anchor = GridBagConstraints.BASELINE_TRAILING;
        add(formatLabel, c);

        i = new Insets(10, 15, 10, 0);
        c.gridx = 1;
        c.insets = i;
        c.anchor = GridBagConstraints.BASELINE_LEADING;
        add(format, c);

        c.gridx = 2;
        c.anchor = GridBagConstraints.BASELINE_TRAILING;
        add(featuresLabel, c);

        c.gridx = 3;
        c.anchor = GridBagConstraints.BASELINE_LEADING;
        add(features, c);

        c.gridx = 4;
        c.anchor = GridBagConstraints.BASELINE_TRAILING;
        add(deadLabel, c);

        c.gridx = 5;
        c.anchor = GridBagConstraints.BASELINE_LEADING;
        add(dead, c);
    }

    @Override
    public void update(Observable o, final Object arg) {
        Runnable code = new Runnable() {

            @Override
            public void run() {
                if ("xml".equals(model.getFeatureModelFormat())) {
                    name.setText(model.getFeatureModelName());
                    format.setText(model.getFeatureModelFormat());
                   // System.out.println(model.getFeatureModelFormat());
                    constraints.setText(""+model.getFeatureModelConstraintsString().size());
                    features.setText(""+model.getFeaturesList().size());
                    core.setText("" + model.getCoreFeatures().size());
                    dead.setText(""+model.getDeadFeatures().size());
                    
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

