package fmstatechart_tool.Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import fmstatechart_tool.GUI.Controllers.ControllerQuit;
import fmstatechart_tool.GUI.Controllers.ControllerLoadFeatureModel;
import fmstatechart_tool.GUI.Controllers.ControllerCloseAbout;
import fmstatechart_tool.GUI.Controllers.controllerDisplayDocumentation;
import fmstatechart_tool.GUI.Controllers.controllerloadStatechart;
import fmstatechart_tool.GUI.Controllers.controllerCloseEditConstraints;
import fmstatechart_tool.GUI.Controllers.ControllerDisplayEditConstraints;
import fmstatechart_tool.GUI.ViewMenuBar;
import fmstatechart_tool.GUI.ViewFeatures;
import fmstatechart_tool.GUI.ViewToolBar;
import fmstatechart_tool.GUI.viewFeatureModelInformation;
import fmstatechart_tool.GUI.ViewAbout;
import fmstatechart_tool.GUI.ViewDocumentation;
import fmstatechart_tool.GUI.viewProducts;
import fmstatechart_tool.GUI.ViewProductInformation;
import fmstatechart_tool.GUI.viewStatusBar;
import fmstatechart_tool.GUI.ViewConstraints;
import fmstatechart_tool.GUI.ViewEditConstraints;
/**
 *
 * @author user
 */
public class Home extends JFrame implements Observer {

     public static final Color BLUE_COLOR = new Color(232, 239, 247);
    public static final int D_WIDTH = 1350, D_HEIGHT = 750;
    public static final String TITLE = "State Chart Test Generation Tool";
    private static final String QUIT_MESSAGE = "Quit fmstatechart_tool will interrupt any running task. Do you want to continue ?";
    private static final String QUIT_TITLE = "Quit fmstatechart_tool";
    private static final String FILE_CHOOSER_FEATURE_MODEL_TITLE = "Load a Feature Model";
     private static final String FILE_CHOOSER_STATECHART_MODEL_TITLE = "Load a Statechart Model";
    private static final String FILE_CHOOSER_PRODUCTS_TITLE = "Load Products";
    private static final String FILE_SAVER_PRODUCTS_TITLE = "Save Products";
 //   private static final String COVERAGE_TITLE = "Pairwise coverage";
    private final FileNameExtensionFilter featureModelFileFilter = new FileNameExtensionFilter("SPLOT Feature Models (.xml)", "xml");
    private final FileNameExtensionFilter StatechartFileFilter = new FileNameExtensionFilter("EA STATECHART files (.xml)", "xml");
    private static final String TAB_FEATURE_MODEL = "Feature Model";
    private final URL BACKGROUND_URL = getClass().getResource("icons/logo.png");

    public enum FileType {

        FEATURE_MODEL, STATECHART
    };
    private JFileChooser featureModelChooser, productsSaver, statechartModelChooser;
    // Model
    private Model model;
    // Controllers
    private ControllerQuit controllerQuit;
    private ControllerLoadFeatureModel controllerLoadFeatureModel;
    private controllerloadStatechart controllerloadStatechart;
//    private ControllerSaveProducts controllerSaveProducts;
//    private ControllerGenerationTechnique controllerGenerationTechnique;
//    private ControllerPrioritizationTechnique controllerPrioritizationTechnique;
//    private ControllerDisplayAbout controllerDisplayAbout;
    private ControllerCloseAbout controllerCloseAbout;
//    private ControllerViewConfigurationGeneration controllerViewConfigurationGeneration;
//    private ControllerGenerateProducts controllerGenerateProducts;
//    private ControllerPrioritizeProducts controllerPrioritizeProducts;
//    private ControllerCoverage controllerCoverage;
    private controllerDisplayDocumentation controllerDisplayDocumentation;
    private ControllerDisplayEditConstraints controllerDisplayEditConstraints;
    private controllerCloseEditConstraints controllerCloseEditConstraints;
//    private ControllerRemoveConstraint controllerRemoveConstraint;
//    private controllerLoadProductsFM controllerLoadProductsFM;
    // Views
    private ViewMenuBar viewMenuBar;
    private ViewConstraints viewConstraints;
    private ViewFeatures viewFeatures;
    private ViewToolBar viewToolBar;
    private viewStatusBar viewStatusBar;
    private ViewAbout ViewAbout;
    private viewFeatureModelInformation viewFeatureModelInformation;
//    private ViewConfigurationGeneration viewConfigurationGeneration;
    private  viewProducts viewProducts;
    private  ViewProductInformation viewProductInformation;
    private JPanel content, background;
    private ViewDocumentation viewDocumentation;
    private ViewEditConstraints viewEditConstraints;

