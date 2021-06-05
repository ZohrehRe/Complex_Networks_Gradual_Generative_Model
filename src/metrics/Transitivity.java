package metrics;

import org.jgrapht.Graph;

/**
 * Created by Zohreh on 7/1/2017.
 */
public class Transitivity implements GraphMetric {
    public double compute(Graph graph) {
        int totalTriangles = 0;
        int totalN = 0;
        for (Object v :  graph.vertexSet()) {
            int iTriangles = 0;
            for (Object edge :  graph.edgeSet()){
                if (graph.containsEdge(v , graph.getEdgeSource(edge)) && graph.containsEdge(v , graph.getEdgeTarget(edge)) && (!v.equals(graph.getEdgeTarget(edge))) && !v.equals(graph.getEdgeSource(edge))){
                    iTriangles++;
                }
            }
            int n = (graph.edgesOf(v).size() * (graph.edgesOf(v).size() - 1)) / 2;

            totalTriangles += iTriangles;
            totalN += n;
        }
        return (double)totalTriangles/totalN;
    }
}
