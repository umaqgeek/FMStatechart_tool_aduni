/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fmstatechart_tool.GUI.Controllers;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import fmstatechart_tool.Interface.Model;
import fmstatechart_tool.Interface.Home;
/**
 *
 * @author user
 */
public class ControllerLoadFeatureModel extends AbstractAction {

    private Model model;
    private Home view;

    public ControllerLoadFeatureModel(Model model, Home view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        File selected = view.displayFileChooser(Home.FileType.FEATURE_MODEL);
        if (selected != null) {
            final String featureModelPath = selected.getPath();
            final Thread t = new Thread(new Runnable() {

                public void run() {
                    try {
                        model.loadFeatureModel(featureModelPath, featureModelPath.toLowerCase().endsWith("xml") ? Model.FeatureModelFormat.SPLOT: Model.FeatureModelFormat.SPLOT);
                     
                    } catch (Exception ex) {
                        Logger.getLogger(ControllerLoadFeatureModel.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });
            t.start();
        }
    }
}