    public Home() {
        super(TITLE);
        model = new Model();
        model.addObserver(this);
        new Thread(new Runnable(){

            @Override
            public void run() {
              viewDocumentation = ViewDocumentation.getInstance();
              viewDocumentation.setLocationRelativeTo(Home.this);
            }
            
        }).start();
        ViewAbout = new ViewAbout(this);
        viewMenuBar = new ViewMenuBar(model);
        viewConstraints = new ViewConstraints(model);
        viewFeatures = new ViewFeatures(model);
        viewStatusBar = new viewStatusBar(model);
        viewFeatureModelInformation = new viewFeatureModelInformation(model);
//        viewConfigurationGeneration = new ViewConfigurationGeneration(model, this);
        viewProducts = new viewProducts(model);
        viewProductInformation = new ViewProductInformation(model);
        viewToolBar = new ViewToolBar(model);
        viewEditConstraints = new ViewEditConstraints(model, this);
        controllerQuit = new ControllerQuit(model, this);
        controllerLoadFeatureModel = new ControllerLoadFeatureModel(model, this);
        controllerloadStatechart = new controllerloadStatechart(model, this);
//        controllerLoadProductsFM = new controllerLoadProductsFM(model, this);
//        controllerDisplayAbout = new ControllerDisplayAbout(this);
        controllerCloseAbout = new ControllerCloseAbout(this);
//        controllerViewConfigurationGeneration = new ControllerViewConfigurationGeneration(this);
//        controllerGenerateProducts = new ControllerGenerateProducts(model, this, viewConfigurationGeneration);
//        controllerCoverage = new ControllerCoverage(model, this);
//        controllerSaveProducts = new ControllerSaveProducts(model, this);
//        controllerPrioritizeProducts = new ControllerPrioritizeProducts(model, this);
        controllerDisplayDocumentation = new controllerDisplayDocumentation(this);
        controllerDisplayEditConstraints = new ControllerDisplayEditConstraints(this);
        controllerCloseEditConstraints =  new controllerCloseEditConstraints(this);
//        controllerRemoveConstraint = new ControllerRemoveConstraint(model);
        viewEditConstraints.getCloseButton().addActionListener(controllerCloseEditConstraints );
        ViewAbout.getCloseButton().addActionListener(controllerCloseAbout);
          viewMenuBar.getQuit().addActionListener(controllerQuit);
          viewMenuBar.getLoadFeatureModel().addActionListener(controllerLoadFeatureModel);
          viewMenuBar.getLoadProducts().addActionListener(controllerloadStatechart);
//        viewMenuBar.getLoadProductsFM().addActionListener(controllerLoadProductsFM);
//        viewMenuBar.getAbout().addActionListener(controllerDisplayAbout);
//        viewMenuBar.getGenerate().addActionListener(controllerViewConfigurationGeneration);
//        viewMenuBar.getSaveProducts().addActionListener(controllerSaveProducts);
//        viewMenuBar.getPrioritize().addActionListener(controllerPrioritizeProducts);
        viewMenuBar.getDoc().addActionListener(controllerDisplayDocumentation);
//        viewMenuBar.getCoverage().addActionListener(controllerCoverage);
//        viewConfigurationGeneration.getOk().addActionListener(controllerGenerateProducts);
//        controllerGenerationTechnique = new ControllerGenerationTechnique(model, viewMenuBar);
//        for (JRadioButtonMenuItem button : viewMenuBar.getGenerationTechniqueButtons()) {
//            button.addActionListener(controllerGenerationTechnique);
//        }
//        controllerPrioritizationTechnique = new ControllerPrioritizationTechnique(model, viewMenuBar);
//        for (JRadioButtonMenuItem button : viewMenuBar.getPrioritizationTechniqueButtons()) {
//            button.addActionListener(controllerPrioritizationTechnique);
//        }
        viewToolBar.getQuit().addActionListener(controllerQuit);
        viewToolBar.getLoadFM().addActionListener(controllerLoadFeatureModel);
        viewToolBar.getLoadStatechart().addActionListener(controllerloadStatechart);
//        viewToolBar.getGenerate().addActionListener(controllerViewConfigurationGeneration);
//        viewToolBar.getSaveProducts().addActionListener(controllerSaveProducts);
//        viewToolBar.getPrioritize().addActionListener(controllerPrioritizeProducts);
//        viewToolBar.getAddConstraint().addActionListener(controllerDisplayEditConstraints);
//        viewToolBar.getRemoveConstraint().addActionListener(controllerRemoveConstraint);
        setJMenuBar(viewMenuBar);
        featureModelChooser = new JFileChooser();
        featureModelChooser.setMultiSelectionEnabled(false);
        featureModelChooser.setAcceptAllFileFilterUsed(false);
        featureModelChooser.addChoosableFileFilter(featureModelFileFilter);
        featureModelChooser.setDialogTitle(FILE_CHOOSER_FEATURE_MODEL_TITLE);
        
        statechartModelChooser = new JFileChooser();
        statechartModelChooser.setMultiSelectionEnabled(false);
        statechartModelChooser.setAcceptAllFileFilterUsed(false);
        statechartModelChooser.addChoosableFileFilter(StatechartFileFilter);
        statechartModelChooser.setDialogTitle(FILE_CHOOSER_STATECHART_MODEL_TITLE);
        

        Home.this.add(viewStatusBar, BorderLayout.SOUTH);
        content = new JPanel(new BorderLayout());

        viewProducts.setVisible(false);
        add(viewToolBar, BorderLayout.PAGE_START);
        background = new JPanel(new GridLayout(1, 1));
        background.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(172, 168, 153)));
        background.setBackground(Color.white);
 
        add(background, BorderLayout.CENTER);

        setSize(D_WIDTH, D_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(getParent());
        setVisible(true);


    }
    
