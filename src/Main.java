import Algorithms.Dijkstra;

import Graph.DirectedGraph;
import Graph.GraphInterface;
import Graph.UndirectedGraph;

public class Main {

    public static void main(String[] args) {
        GraphInterface<Character> graph = new UndirectedGraph<>();

        graph.addVertex('A');
        graph.addVertex('B');
        graph.addVertex('C');
        graph.addVertex('D');
        graph.addVertex('E');
        graph.addVertex('F');
        graph.addVertex('G');
        graph.addVertex('H');


        graph.addEdge('D', 'A');



        Dijkstra solveD = new Dijkstra(graph);

        System.out.println(solveD.shortestPath('D', 'A'));

        System.out.println(graph);
    }
}
