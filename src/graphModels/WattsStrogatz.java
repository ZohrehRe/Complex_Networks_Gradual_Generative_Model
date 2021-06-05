package graphModels;

import graphModels.GradualGenerativeModel;
import org.jgrapht.Graph;

import java.util.Random;

/**
 * Created by Zohreh on 3/6/2017.
 */
public class WattsStrogatz implements GradualGenerativeModel {
    private double beta;
    private int degree;
    int n;
    int current;
    Random r = new Random();
    int s;

    public WattsStrogatz(double beta, int degree, int n) {
        this.beta = beta;
        this.degree = degree;
        this.n = n;
    }

    //Adding nodes to graph
    public Graph generate(Graph graph) {
        s = graph.vertexSet().size();
        for (int i = 0; i < n; i++) {
            graph.addVertex(s + i);
        }

        // Adding the circle links.
        int kk = degree / 2;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= kk; j++) {
                int jj = (i + j) % n;
                graph.addEdge( s + i , s + jj);
            }
        }

        //Rewiring
        current = 0;
        while (current < n){
            rewire1(graph);
        }
        return graph;
    }

    public Graph rewire1(Graph graph){
        int kk = degree / 2;
        for (int j = 1; j <= kk; j++) {
            int jj = (current + j) % n;
            if (r.nextDouble() < beta && graph.containsEdge(current + s, jj+s)) {
                int newTarget = chooseNewNode(graph , current, jj);
                if (newTarget == -100){
                    return graph;
                } else if (!graph.containsEdge(current + s , newTarget))
                    graph.addEdge(current + s , newTarget);
                graph.removeEdge(current+s , jj+s);
            }
        }
        current += 1;
        return graph;
    }

    protected int chooseNewNode(Graph graph, int avoid, int old) {
        int newId = 0;
        boolean edgeExists = true;
        if ((graph.vertexSet().size() * (graph.vertexSet().size() - 1) / 2) == graph.edgeSet().size())
            return -100;
        do {
            newId = r.nextInt(graph.vertexSet().size());
            edgeExists = graph.containsEdge(avoid + s , newId);
        } while (newId == avoid+s || edgeExists || newId == old + s);

        return newId;
    }
}
