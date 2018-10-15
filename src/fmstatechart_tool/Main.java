/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fmstatechart_tool;

import java.util.logging.Level;
import java.util.logging.Logger;
import fmstatechart_tool.GUI.GUI;
import fmstatechart_tool.Interface.Model;
/**
 *
 * @author user
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                new GUI();
            } else {
                CommandLineParser parser = new CommandLineParser(args, "FMSTATECHART_TOOL");
                try {
                    
                    parser.parseArgs();
                    if (parser.getCommandName().equals(CommandLineParser.GENERATE)) {
                        Model model = new Model();
                        if (parser.getCommandGenerate().dimacs) {
                            model.loadFeatureModel(parser.getCommandGenerate().fmFile);
                          } 
                        
                        model.setNbProductsToGenerate(parser.getCommandGenerate().nbProds);
                        model.setGenerationTimeMSAllowed(parser.getCommandGenerate().timeAllowed);
                       // model.generateProducts();
                        model.saveProducts(parser.getCommandGenerate().outputFile);
                    }
                } catch (Exception e) {
                    parser.printUsage();
                }
            }



        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