//    public void displayCoverage(String cov){
//        JOptionPane.showMessageDialog(this, cov, COVERAGE_TITLE, JOptionPane.INFORMATION_MESSAGE);
//    }

    public void displayDocumentation() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                viewDocumentation.setVisible(true);
            }
        });
    }

    public boolean getQuitConfirmation() {
        return JOptionPane.showConfirmDialog(this, QUIT_MESSAGE, QUIT_TITLE, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public File displayFileChooser(FileType fileType) {

        switch (fileType) {
            case FEATURE_MODEL:
                if (featureModelChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    return featureModelChooser.getSelectedFile();
                }
                break;
            case STATECHART:
                if (statechartModelChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    return statechartModelChooser.getSelectedFile();
                }
                break;
        }


        return null;
    }

    public File displayFileSaver() {


        if (productsSaver.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            return productsSaver.getSelectedFile();
        }


        return null;
    }

    @Override
    public void update(Observable o, final Object arg) {
        Runnable code;
         code = new Runnable() {
             
             @Override
             public void run() {
                 if (arg != null) {
                     remove(background);
                     add(content, BorderLayout.CENTER);
                     viewStatusBar.addSeparator();
                     if ("".equals(model.getFeatureModelFormat())) {
                         viewFeatureModelInformation.setVisible(false);
                         viewConstraints.setVisible(false);
                         viewFeatures.setVisible(false);
                         content.add(viewProducts, BorderLayout.CENTER);
                     } else {
                         content.add(viewConstraints, BorderLayout.EAST);
                         content.add(viewFeatures, BorderLayout.CENTER);
                         content.add(viewFeatureModelInformation, BorderLayout.NORTH);
                         //content.add(viewProductInformation, BorderLayout.NORTH);
                         //content.add(viewProducts, BorderLayout.CENTER);
                         viewFeatureModelInformation.setVisible(true);
                         viewConstraints.setVisible(true);
                         viewFeatures.setVisible(true);
                         //viewProducts.setVisible(true);
                         //viewProductInformation.setVisible(true);
                     }
                     
                     validate();
                     SwingUtilities.updateComponentTreeUI(Home.this);
                     
                 }
                 
                 if ("xml".equals(model.getStateChartFormat()))
                 {
                     viewStatusBar.addSeparator();
                     content.add(viewProductInformation, BorderLayout.NORTH);
                     content.add(viewProducts, BorderLayout.CENTER);
                     viewProducts.setVisible(true);
                     viewProductInformation.setVisible(true);
                 }
                 else
                 {
                     displayError("Error while loading products", "Incorrect products file");
                 }
                 
             }
         };
        if (SwingUtilities.isEventDispatchThread()) {
            code.run();
        } else {
            SwingUtilities.invokeLater(code);
        }
    }

    public void displayViewAboutWindow(final boolean display) {
        Runnable code = new Runnable() {

            @Override
            public void run() {
                ViewAbout.setVisible(display);
            }
        };
        if (SwingUtilities.isEventDispatchThread()) {
            code.run();
        } else {
            SwingUtilities.invokeLater(code);
        }
    }
    
    public void displayViewEditConstraints(final boolean display) {
        Runnable code = new Runnable() {

            @Override
            public void run() {
                viewEditConstraints.setVisible(display);
            }
        };
        if (SwingUtilities.isEventDispatchThread()) {
            code.run();
        } else {
            SwingUtilities.invokeLater(code);
        }
    }

    public void displayViewConfigurationGeneration(final boolean display) {
        Runnable code = new Runnable() {

            @Override
            public void run() {
//                viewConfigurationGeneration.setVisible(display);
            }
        };
        if (SwingUtilities.isEventDispatchThread()) {
            code.run();
        } else {
            SwingUtilities.invokeLater(code);
        }
    }

    public void displayError(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
        public void displayViewStatechart(final boolean display) {
        Runnable code = new Runnable() {

            @Override
            public void run() {
                viewProducts.setVisible(display);
            }
        };
        if (SwingUtilities.isEventDispatchThread()) {
            code.run();
        } else {
            SwingUtilities.invokeLater(code);
        }
    }
}
