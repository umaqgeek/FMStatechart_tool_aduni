package fmstatechart_tool.Interface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.minisat.core.IOrder;
import org.sat4j.minisat.core.Solver;
import org.sat4j.minisat.orders.RandomLiteralSelectionStrategy;
import org.sat4j.minisat.orders.RandomWalkDecorator;
import org.sat4j.minisat.orders.VarOrderHeap;
import org.sat4j.reader.DimacsReader;
import org.sat4j.specs.IConstr;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.IVecInt;
import org.sat4j.specs.TimeoutException;
import org.sat4j.tools.ModelIterator;
//import pledge.core.techniques.generation.EvolutionaryAlgorithm1Plus1;
//import pledge.core.techniques.generation.GenerationTechnique;
//import pledge.core.techniques.prioritization.PrioritizationTechnique;
//import pledge.core.techniques.prioritization.SimilarityGreedy;
//import pledge.core.techniques.prioritization.SimilarityNearOptimal;
import splar.core.constraints.CNFClause;
import splar.core.constraints.CNFFormula;
import splar.core.fm.FeatureModel;
import splar.core.fm.XMLFeatureModel;
import splar.plugins.reasoners.sat.sat4j.FMReasoningWithSAT;
import splar.plugins.reasoners.sat.sat4j.ReasoningWithSAT;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Attr;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

public class Model extends Observable {

    private static final int SAT_TIMEOUT = 1000;
    private static final int ITERATOR_TIMEOUT = 150000;
    private static final String solverName = "MiniSAT";
    public static final String OR = "   OR   ";
    public static final String NOT = "! ";
    private static final IOrder order = new RandomWalkDecorator(new VarOrderHeap(new RandomLiteralSelectionStrategy()), 1);
    private static final String GLOBAL_ACTION_LOAD_FM = "Loading the Feature Model";
    private static final String GLOBAL_ACTION_LOAD_PRODUCTS = "Loading Products";
//    private static final String GLOBAL_ACTION_GENERATING_PRODUCTS = "Generating products";
//    private static final String GLOBAL_ACTION_PRIORITIZING_PRODUCTS = "Prioritizing products";
//    private static final String GLOBAL_ACTION_COVERAGE = "Computing the coverage";
    private static final String CURRENT_ACTION_LOAD_CONSTRAINTS = "Loading the constraints...";
    private static final String CURRENT_ACTION_EXTRACT_FEATURES = "Extracting the features...";
    private static final String CURRENT_ACTION_EXTRACT_CONSTRAINTS = "Extracting the constraints...";
    private static final String CURRENT_ACTION_FINDING_CORE_DEAD_FEATURES = "Finding core and dead features...";
//    private static final String CURRENT_ACTION_MODEL_PAIRS = "Computing the valid pairs of the model...";
//    private static final String CURRENT_ACTION_PRODUCT_PAIRS = "Computing the pairs covered by the products...";
    private static final String CORE_FEATURE = "Core";
    private static final String DEAD_FEATURE = "Dead";
    private static final String STATE_LIST = "State";
    private static final String TRANSITION_LIST = "Transition";
    private static final String FREE_FEATURE = "Free";
    String line;
    protected Vector data;
    protected Vector columnNames;

    public static enum FeatureModelFormat {

        xml
    };

    public static enum stateChartFormat {

        xml
    };
    private Solver solver;
    private ISolver solverIterator;
    private List<Integer> featuresIntList;
    private List<Integer> StateIntList;
    static List<String> featuresList;
    static List<String> StateChartList;
    private Map<String, Integer> namesToFeaturesInt;
    private List<String> featureModelConstraints;
    private List<String> featureModelConstraintsString;
    static List<String> TransitionList;
    private FeatureModelFormat featureModelFormat;
    private stateChartFormat statechartFormat;
//    private Format Format;
    private String featureModelName;
    private String statechartModelName;
    private String FileExtension;
    private String FileExtension1;
    private boolean running, indeterminate;
    private String globalAction, currentAction;
    private List<String> coreFeatures, deadFeatures, state, transition;
    private int progress;
    private List<Product> products;
//    private List<GenerationTechnique> generationTechniques;
//    private GenerationTechnique generationTechnique;
//    private List<PrioritizationTechnique> prioritizationTechniques;
//    private PrioritizationTechnique prioritizationTechnique;
    private long generationTimeMSAllowed = 60000;
    private int nbProductsToGenerate = 10;
    private String fmPath;
    private String statechartPath;
    private int currentConstraint = -1;

