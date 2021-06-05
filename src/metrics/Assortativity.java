package metrics;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.Set;

/**
 * Created by Zohreh on 7/1/2017.
 */
public class Assortativity implements GraphMetric {

    public double compute(Graph graph) {
        double r = 0.0;

        Set<DefaultEdge> edges = graph.edgeSet();
        int dSource = 0;    // Degree of the Source
        int dDestination = 0;   // Degree of the Destination

        double sumMultSourceDestination = 0.0;    // Sum{dSource*dDestination}
        double sumAddSourceDestination = 0.0;    // Sum{dSource+dDestination}
        double sumAddSquaredSourceDestination = 0.0;  // Sum{dSource^2+dDestination^2}
        double M = edges.size();      // Number of Edges
        Object sourceV, destV;
        for (DefaultEdge e : edges) {
            sourceV = graph.getEdgeSource(e);
            destV = graph.getEdgeTarget(e);
            dSource = graph.edgesOf(sourceV).size();
            dDestination = graph.edgesOf(destV).size();
            sumMultSourceDestination += (dSource * dDestination);
            sumAddSourceDestination += 0.5 * (dSource + dDestination);
            sumAddSquaredSourceDestination += 0.5 * ((dSource * dSource) + (dDestination * dDestination));
        }

        double numerator = ( (1/M) * sumMultSourceDestination)
                - (Math.pow( (1/M) * sumAddSourceDestination, 2));
        double denominator = ( (1/M) * sumAddSquaredSourceDestination)
                - (Math.pow( (1/M) * sumAddSourceDestination, 2));

        if(denominator == numerator){ // special case: a ring network will result in 0/0. The resulting NaN is wrong, it has to be 1
            return 1;
        }
        return numerator / denominator;
    }
}
