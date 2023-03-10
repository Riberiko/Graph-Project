package Examples;

import Algorithms.DAG;
import Graph.DirectedGraph;

public class DAGExample {

    public static void main(String[] args) {
        DirectedGraph<Character> graph = new DirectedGraph<>();

        graph.addVertex('A');
        graph.addVertex('B');
        graph.addVertex('C');
        graph.addVertex('D');
        graph.addVertex('E');
        graph.addVertex('F');
        graph.addVertex('G');
        graph.addVertex('S');

        graph.addEdge('A','C');
        graph.addEdge('A', 'E', 5);
        graph.addEdge('B', 'C', 3);
        graph.addEdge('B','D',7);
        graph.addEdge('C', 'F');
        graph.addEdge('D', 'C', 9);
        graph.addEdge('D', 'F', 8);
        graph.addEdge('E', 'C', -2);
        graph.addEdge('E', 'F', -2);
        graph.addEdge('S', 'B', 2);
        graph.addEdge('S','D');

        DAG<Character> solve = new DAG<>(graph);
        System.out.println("Path : " + solve.shortestPath('A','F'));
        System.out.println("Cost : " + solve.shortestPathCost('A','F'));
        System.out.println(graph);
    }
}
