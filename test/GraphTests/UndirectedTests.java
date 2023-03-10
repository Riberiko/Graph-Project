package GraphTests;

import Graph.EDGESTATE;
import Graph.UndirectedGraph;
import Graph.UndirectedGraph.Edge;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

public class UndirectedTests {

    UndirectedGraph<String> graph;

    @BeforeEach
    void setUp(){
        graph = new UndirectedGraph<>();
    }

    @Test
    public void addVertex(){
        graph.addVertex("Riko");
        graph.addVertex("Adam");

        Assertions.assertTrue(graph.getVertex("Riko") != null);
        Assertions.assertTrue(graph.getVertex("Adam") != null);
    }

    @Test
    public void removeVertex(){
        addVertex();
        graph.removeVertex("Riko");
        Assertions.assertTrue(graph.getVertex("Riko") == null);
        Assertions.assertTrue(graph.getVertex("Adam") != null);
    }

    @Test
    public void setVertex(){
        addVertex();
        graph.getVertex("Riko").setData("Athanase");
        Assertions.assertTrue(graph.getVertex("Riko") == null);
        Assertions.assertTrue(graph.getVertex("Athanase") != null);
    }

    @Test
    public void addEdge(){
        addVertex();
        graph.addEdge("Riko", "Adam");
        Assertions.assertTrue(graph.getEdges().size() == 1);

    }

    @Test
    public void removeEdge(){
        addEdge();
        graph.removeEdge("Riko", "Adam");
        Assertions.assertTrue(graph.getEdges().size() == 0);
    }

    @Test
    public void setEdgeWeightsAndState(){
        addEdge();

        LinkedList<Edge<String>> edges = graph.getEdges();

        Edge<String> edge = null;

        for(Edge<String> e : edges){
            if(e.getA().getData() == "Riko" || e.getB().getData() == "Riko"){
                edge = e;
                break;
            }
        }

        edge.setWeight(5);
        edge.setState(EDGESTATE.PATH);
        Assertions.assertEquals(5, edge.getWeight());
        Assertions.assertEquals(EDGESTATE.PATH, edge.getState());
    }

}
