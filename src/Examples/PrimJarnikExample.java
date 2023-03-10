package Examples;

import Algorithms.PrimJarnik;
import Graph.UndirectedGraph;

public class PrimJarnikExample {

    public static void main(String[] args) {

        UndirectedGraph<Character> graph = new UndirectedGraph<>();

        graph.addVertex('S');
        graph.addVertex('A');
        graph.addVertex('B');
        graph.addVertex('C');
        graph.addVertex('D');
        graph.addVertex('E');
        graph.addVertex('F');
        graph.addVertex('G');

        graph.addEdge('A', 'B', 7);
        graph.addEdge('A', 'D',4);

        graph.addEdge('B', 'D',2);

        graph.addEdge('D', 'C',5);

        graph.addEdge('C', 'E',2);
        graph.addEdge('C', 'F',5);
        graph.addEdge('C', 'S',8);

        graph.addEdge('S', 'G',8);

        graph.addEdge('F', 'G',2);

        graph.addEdge('F', 'E',10);
        graph.addEdge('G', 'G',10);




        PrimJarnik<Character> solve = new PrimJarnik<>(graph);
        System.out.println("Path : " + solve.minSpanningTree('A'));
        System.out.println("Cost : " + solve.minSpanningTreeCost('A'));
        System.out.println(graph);
    }
}
