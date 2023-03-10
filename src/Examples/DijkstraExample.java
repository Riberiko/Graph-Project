package Examples;

import Algorithms.Dijkstra;
import Graph.UndirectedGraph;

public class DijkstraExample {

    public static void main(String[] args) {
        UndirectedGraph<Character> graph = new UndirectedGraph<>();

        graph.addVertex('A');
        graph.addVertex('B');
        graph.addVertex('C');
        graph.addVertex('D');
        graph.addVertex('E');
        graph.addVertex('F');
        graph.addVertex('G');
        graph.addVertex('H');

        graph.addEdge('A', 'D', 9);
        graph.addEdge('A','E',5);
        graph.addEdge('D','B',5);
        graph.addEdge('B','C',1);
        graph.addEdge('C','E',9);
        graph.addEdge('D','F',3);
        graph.addEdge('B','G',8);
        graph.addEdge('B', 'F', 9);
        graph.addEdge('C','G',6);
        graph.addEdge('G','H',8);

        Dijkstra<Character> solve = new Dijkstra<>(graph);

        System.out.println("Path : "+solve.shortestPath('A','H'));
        System.out.println("Cost : "+solve.shortestPathCost('A','H'));
        System.out.println(graph);
    }
}
