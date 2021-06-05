package metrics;

import org.jgrapht.Graph;

/**
 * Created by Zohreh on 7/1/2017.
 */
public interface GraphMetric {
    double compute(Graph graph);
}
