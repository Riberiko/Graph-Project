import Algorithms.BellmanFord;
import Algorithms.Dijkstra;

import Graph.DirectedGraph;
import Graph.GraphInterface;
import Graph.UndirectedGraph;

public class Main {

    public static void main(String[] args) {
        DirectedGraph<Character> graph = new DirectedGraph<>();

        graph.addVertex('A');
        graph.addVertex('B');
        graph.addVertex('C');
        graph.addVertex('D');
        graph.addVertex('E');
        graph.addVertex('F');
        graph.addVertex('G');
        graph.addVertex('H');


        graph.addEdge('A', 'B', 10);
        graph.addEdge('A', 'G', 2);

        graph.addEdge('C', 'B',-10);

        graph.addEdge('E', 'D',2);

        graph.addEdge('F', 'E',3);

        graph.addEdge('G', 'H',1);
        graph.addEdge('G', 'C',11);
        graph.addEdge('G', 'F',6);
        

        BellmanFord solve = new BellmanFord(graph);
        System.out.println(solve.shortestPath('A', 'D'));
        System.out.println(solve.shortestPathCost('A', 'D'));


        System.out.println(graph);
    }
}
