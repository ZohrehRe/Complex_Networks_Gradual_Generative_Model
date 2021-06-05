package metrics;

import graph.GraphWorks;
import org.jgrapht.Graph;
import org.jgrapht.VertexFactory;
import org.jgrapht.graph.ClassBasedVertexFactory;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

/**
 * Created by Zohreh on 7/1/2017.
 */
public class CheckMetrics {
    public static void main(String[] args) {
        GraphWorks graphWorks = new GraphWorks();
        VertexFactory<Object> vFactory = new ClassBasedVertexFactory<Object>(Object.class);
        Graph graph = readingGraph(graphWorks, vFactory);
        Transitivity transitivity = new Transitivity();
        ClusteringCoefficient clusteringCoefficient = new ClusteringCoefficient();
        Assortativity assortativity = new Assortativity();
        System.out.println(transitivity.compute(graph));
        System.out.println(clusteringCoefficient.compute(graph));
        System.out.println(assortativity.compute(graph));

    }
    public static Graph readingGraph(GraphWorks graphWorks , VertexFactory<Object> vFactory){
        Graph<Integer, DefaultEdge> g2 = new SimpleGraph<Integer, DefaultEdge>(DefaultEdge.class);
        graphWorks.getGraphFromFile(vFactory, g2 , "F:\\edu\\Courses\\term8\\porozhe\\project\\project1.1-Manual-GA-F2\\dataSet1.txt");
        System.out.println("g2:");
        //graphWorks.printGraph(g2);
        return g2;
    }
}