    /**
     * Creates the model of the application.
     */
    public Model() {
        solver = null;
        solverIterator = null;
        featuresIntList = new ArrayList<Integer>();
        StateIntList = new ArrayList<Integer>();
        featuresList = new ArrayList<String>();
        StateChartList = new ArrayList<String>();
        namesToFeaturesInt = new HashMap<String, Integer>();
        featureModelConstraints = new ArrayList<String>();
        featureModelConstraintsString = new ArrayList<String>();
        TransitionList = new ArrayList<String>();
        coreFeatures = new ArrayList<String>();
        deadFeatures = new ArrayList<String>();
        state = new ArrayList<String>();
        transition = new ArrayList<String>();
        products = null;
        running = false;
        indeterminate = true;
        progress = 0;
//        generationTechniques = new ArrayList<GenerationTechnique>();
//        generationTechniques.add(new EvolutionaryAlgorithm1Plus1());
//        generationTechnique = generationTechniques.get(0);
//        prioritizationTechniques = new ArrayList<PrioritizationTechnique>();
//        prioritizationTechniques.add(new SimilarityGreedy());
//        prioritizationTechniques.add(new SimilarityNearOptimal());
//        prioritizationTechnique = prioritizationTechniques.get(0);
    }

    /**
     * Returns the format of the currently loaded feature model.
     *
     * @return the format of the current feature model.
     */
    /**
     * Returns the name of the currently loaded feature model.
     *
     * @return the name of the currently loaded feature model.
     */
    public String getFeatureModelName() {
        return featureModelName;
    }

    /**
     * Returns an indices list of the features.
     *
     * @return a list containing the indices of the feature model's features.
     */
    public List<Integer> getFeaturesIntList() {
        return featuresIntList;
    }

    /**
     * returns the features' list of the feature model.
     *
     * @return a list of features of the feature model.
     */
    public static List<String> getFeaturesList() {
        return featuresList;
    }

    /**
     * Returns a mapping between each feature and its corresponding index.
     *
     * @return a map containing the index of each feature.
     */
    public Map<String, Integer> getNamesToFeaturesInt() {
        return namesToFeaturesInt;
    }

    /**
     * Returns the constraints of the feature model.
     *
     * @return a list containing the constraints of the feature model.
     */
    public List<String> getFeatureModelConstraints() {
        return featureModelConstraints;
    }

    /**
     * Returns the constraints of the feature model.
     *
     * @return a list containing a String represent of the constraints of the
     * feature model.
     */
    public List<String> getFeatureModelConstraintsString() {
        return featureModelConstraintsString;
    }

    /**
     * Returns the SAT solver.
     *
     * @return the SAT solver asosciated to this model.
     */
    public Solver getSolver() {
        return solver;
    }

    /**
     * Checks if the application is running.
     *
     * @return true if the program is running.
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Returns the list of products currently loaded into the application.
     *
     * @return the generated or priotized products.
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Returns the amount of time allowed for generating products.
     *
     * @return the amount of time in seconds allowed for generating products.
     */
    public long getGenerationTimeMSAllowed() {
        return generationTimeMSAllowed;
    }

    /**
     * Specifies the amount of time allowed for generating products.
     *
     * @param generationTimeMSAllowed the amount of time in seconds allowed for
     * generating products.
     */
    public void setGenerationTimeMSAllowed(long generationTimeMSAllowed) {
        this.generationTimeMSAllowed = generationTimeMSAllowed;
        setChanged();
        notifyObservers();
    }

