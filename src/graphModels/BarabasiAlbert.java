package graphModels;

import graph.GraphWorks;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Zohreh on 2/28/2017.
 */
public class BarabasiAlbert implements GradualGenerativeModel {
    int m, n;

    public BarabasiAlbert(int m , int n) {
        if (m < 0)
            this.m = 0;
        else
            this.m = m;
        this.n = n;
    }

    public Graph generate(Graph graph) {
        for (int i = 0 ; i < n ; i++) {
            graph = generate1(graph);
        }
        return graph;
    }

    public Map<String , Double> findProbability(Graph graph){
        Map<String , Double> probabilities = new HashMap<String, Double>();
        double lastP = 0;
        for (Object vertex :  graph.vertexSet()){
            double p = (double) graph.edgesOf(vertex).size() / (graph.edgeSet().size()*2);
            lastP += p;
            probabilities.put(vertex.toString() , lastP);
        }
        Iterator it = probabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
        }
        return probabilities;
    }

    public Graph generate1(Graph graph) {
        Map<String , Double> p = findProbability(graph);
        int v2= graph.vertexSet().size();
        graph.addVertex(v2);
        int maxEdge = (graph.vertexSet().size() * (graph.vertexSet().size() - 1)) / 2;
        int maxM = maxEdge - graph.edgeSet().size();
        int degree = m;
        while(degree != 0){
            int minVertex = Integer.MAX_VALUE;
            double rand = Math.random();
            for (String vertex : p.keySet()) {
                //
                if (rand <= p.get(vertex) && minVertex > Integer.parseInt(vertex)) {
                    minVertex = Integer.parseInt(vertex);
                }
            }
            if (!graph.containsEdge(Integer.parseInt(String.valueOf(minVertex)), v2)) {
                graph.addEdge(Integer.parseInt(String.valueOf(minVertex)), v2);
                degree--;
            }
        }
        return graph;
    }
}
