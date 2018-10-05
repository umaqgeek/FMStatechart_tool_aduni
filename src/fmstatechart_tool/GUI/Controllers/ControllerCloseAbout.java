/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fmstatechart_tool.GUI.Controllers;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import fmstatechart_tool.Interface.Home;

/**
 *
 * @author user
 */
public class ControllerCloseAbout extends AbstractAction{

    private Home HOME;

    public ControllerCloseAbout(Home HOME) {
        this.HOME = HOME;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        HOME.displayViewAboutWindow(false);
    }
    
}

