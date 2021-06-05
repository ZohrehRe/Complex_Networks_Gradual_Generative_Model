package graph;

import metrics.Assortativity;
import metrics.ClusteringCoefficient;
import metrics.Transitivity;
import org.jgrapht.Graph;

/**
 * Created by Zohreh on 5/29/2017.
 */
public class GraphAttr {
    static Assortativity assortativityFinder = new Assortativity();
    static Transitivity transitivityFinder = new Transitivity();
    static ClusteringCoefficient clusteringCoefficientFinder = new ClusteringCoefficient();

    public static double[] compute(Graph graph){
        double[] graphAttr = new double[8];
        graphAttr[0] = assortativityFinder.compute(graph);
        graphAttr[1] = transitivityFinder.compute(graph);
        graphAttr[2] = clusteringCoefficientFinder.compute(graph);
        graphAttr[3] = 0;
        graphAttr[4] = 0;
        graphAttr[5] = 0;
        graphAttr[6] = 0;
        graphAttr[7] = 0;
        return graphAttr;
    }
}
