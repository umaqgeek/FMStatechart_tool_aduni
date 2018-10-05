/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fmstatechart_tool.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import fmstatechart_tool.Interface.Home;

/**
 *
 * @author user
 */
public class ViewAbout extends JDialog {

    private JPanel content, close;
    private JLabel icon, authors;
    private JButton closeButton;
    private static final int D_WIDTH = 550, D_HEIGHT = 250;
    private final URL AUTHORS_URL = getClass().getResource("icons/authors.png");
    private static final String TITLE = "About PLEDGE...";
    private static final String AUTHORS = "<html><center><font size=+1><b>" + Home.TITLE + "<b></font><br/><br/> <font size =-1>Christopher Henard<br/><br/> version 1.1 - August 2013<font size =-1></center></html>";


    public ViewAbout(Home HOME) {
        super(HOME, TITLE);
        content = new JPanel(new BorderLayout());
        if (AUTHORS_URL != null) {
            icon = new JLabel(new ImageIcon(AUTHORS_URL));
        }
        authors = new JLabel(AUTHORS, SwingConstants.RIGHT);
        closeButton = new JButton("Close");
        content.setBackground(Color.white);
        content.add(icon, BorderLayout.WEST);
        content.add(authors, BorderLayout.CENTER);
        close = new JPanel();
        close.setBackground(Color.white);
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
