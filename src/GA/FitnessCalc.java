package GA;

//import com.sun.xml.internal.fastinfoset.tools.FI_DOM_Or_XML_DOM_SAX_SAXEvent;
import graph.GraphAttr;
import graph.GraphWorks;
import graphModels.BarabasiAlbert;
import graphModels.ErdosRenyi;
import graphModels.GradualGenerativeModel;
import graphModels.WattsStrogatz;
import metrics.Assortativity;
import metrics.ClusteringCoefficient;
import metrics.Transitivity;
import org.jgrapht.Graph;
import org.jgrapht.VertexFactory;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.ClassBasedVertexFactory;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Zohreh on 5/28/2017.
 */
public class FitnessCalc {
    static GraphAttr graphAttr = new GraphAttr();
    static double[] solution;
    static int totalN, stepN;


    public static void setSolution(double[] newSolution) {
        solution = newSolution;
    }

    public static double getFitness(Individual individual) {
        individual.setGraph(generate("out.txt" , totalN, stepN, individual.getGenes(),individual.getModelParamGenes()));
        double[] individualAttr = graphAttr.compute(individual.getGraph());
        return getMaxFitness() - attrDiff(solution , individualAttr);
    }


    public static double attrDiff(double[] targetAttr, double[] graphAttr){
        double totalDiff = 0;
        for (int i = 0; i < targetAttr.length; i++){
            totalDiff += Math.pow((targetAttr[i] - graphAttr[i]) , 2);
        }
        return totalDiff;
    }
    public static Graph generate(String fileName, int totalN, int stepN, double[] genes, double[] modelParamGenes){
        int numOfSteps = totalN/stepN;
        System.out.println("num of steps: " + numOfSteps);
        GradualGenerativeModel generativeModel;
        Graph<Object, DefaultEdge> graph = new SimpleGraph<Object, DefaultEdge>(DefaultEdge.class);
        //creating a click for start?!?!?
        GraphWorks graphWorks = new GraphWorks();
        VertexFactory<Object> vFactory = new ClassBasedVertexFactory<Object>(Object.class);
        CompleteGraphGenerator<Object , DefaultEdge> cmGenerator = new CompleteGraphGenerator<Object, DefaultEdge>(4);
        cmGenerator.generateGraph(graph, vFactory, null);
        graphWorks.addNum2graphsVertices(graph);
        //end of creating click
        for (int i = 0 ; i < numOfSteps ; i++){
            System.out.println("-----------------step " + i + " -------------------------");
            generativeModel = chooseModel(graph.vertexSet().size(), stepN, modelParamGenes, genes);
            graph = generativeModel.generate(graph);
        }

        return graph;
    }

    public static GradualGenerativeModel chooseModel(int graphSize, int stepN, double[] modelParamGenes, double[] genes){
        GradualGenerativeModel generativeModel = null;
        Random r = new Random();
        double randomValue = r.nextDouble();
        ArrayList<Double> geneProb = cumulativeDistribution(genes);
        randomValue *= geneProb.get(2);
        for (int i = 0 ; i < geneProb.size() ; i++){
            if (randomValue <= geneProb.get(i)){
                if ((i != 0 && randomValue >= geneProb.get(i-1)) || i == 0) {
                    switch (i) {
                        case 0:
                            generativeModel = new ErdosRenyi(modelParamGenes[0], stepN);
                            System.out.println("erdos model");
                            break;
                        case 1:
                            int m = (int) (Math.round(modelParamGenes[1] * (Math.min(stepN , graphSize))) - 1);
                            generativeModel = new BarabasiAlbert(m, stepN);
                            System.out.println("barabasi model");
                            break;
                        case 2:
                            generativeModel = new WattsStrogatz(modelParamGenes[2], (int) Math.round(modelParamGenes[3] * ((stepN - 1)/2)), stepN);
                            System.out.println("watts model");
                            break;
                    }
                }
            }
        }
        return generativeModel;
    }

    public static ArrayList<Double> cumulativeDistribution(double[] genes){
        double alleleSum = 0;
        ArrayList<Double> geneProb = new ArrayList();
        for (int i = 0 ; i < genes.length ; i++) {
            alleleSum += genes[i];
            geneProb.add(alleleSum);
        }
        return geneProb;
    }

    // Get optimum fitness
    static double getMaxFitness() {
        return 8;
    }

    public static int getTotalN() {
        return totalN;
    }

    public static void setTotalN(int totalN) {
        FitnessCalc.totalN = totalN;
    }

    public static int getStepN() {
        return stepN;
    }

    public static void setStepN(int stepN) {
        FitnessCalc.stepN = stepN;
    }
}
