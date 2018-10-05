/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fmstatechart_tool.GUI.Controllers;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import fmstatechart_tool.Interface.Model;
import fmstatechart_tool.Interface.Home;


/**
 *
 * @author user
 */
public class ControllerQuit extends AbstractAction {

    private Model model;
    private Home view;

    public ControllerQuit(Model model, Home view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (view.getQuitConfirmation()) {
            model.quit();
        }
    }
}
