package graph;

import graphModels.BarabasiAlbert;
import graphModels.ErdosRenyi;
import graphModels.WattsStrogatz;
import org.jgrapht.*;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.*;
/**
 * Created by Zohreh on 2/19/2017.
 */
public class HelloJGraphT {
    private HelloJGraphT()
    {
    }
    public static void main(String[] args)
    {
        GraphWorks graphWorks = new GraphWorks();
        VertexFactory<Object> vFactory = new ClassBasedVertexFactory<Object>(Object.class);
        CompleteGraphGenerator<Object , DefaultEdge> cmGenerator = new CompleteGraphGenerator<Object, DefaultEdge>(4);

        int n = 10;

        //........................graphe avalie!.........................................................
        Graph<Object, DefaultEdge> graph = new SimpleGraph<Object, DefaultEdge>(DefaultEdge.class);
        cmGenerator.generateGraph(graph, vFactory, null);
        graphWorks.addNum2graphsVertices(graph);
        System.out.println("...................first graph:");
        graphWorks.printGraph(graph);

        //.....................erdos reniy graph generation (gradual)....................................
        checkGradualErdosRenyi(graph , graphWorks , vFactory , cmGenerator, n);

        //....................barabasi albert graph generation(gradual)..................................
        checkBarabasiAlbert(graph , graphWorks, vFactory, cmGenerator, n);

        //.....................watts strogatz............................................................
        checkWattsStrogatz(graph , graphWorks, vFactory, n);
        checkBarabasiAlbert(graph , graphWorks, vFactory, cmGenerator, n);
        checkWattsStrogatz(graph , graphWorks, vFactory, n);
        checkGradualErdosRenyi(graph , graphWorks , vFactory , cmGenerator, n);
        checkWattsStrogatz(graph , graphWorks, vFactory, n);

        //transitivity of graphs
        /*System.out.println("transitivity randGraph = " + findTransitivity(randGraph));
        System.out.println("transitivity g2 = " + findTransitivity(g2));*/

    }





    public static void checkErdosRenyi(GraphWorks graphWorks , VertexFactory<Object> vFactory , int n){
        Graph<Object, DefaultEdge> randGraph = new SimpleGraph<Object, DefaultEdge>(DefaultEdge.class);

        graphWorks.createPrandGraph(vFactory , randGraph , 100 , 0.3);
        graphWorks.addNum2graphsVertices(randGraph);
        //graphWorks.printGraph(randGraph);
    }

    public static void checkReadingGraph(GraphWorks graphWorks , VertexFactory<Object> vFactory){
        Graph<Integer, DefaultEdge> g2 = new SimpleGraph<Integer, DefaultEdge>(DefaultEdge.class);
        graphWorks.getGraphFromFile(vFactory, g2 , "F:\\edu\\Courses\\term8\\porozhe\\project\\project1.1\\src\\main\\dataSet1.txt");
        System.out.println("g2:");
        //graphWorks.printGraph(g2);

    }

    public static void checkBarabasiAlbert(Graph graph , GraphWorks graphWorks , VertexFactory<Object> vFactory , CompleteGraphGenerator<Object , DefaultEdge> cmGenerator , int n){
        System.out.println("...................barabasi-albert:");
        BarabasiAlbert ba = new BarabasiAlbert(2 , n);
        graph= ba.generate(graph);
        graphWorks.printGraph(graph);
    }

    public static void checkGradualErdosRenyi(Graph graph , GraphWorks graphWorks , VertexFactory<Object> vFactory , CompleteGraphGenerator<Object , DefaultEdge> cmGenerator , int n){
        System.out.println("...................erdos renyi:");
        ErdosRenyi er = new ErdosRenyi(0.1 , n);
        graph= er.generate(graph);
        graphWorks.printGraph(graph);
    }

    public static void checkWattsStrogatz(Graph graph , GraphWorks graphWorks , VertexFactory<Object> vFactory , int n){
        System.out.println("...................watts strogatz:");
        WattsStrogatz wattsStrogatz = new WattsStrogatz(0.7 ,2,n);
        wattsStrogatz.generate(graph);
        graphWorks.printGraph(graph);
    }
}