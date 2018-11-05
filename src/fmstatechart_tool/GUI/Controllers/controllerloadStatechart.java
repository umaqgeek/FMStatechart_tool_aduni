/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fmstatechart_tool.GUI.Controllers;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import fmstatechart_tool.Interface.Home;
import fmstatechart_tool.Interface.Model;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author user
 */
public class controllerloadStatechart extends AbstractAction{

    private Model model;
    private Home HOME;

    public controllerloadStatechart(Model model, Home HOME) {
        this.model = model;
        this.HOME = HOME;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        File selected = HOME.displayFileChooser(Home.FileType.STATECHART);
        if (selected != null) {
            
            final String productsPath = selected.getPath();
            final Thread t = new Thread(new Runnable() {
             
                public void run() {
                    
                       try {
                        model.loadstatechart(productsPath);
                        
                    } catch (Exception ex) {
                        ex.printStackTrace();
                       model.setRunning(false);
                        HOME.displayError("Error while loading products", "Incorrect products file");
                    }
//                    if (productsPath.endsWith(".xml")) {
//				model.loadstatechart (productsPath);
//                                //String newPath = productsPath;
//                                //model.loadstatechart (newPath);
//			}
//                    else
//                    {
//                          model.setRunning(false);
//                        HOME.displayError("Error while loading products", "Incorrect products file");
//                    }
                }
            });
            t.start();
        }
    }
    
}