    /**
     * Returns the number of products to generate.
     *
     * @return an integer representing the number of products to generate.
     */
    public int getNbProductsToGenerate() {
        return nbProductsToGenerate;
    }

    /**
     * Specifies the number of products to generate.
     *
     * @param nbProductsToGenerate the number of products to generate.
     */
    public void setNbProductsToGenerate(int nbProductsToGenerate) {
        this.nbProductsToGenerate = nbProductsToGenerate;
        setChanged();
        notifyObservers();
    }

    /**
     * Specifies whether the application is running or not.
     *
     * @param running a boolean indicating whether the application is running or
     * not.
     */
    public void setRunning(boolean running) {
        this.running = running;
        if (!running) {
            indeterminate = true;
        }
        progress = 0;
        setChanged();
        notifyObservers();
    }

    /**
     * Specifies whether the duration of current action performed by the tool is
     * indeterminate or not.
     *
     * @return true if the duration of current action performed by the tool is
     * indeterminate.
     */
    public boolean isIndeterminate() {
        return indeterminate;
    }

    /**
     * Return the progress of the current action performed by the tool
     * (percentage)
     *
     * @return an integer representing the percentage of the current action
     * performed.
     */
    public int getProgress() {
        return progress;
    }

    /**
     * Specifies the progress of the current action performed by the tool
     * (percentage)
     *
     * @param progress an integer representing the percentage of the current
     * action performed.
     */
    public void setProgress(int progress) {
        this.progress = progress;
        setChanged();
        notifyObservers();
    }

    /**
     * Specifies wether the duration of current action performed by the tool is
     * indeterminate or not.
     *
     * @param indeterminate a boolean specifying wether the duration of current
     * action performed by the tool is indeterminate or not.
     */
    public void setIndeterminate(boolean indeterminate) {
        this.indeterminate = indeterminate;
        setChanged();
        notifyObservers();
    }

    /**
     * Returns the solver iterator that is used to generate valid products.
     *
     * @return the solver iterator that is used to generate valid products.
     */
    public ISolver getSolverIterator() {
        return solverIterator;
    }

    /**
     * Returns the current action performed by the tool.
     *
     * @return a String representing the current action performed by the tool.
     */
    public String getCurrentAction() {
        return currentAction;
    }

    /**
     * Specifies the current action performed by the tool.
     *
     * @param currentAction a String representing the action which is currently
     * performed by the tool.
     */
    public void setCurrentAction(String currentAction) {
        this.currentAction = currentAction;
        setChanged();
        notifyObservers();
    }

    /**
     * Returns the current global action performed by the tool.
     *
     * @return a String representing the current global action performed by the
     * tool.
     */
    public String getGlobalAction() {
        return globalAction;
    }

    /**
     * Specifies the current global action performed by the tool.
     *
     * @param globalAction a String representing the global action which is
     * currently performed by the tool.
     */
    public void setGlobalAction(String globalAction) {
        this.globalAction = globalAction;
        setChanged();
        notifyObservers();
    }

    private void clean() {
        featuresIntList.clear();
        featuresList.clear();
        StateChartList.clear();
        TransitionList.clear();
        StateIntList.clear();
        namesToFeaturesInt.clear();
        featureModelConstraints.clear();
        featureModelConstraintsString.clear();
        coreFeatures.clear();
        deadFeatures.clear();
        state.clear();
        transition.clear();
        setChanged();
        notifyObservers();
    }

    /**
     * Returns the core features of the feature model.
     *
     * @return the list of mandatory features of the feature model.
     */
    public List<String> getCoreFeatures() {
        return coreFeatures;
    }

    /**
     * Returns the dead features of the feature model.
     *
     * @return the list of dead features of the feature model.
     */
    public List<String> getDeadFeatures() {
        return deadFeatures;
    }

    public List<String> getStates() {
        return state;
    }

    public List<String> getTransitions() {
        return transition;
    }

