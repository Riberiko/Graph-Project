package Algorithms;

import Graph.EDGESTATE;
import Graph.GraphInterface;
import Graph.UndirectedGraph;
import Graph.UndirectedGraph.Vertex;
import Graph.UndirectedGraph.Edge;

import java.util.LinkedList;
import java.util.PriorityQueue;

public class PrimJarnik<T extends Comparable<T>> {

    private final UndirectedGraph<T> graph;
    private final LinkedList<Vertex<T>> vertices;
    private final LinkedList<Edge<T>> edges;

    private final PriorityQueue<Edge<T>> hold;


    public PrimJarnik(GraphInterface<T> graph){
        if(graph.getClass() != UndirectedGraph.class) throw new IllegalArgumentException("The graph must be Undirected");
        this.graph = (UndirectedGraph<T>) graph;
        vertices = this.graph.getVertices();
        edges = this.graph.getEdges();
        hold = new PriorityQueue<>();
    }

    /**
     *Solves for the shortest path using the Prim Jerkin's algorithm
     *
     * Runtime : O( E log(V) )
     *
     * @param start the start vertex label
     * @return  the minimum spanning tree path
     */
    public LinkedList<Vertex<T>>  minSpanningTree(T start){
        Vertex<T> v = graph.getVertex(start);
        if(v == null) return null;
        LinkedList<Vertex<T>> path = new LinkedList<>();
        v.setDistance(0, null, null);
        getEdges(v);
        path.add(v);

        while(!hold.isEmpty()){
            Edge<T> currentEdge = hold.poll();

            //when both vertices have been visited we already have the best path for them
            if(currentEdge.getA().getIsVisited() && currentEdge.getB().getIsVisited()) {
                currentEdge.setState(EDGESTATE.RELAXED);
                continue;
            }

            Vertex<T> from = (currentEdge.getA().getIsVisited()) ? currentEdge.getA() : currentEdge.getB();
            Vertex<T> to = (from == currentEdge.getB()) ? currentEdge.getA() : currentEdge.getB();

            if(from.getDistance() + currentEdge.getWeight() < to.getDistance()) {
                to.setDistance(from.getDistance() + currentEdge.getWeight(), from, currentEdge);
                currentEdge.setState(EDGESTATE.PATH);
            }
            getEdges(to);
            path.addLast(to);
        }

        return path;
    }

    /**
     * Retrieves all the edges for this vertex
     *
     * Runtime : O(E)
     *
     * @param vertex    the vertex of the nodes you want
     */
    private void getEdges(Vertex<T> vertex){
        vertex.setVisited(true);    //flags this vertex
        for(Edge<T> e : edges) if (e.getState() == EDGESTATE.UNEXPLORED && (e.getA() == vertex || e.getB() == vertex)){
            e.setState(EDGESTATE.EXPLORED);
            hold.add(e);
        }
    }

    public Integer minSpanningTreeCost(T start){
        reset();
        LinkedList<Vertex<T>> path = minSpanningTree(start);
        int cost = 0;
        if(path == null) return null;
        for(Edge<T> edge : edges) if (edge.getState() == EDGESTATE.PATH) cost += edge.getWeight();
        return cost;
    }


    /**
     * Runtime : O(V)
     */
    private void reset(){
        for(Vertex<T> v : vertices) {
            v.setDistance(Integer.MAX_VALUE, null, null);
            v.setVisited(false);
        }
        for(Edge<T> e : edges) e.setState(EDGESTATE.UNEXPLORED);
    }

}
