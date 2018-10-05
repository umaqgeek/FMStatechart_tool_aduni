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
import fmstatechart_tool.Interface.Home;
import fmstatechart_tool.Interface.Model;

/**
 *
 * @author user
 */
public class ViewEditConstraints extends JDialog {

    private JPanel content, close;
    private JLabel icon, authors;
    private JButton closeButton;
    private static final int D_WIDTH = 550, D_HEIGHT = 250;

    private static final String TITLE = "Insert a constraint";


    public ViewEditConstraints(Model MODEL, Home HOME) {
        super(HOME, TITLE);
        content = new JPanel(new BorderLayout());

        closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        close = new JPanel();
        close.add(closeButton);
        content.add(close, BorderLayout.SOUTH);
        add(content);
        setSize(new Dimension(D_WIDTH, D_HEIGHT));
        setResizable(false);
        setLocationRelativeTo(getParent());
    }

    public JButton getCloseButton() {
        return closeButton;
    }
}

