package GraphTests;

import Graph.DirectedGraph;
import Graph.EDGESTATE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DirectedTests {

    DirectedGraph<String> graph;

    @BeforeEach
    void setUp(){
        graph = new DirectedGraph<>();
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
        Assertions.assertTrue(graph.getVertex("Riko").getEdge("Adam") != null);
        Assertions.assertTrue(graph.getVertex("Adam").getEdge("Riko") == null);

    }

    @Test
    public void removeEdge(){
        addEdge();
        graph.removeEdge("Riko", "Adam");
        Assertions.assertTrue(graph.getVertex("Riko").getEdge("Adam") == null);
    }

    @Test
    public void setEdgeWeightsAndState(){
        addEdge();
        graph.getVertex("Riko").getEdge("Adam").setWeight(5);
        graph.getVertex("Riko").getEdge("Adam").setEdgeState(EDGESTATE.PATH);
        Assertions.assertEquals(5, graph.getVertex("Riko").getEdge("Adam").getWeight());
        Assertions.assertEquals(EDGESTATE.PATH, graph.getVertex("Riko").getEdge("Adam").getEdgeState());
    }

}
