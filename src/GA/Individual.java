package GA;

import graph.GraphWorks;
import org.jgrapht.Graph;
import org.jgrapht.VertexFactory;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

/**
 * Created by Zohreh on 5/28/2017.
 */
public class Individual {
    static int defaultGeneLength = 3;
    static int modelParamGeneLength = 4;
    static int stepSize;
    private Graph<Object, DefaultEdge> graph;

    private double[] genes = new double[defaultGeneLength];
    private double[] modelParamGenes = new double[modelParamGeneLength];
    //erP, baM, wsB, wsD

    // Cache
    private double fitness = 0;

    // Create a random individual
    public void generateIndividual() {
        for (int i = 0; i < size(); i++) {
            double gene = Math.random();
            genes[i] = gene;
        }

        for (int i = 0 ; i < modelParamGeneLength ; i++){
                modelParamGenes[i] = Math.random();
        }
    }

    public static void setDefaultGeneLength(int length) {
        defaultGeneLength = length;
    }

    public double getGene(int index) {
        return genes[index];
    }
    public double getModelParamGene(int index) {
        return modelParamGenes[index];
    }


    public void setGene(int index, double value) {
        genes[index] = value;
        fitness = 0;
    }
    public void setModelParamGene(int index, double value) {
        modelParamGenes[index] = value;
        fitness = 0;
    }
    public Graph<Object, DefaultEdge> getGraph() {
        return graph;
    }

    public void setGraph(Graph<Object, DefaultEdge> graph) {
        this.graph = graph;
    }

    public double[] getGenes() {
        return genes;
    }

    public void setGenes(double[] genes) {
        this.genes = genes;
    }

    /* Public methods */
    public int size() {
        return genes.length;
    }

    public double getFitness() {
        if (fitness == 0) {
            fitness = FitnessCalc.getFitness(this);
        }
        return fitness;
    }

    @Override
    public String toString() {
        String geneString = "";
        for (int i = 0; i < size(); i++) {
            geneString += getGene(i);
            geneString += " ";
        }
        geneString += "| ";
        for (int i = 0; i < modelParamGeneLength; i++) {
            geneString += getModelParamGene(i);
            geneString += " ";
        }

        return geneString;
    }

    public Individual(VertexFactory<Object> vFactory, CompleteGraphGenerator<Object , DefaultEdge> cmGenerator, GraphWorks graphWorks) {
        graph = new SimpleGraph<Object, DefaultEdge>(DefaultEdge.class);
        cmGenerator.generateGraph(graph, vFactory, null);
        graphWorks.addNum2graphsVertices(graph);
    }

    public static int getStepSize() {
        return stepSize;
    }

    public static void setStepSize(int stepSize) {
        Individual.stepSize = stepSize;
    }

    public double[] getModelParamGenes() {
        return modelParamGenes;
    }

    public void setModelParamGenes(double[] modelParamGenes) {
        this.modelParamGenes = modelParamGenes;
    }
}
