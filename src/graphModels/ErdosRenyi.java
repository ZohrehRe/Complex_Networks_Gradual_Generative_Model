package graphModels;

import org.jgrapht.Graph;

import java.util.Random;

/**
 * Created by Zohreh on 2/28/2017.
 */
public class ErdosRenyi implements GradualGenerativeModel {
    double p;
    int n;

    public ErdosRenyi(double p , int n) {
        this.p = p;
        this.n = n;
    }
//TODO be harki ba ye ehtemali vasl mishe! khube?!?
    public Graph generate(Graph graph) {
        int s = graph.vertexSet().size();
        int v2= graph.vertexSet().size();
        for (int i = 0 ; i < n ; i++){
            graph.addVertex(v2 + i);
        }

        for (int i = 0 ; i < n ; i++){
            for (int j = 0 ; j < graph.vertexSet().size() ; j++) {
                double rand = Math.random();
                if (rand <= p && !graph.containsEdge(i+s, j) && i+s != j)
                    graph.addEdge(i+s, j);
            }
        }
        return graph;
    }

    public Graph generateWithP(Graph graph) {
        int v2= graph.vertexSet().size();
        graph.addVertex(v2);

        for (int i = 0 ; i < graph.vertexSet().size()-1 ; i++){
            double rand = Math.random();
            if (rand <= p)
                graph.addEdge(i , v2);
        }
        return graph;
    }

    public Graph addNode(Graph graph){
        int v2= graph.vertexSet().size();
        graph.addVertex(v2);
        // tedade yaal ha chandta bashe!(ye darsaD az tedad node ai ke mitune beheshun vasl she!
        double rand = Math.random();
        long edgeNum = Math.round(rand*(graph.vertexSet().size() - 1));

        for (int i = 0 ; i < edgeNum ; i++){
            Random generator = new Random();

            int rand2 = generator.nextInt(graph.vertexSet().size() - 1);
            graph.addEdge(rand2 , v2);
        }
        return graph;
    }
}