    /**
     * Returns the specified generation technique.
     *
     * @return the technique used by the tool to generate products.
     */
//    public GenerationTechnique getGenerationTechnique() {
//        return generationTechnique;
//    }

    /**
     * Specifies the generation technique used by the tool.
     *
     * @param name the name of the generation technique that has to be used to
     * generate products.
     */
//    public void SetGenerationTechniqueByName(String name) {
//        for (GenerationTechnique gt : generationTechniques) {
//            if (gt.getName().equals(name)) {
//                generationTechnique = gt;
//                break;
//            }
//        }
//
//    }
    /**
     * Returns the generation techniques available.
     *
     * @return a list containing the available generation techniques.
     */
//    public List<GenerationTechnique> getGenerationTechniques() {
//        return generationTechniques;
//    }
//
//    /**
//     * Returns the specified prioritization technique.
//     * @return the technique used by the tool to prioritize products.
//     */
//    public PrioritizationTechnique getPrioritizationTechnique() {
//        return prioritizationTechnique;
//    }
//
//    /**
//     * Specifies the prioritization technique used by the tool.
//     * @param name the name of the prioritization technique that has to be used to prioritize    products.
//     */
//    public void SetPrioritizationTechniqueByName(String name) {
//        for (PrioritizationTechnique pt : prioritizationTechniques) {
//            if (pt.getName().equals(name)) {
//                prioritizationTechnique = pt;
//                break;
//            }
//        }
//
//    }
    /**
     * Returns the prioritization techniques available.
     *
     * @return a list containing the available prioritization techniques.
     */
//    public List<PrioritizationTechnique> getPrioritizationTechniques() {
//        return prioritizationTechniques;
//    }
    /**
     * Load a feature model.
     *
     * @param filePath the path to the feature model file.
     * @param format the format of the feature model.
     * @throws Exception if the file format is incorrect.
     */
    public void loadFeatureModel(String inFile1) throws Exception {
        setRunning(true);
        setIndeterminate(true);
        setGlobalAction(GLOBAL_ACTION_LOAD_PRODUCTS);

        featureModelName = new File(inFile1).getName();
        //System.out.println(featureModelName);
        featureModelName = featureModelName.substring(0, featureModelName.lastIndexOf("."));
        int i = inFile1.lastIndexOf('.');
        if (i > 0) {
            FileExtension1 = inFile1.substring(i + 1);

        }

        try {
            BufferedReader in = new BufferedReader(new FileReader(inFile1));
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inFile1);
            NodeList nList = doc.getElementsByTagName("feature_tree");
        //  NodeList n = nList.item(0).getFirstChild().getTextContent();
            // int num = nList.getLength();
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node NNODE = nList.item(temp);
                // System.out.println("\nCurrent Element :" + NNODE.getNodeName());
                if (NNODE.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) NNODE;
                      //System.out.println(eElement.getFirstChild().getTextContent());

		//System.out.println(NNODE.getFirstChild().getTextContent());
                    //NodeList nListm = nListm.item(0).getFirstChild().getTextContent();
                    //    Element node3 = (Element) nList.item(0);
                    listAllFeatures(eElement);
                    //  System.out.println(node3);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        setRunning(false);
        setChanged();
        notifyObservers(this);
    }

    public void removeConstraint(int i) {
        featureModelConstraintsString.remove(i);
        setChanged();
        notifyObservers(featureModelConstraints);
    }

    public String getFeatureType(String feature) {
        if (coreFeatures.contains(feature)) {
            return CORE_FEATURE;
        } else if (deadFeatures.contains(feature)) {
            return DEAD_FEATURE;
        } else {
            return FREE_FEATURE;
        }
    }

    private Product toProduct(int[] vector) {

        Product product = new Product();
        for (int i : vector) {
            product.add(i);
        }
        return product;
    }

