/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fmstatechart_tool.GUI.Controllers;

import fmstatechart_tool.Interface.Home;
import fmstatechart_tool.Interface.Model;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;

/**
 *
 * @author umarmukhtar
 */
public class ControllerMatchSet extends AbstractAction {

    private Model model;
    private Home view;

    public ControllerMatchSet(Model model, Home view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        final Thread t = new Thread(new Runnable() {
            public void run() {

                
            }
        });

        t.start();
    }
}
