package Algorithms;

import Graph.EDGESTATE;
import Graph.GraphInterface;
import Graph.UndirectedGraph;
import Graph.UndirectedGraph.Edge;
import Graph.UndirectedGraph.Vertex;

import java.util.*;

public class Dijkstra <T extends Comparable<T>> {

    private PriorityQueue<Edge<T>> hold;

    UndirectedGraph<T> graph;
    LinkedList<Vertex<T>> vertices;
    LinkedList<Edge<T>> edges;

    public Dijkstra(GraphInterface<T> graph){
        if (graph.getClass() == UndirectedGraph.class){
            this.graph = (UndirectedGraph<T>) graph;
            hold = new PriorityQueue<>();
            vertices = this.graph.getVertices();
            edges = this.graph.getEdges();
        }else throw new IllegalArgumentException("The graph must be Undirected");
    }

    private void ensureWeights(){
        for (Edge<T> e : edges) if (e.getWeight() < 1) throw new IllegalStateException("There can be no weights below 1 for Dijkstra's Algorithm");
    }

    private void shortestPath(T from) {
        hold.clear();
        reset();
        ensureWeights();

        Vertex<T> current = graph.getVertex(from);
        current.setDistance(0, null, null);
        addEdges(current);

        while (!hold.isEmpty()){
            Edge<T> bestEdge = hold.poll();
            if(bestEdge.getA().getIsVisited()) current = bestEdge.getB();
            else current = bestEdge.getA();
            addEdges(current);
        }

    }

    public void addEdges(Vertex<T> v){
        if(v.getIsVisited()) return;
        v.setVisited(true);

        for(Edge<T> e : edges) if (e.getA() == v || e.getB() == v) {

            if(e.getState() == EDGESTATE.UNEXPLORED) {
                hold.add(e);
                e.setState(EDGESTATE.EXPLORED);
            }else continue;

            Vertex<T> next = null;

            if(v != e.getA()) next = e.getA();
            else next = e.getB();


            if(next.getDistance() > v.getDistance() + e.getWeight()){
                if(next.getShortestEdge() != null) next.getShortestEdge().setState(EDGESTATE.RELAXED);  //relax the old edge
                e.setState(EDGESTATE.PATH); //makes the new edge a short distance edge
                next.setDistance(v.getDistance() + e.getWeight(), v, e);    //since the new edge is discovered we know how far it is
            }
            else e.setState(EDGESTATE.RELAXED);  //relaxes this edge and

        }

    }

    public LinkedList<Vertex<T>> shortestPath(T from, T to) {
        shortestPath(from);
        LinkedList<Vertex<T>> path = new LinkedList<>();

        Vertex<T> current =  graph.getVertex(to);
        path.add(current);

        while(current != null){
            path.addFirst(current.getShortestVertex());
            current = current.getShortestVertex();
        }
        path.remove(0);

        return (path.get(0).getData() == from) ? path : null;
    }

    public String shortestPathCost(T from, T to){
        shortestPath(from, to);
        Vertex<T> f = graph.getVertex(from);
        Vertex<T> t = graph.getVertex(to);

        return (t.getDistance() - f.getDistance() < Integer.MAX_VALUE) ? Integer.toString(t.getDistance()-f.getDistance()) : "INFINITY";
    }

    public void reset(){
        for(Vertex<T> v : vertices) {
            v.setDistance(Integer.MAX_VALUE, null, null);
            v.setVisited(false);
        }
        for(Edge<T> e : edges) e.setState(EDGESTATE.UNEXPLORED);
    }
}