    /**
     * returns n products obtained at random from the solver.
     *
     * @param count the number of products to get.
     * @return the list of products obtained at random for the solver.
     */
    public List<Product> getUnpredictableProducts(int count) {
        List<Product> products = new ArrayList<Product>(count);

        while (products.size() < count) {

            try {
                if (solverIterator.isSatisfiable()) {
                    Product product = toProduct(solverIterator.model());

                    if (!products.contains(product)) {
                        products.add(product);
                    }

                } else {

                    FeatureModel fm = new XMLFeatureModel(fmPath, XMLFeatureModel.USE_VARIABLE_NAME_AS_ID);
                    fm.loadModel();
                    ReasoningWithSAT reasonerSAT = new FMReasoningWithSAT(solverName, fm, SAT_TIMEOUT);
                    reasonerSAT.init();
                    solver = (Solver) reasonerSAT.getSolver();
                    break;

                  //  solver.setTimeout(SAT_TIMEOUT);
                    //  solver.setOrder(order);
                    // solverIterator = new ModelIterator(solver);
                    // solverIterator.setTimeoutMs(ITERATOR_TIMEOUT);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return products;
    }

    /**
     * Save products to a file.
     *
     * @param outFile the file to write the products in.
     * @throws Exception if an error occurs while writing the products to the
     * file.
     */
    public void saveProducts(String outFile) throws Exception {

        BufferedWriter out = new BufferedWriter(new FileWriter(outFile));

        int featuresCount = featuresList.size();
        for (int i = 1; i <= featuresCount; i++) {
            out.write(i + "->" + this.featuresList.get(i - 1));
            out.newLine();
        }
//        for (Product product : products) {
//            int done = 0;
//            for (Integer feature : product) {
//                out.write("" + feature);
//                if (done < product.size()) {
//                    out.write(";");
//                }
//                done++;
//            }
//
//            out.newLine();
//        }
        out.close();

    }

    /**
     * Load products from a file.
     *
     * @param inFile the file to loead the products from.
     * @throws Exception if an error occurs while reading the products file.
     */
    public void loadstatechart(String inFile) throws Exception {
        setRunning(true);
        setIndeterminate(true);
        setGlobalAction(GLOBAL_ACTION_LOAD_PRODUCTS);
       // setCurrentAction(CURRENT_ACTION_LOAD_CONSTRAINTS);
        // clean();

//        featuresIntList = new ArrayList<Integer>();
//        featuresList = new ArrayList<String>();
//        namesToFeaturesInt = new HashMap<String, Integer>();
//        featureModelConstraints = new ArrayList<String>();
//        featureModelConstraintsString = new ArrayList<String>();
//        coreFeatures = new ArrayList<String>();
//        deadFeatures = new ArrayList<String>();
   //     StateChart sc = new XMLStatechart(inFile, XMLStatechart.USE_VARIABLE_NAME_AS_ID);
        //           sc.loadModel();
        // ReasoningWithSAT reasonerSAT = new FMReasoningWithSAT(solverName, sc, SAT_TIMEOUT);
        //BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        //data = new Vector();
        //columns = new Vector();
//                    statechartModelName = new File(inFile).getName();
//                statechartModelName = statechartModelName.substring(0, statechartModelName.lastIndexOf("."));
//       // products = null;
//                   statechartPath = inFile;
//        //System.out.println (statechartPath);
//        solver = null;
//        solverIterator = null;
        statechartModelName = new File(inFile).getName();
        statechartModelName = statechartModelName.substring(0, statechartModelName.lastIndexOf("."));
                   // FileExtension = statechartModelName.getExtension

        int i = inFile.lastIndexOf('.');
        if (i > 0) {
            FileExtension = inFile.substring(i + 1);

        }

        try {
            BufferedReader in = new BufferedReader(new FileReader(inFile));
            //products = new ArrayList<Product>();
//            String line;
//            String aLine ;
//            columnNames = new Vector();
//             StringTokenizer st1 = new StringTokenizer(in.readLine(), "|");
//            while(st1.hasMoreTokens())
//              columnNames.addElement(st1.nextToken());
//            System.out.println(columnNames);
//          // extract data
//          while ((aLine = in.readLine()) != null) {  
//            StringTokenizer st2 = 
//             new StringTokenizer(aLine, "|");
//            while(st2.hasMoreTokens())
//              data.addElement(st2.nextToken());
//            }
//            in.close();  
//            }
//          catch (Exception e) {
//            e.printStackTrace();
//            }   
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inFile);
            NodeList nList1 = doc.getElementsByTagName("UML:SimpleState");
            NodeList nList2 = doc.getElementsByTagName("UML:Transition");
            int num1 = nList1.getLength();
            int num2 = nList2.getLength();

            for (int l = 0; l < num1; l++) {
                Element node1 = (Element) nList1.item(l);
                listAllAttributes(node1);
            }
            for (int m = 0; m < num2; m++) {

                Element node2 = (Element) nList2.item(m);
                listAllTransition(node2);
                // System.out.println(listAllTransition());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        setRunning(false);
        setChanged();
        notifyObservers(this);
    }

    public static void listAllAttributes(Element element1) {
        // System.out.println("hh " + element.getNodeName());
        // get a map containing the attributes of this node
        NamedNodeMap attributes = element1.getAttributes();
        // get the number of nodes in this map
        int numAttrs = attributes.getLength();
        //System.out.println( numAttrs);
        for (int i = 0; i < numAttrs; i++) {
            Attr attr = (Attr) attributes.item(i);
            String attrName = attr.getNodeName();
            //  System.out.println(attrName);
            String attrValue = attr.getNodeValue();
            //  System.out.println(attrValue);
            if (attrName.equals("name")) {

                StateChartList.add(attrValue);
                //System.out.println(StateChartList);
            }

        }
    }

    public static void listAllTransition(Element element2) {
        // System.out.println("hh " + element.getNodeName());
        // get a map containing the attributes of this node
        NamedNodeMap attributes = element2.getAttributes();
        // get the number of nodes in this map
        int numAttrs2 = attributes.getLength();

        for (int n = 0; n < numAttrs2; n++) {
            Attr attr = (Attr) attributes.item(n);
            String attrName = attr.getNodeName();
            //System.out.println(attrName);
            String attrValue = attr.getNodeValue();
            //System.out.println(attrName);
            if (attrName.equals("name")) {
                // System.out.println(attrValue);
                TransitionList.add(attrValue);
                // System.out.println(TransitionList);
            }

        }
    }

    public static void listAllFeatures(Element eElement) {
        // System.out.println("hh " + element.getNodeName());
        NamedNodeMap attributes = eElement.getAttributes();
        // get a map containing the attributes of this node
        Node carsNode = eElement.getFirstChild();
      // NodeList carsNodeList = eElement.getFirstChild().getTextContent();
        //System.out.println(carsNode);
        // get the number of nodes in this map
        String numAttrs = carsNode.getNodeValue();
        //String[] output = numAttrs.split(":");
        //System.out.println(numAttrs);
        featuresList.add(numAttrs);
        // System.out.println(eElement.getFirstChild().getTextContent())

    }

//    
//    /**
//     * Load products from a file, after having loaded a FM that corresponds to the products.
//     * @param inFile the file to loead the products from.
//     * @throws Exception if an error occurs while reading the products file.
//     */
    /**
     * Quit the application.
     */
    public void quit() {
        System.exit(0);
    }

    public int getCurrentConstraint() {
        return currentConstraint;
    }

    public void setCurrentConstraint(int currentConstraint) {
        this.currentConstraint = currentConstraint;
        setChanged();
        notifyObservers();
    }

    public static List<String> getStateChartList() {

        //System.out.println(StateChartList);
        return StateChartList;
    }

    public String getStateChartName() {
        return statechartModelName;
    }

    public static List<String> getTransitionList() {
        //System.out.println (TransitionList);
        return TransitionList;
    }

    public String getStateChartFormat() {
        return FileExtension;

    }

    public String getFeatureModelFormat() {
        return FileExtension1;
    }

    public List<Integer> getStateChartIntList() {
        return StateIntList;
    }
}
