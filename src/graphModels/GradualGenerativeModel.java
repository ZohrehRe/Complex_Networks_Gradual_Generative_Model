package graphModels;

import org.jgrapht.Graph;

/**
 * Created by Zohreh on 2/28/2017.
 */
public interface GradualGenerativeModel {
    Graph generate(Graph graph);
}
