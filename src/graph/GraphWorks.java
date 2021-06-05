package graph;

import org.jgrapht.Graph;
import org.jgrapht.VertexFactory;
import org.jgrapht.generate.GnpRandomGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.DepthFirstIterator;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Zohreh on 4/4/2017.
 */
public class GraphWorks {
    public static boolean replaceVertex(Object oldVertex, Object newVertex , Graph<Object, DefaultEdge> completeGraph)
    {
        if ((oldVertex == null) || (newVertex == null)) {
            return false;
        }
        Set<DefaultEdge> relatedEdges = completeGraph.edgesOf(oldVertex);
        completeGraph.addVertex(newVertex);

        Object sourceVertex;
        Object targetVertex;
        for (DefaultEdge e : relatedEdges) {
            sourceVertex = completeGraph.getEdgeSource(e);
            targetVertex = completeGraph.getEdgeTarget(e);
            if (sourceVertex.equals(oldVertex)
                    && targetVertex.equals(oldVertex))
            {
                completeGraph.addEdge(newVertex, newVertex);
            } else {
                if (sourceVertex.equals(oldVertex)) {
                    completeGraph.addEdge(newVertex, targetVertex);
                } else {
                    completeGraph.addEdge(sourceVertex, newVertex);
                }
            }
        }
        completeGraph.removeVertex(oldVertex);
        return true;
    }

    public static void printGraph( Graph graph){
        Iterator<Object> iter =
                new DepthFirstIterator<Object, DefaultEdge>(graph);
        Object vertex;
        while (iter.hasNext()) {
            vertex = iter.next();
            System.out.println(
                    "Vertex " + vertex.toString() + " is connected to: "
                            + graph.edgesOf(vertex).toString());
        }
    }

    public static void addNum2graphsVertices(Graph graph){
        Set<Object> vertices = new HashSet<Object>();
        vertices.addAll(graph.vertexSet());
        Integer counter = 0;
        for (Object vertex : vertices) {
            replaceVertex(vertex, (Object) counter++ , graph);
        }
    }

    public static void createPrandGraph(VertexFactory vFactory, Graph graph, int vNum, double p){
        GnpRandomGraphGenerator<Object, DefaultEdge> randomGraphGenerator = new GnpRandomGraphGenerator<Object, DefaultEdge>(vNum, p);
        randomGraphGenerator.generateGraph(graph, vFactory, null);
    }

    public static void getGraphFromFile(VertexFactory vFactory, Graph graph, String file){
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();

            while (line != null) {
                int v1 = Integer.parseInt(line.split(" ")[0]);
                int v2 = Integer.parseInt(line.split(" ")[1]);
                if (graph.containsVertex(v1) && graph.containsVertex(v2)) {
                    graph.addEdge(v1, v2);
                }
                if(!graph.containsVertex(v1) && graph.containsVertex(v2)) {
                    graph.addVertex(v1);
                    graph.addEdge(v1, v2);
                }
                if(graph.containsVertex(v1) && !graph.containsVertex(v2)) {
                    graph.addVertex(v2);
                    graph.addEdge(v1, v2);
                }
                if (!graph.containsVertex(v2) && !graph.containsVertex(v1)) {
                    graph.addVertex(v1);
                    graph.addVertex(v2);
                    graph.addEdge(v1, v2);
                }
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void outPutGraph(String fileName, Graph graph){
        try{
            PrintWriter writer = new PrintWriter(fileName, "UTF-8");
            Set<Object> vertexSet = graph.vertexSet();
            Set<DefaultEdge> edges;
            for (Object vertex : vertexSet) {
                edges = graph.edgesOf(vertex);
                for (DefaultEdge edge : edges){
                    if (graph.getEdgeSource(edge).toString().equals(vertex.toString())){
                        if (Integer.parseInt(graph.getEdgeTarget(edge).toString()) > Integer.parseInt(vertex.toString())){
                            writer.println(vertex.toString()+ "\t" +graph.getEdgeTarget(edge).toString());
                        }
                    }else {
                        if (Integer.parseInt(graph.getEdgeSource(edge).toString()) > Integer.parseInt(vertex.toString())){
                            writer.println(vertex.toString()+ "\t" +graph.getEdgeSource(edge).toString());
                        }
                    }
                }

            }
            writer.close();
        } catch (IOException e) {
            // do something
        }


    }

    public static void printGraphAttr(Graph graph){
        System.out.print("Metrics: ");
        double[] attr = new GraphAttr().compute(graph);
        for (int i = 0 ; i < attr.length; i++)
            System.out.print(attr[i] + " ");
        System.out.println();
    }
}
