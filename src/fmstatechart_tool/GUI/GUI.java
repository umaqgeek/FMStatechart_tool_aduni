/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fmstatechart_tool.GUI;

import javax.swing.SwingUtilities;
import fmstatechart_tool.Interface.Home;
/**
 *
 * @author user
 */
public class GUI {
     public GUI() {
        Runnable code = new Runnable() {

            @Override
            public void run() {
                new Home();
            }
        };

        if (SwingUtilities.isEventDispatchThread()) {
            code.run();
        } else {
            SwingUtilities.invokeLater(code);
        }
    }
    
}
