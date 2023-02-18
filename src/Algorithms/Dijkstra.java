package Algorithms;

import Graph.EDGESTATE;
import Graph.GraphInterface;
import Graph.UndirectedGraph;
import Graph.UndirectedGraph.Edge;
import Graph.UndirectedGraph.Vertex;

import java.util.*;

/**
 * Dijkstra Path Finding Algorithm
 *
 * @author Riberiko Niyomwungere
 * @version 1.0
 * @param <T>
 */
public class Dijkstra <T extends Comparable<T>> {

    private final PriorityQueue<Edge<T>> hold;    //ensure that the best edge is always the next to come off

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

    private void ensureWeights(){   //ensures that no weights for the graph are under 1
        for (Edge<T> e : edges) if (e.getWeight() < 1) throw new IllegalStateException("There can be no weights below 1 for Dijkstra's Algorithm");
    }

    private void shortestPath(T from) {
        hold.clear();
        reset();
        ensureWeights();

        Vertex<T> current = graph.getVertex(from);  //starting point for the algorithm
        current.setDistance(0, null, null); //all the other vertices are infinite distance away, and our starting point is zero
        addEdges(current);  //adds all the edges at this vertex to our priority que

        while (!hold.isEmpty()){    //until our que is empty
            Edge<T> bestEdge = hold.poll(); //removes an edge from the que
            //finds a vertex that as not been visited in the edge set
            if(bestEdge.getA().getIsVisited()) current = bestEdge.getB();
            else current = bestEdge.getA();
            //adds all the edges of that vertex
            addEdges(current);
        }

    }

    private void addEdges(Vertex<T> v){
        if(v.getIsVisited()) return;    //if this vertex has already been visited, move on
        v.setVisited(true); //set this vertex to visited

        for(Edge<T> e : edges) if (e.getA() == v || e.getB() == v) {    //looks at all the edges and only operates on those that have the current vertex

            if(e.getState() == EDGESTATE.UNEXPLORED) {  //adds the edge to que only if we have not explored it yet
                hold.add(e);
                e.setState(EDGESTATE.EXPLORED); //sets it to explored, so we don't explore it again
            }else continue;

            Vertex<T> next;

            //finds the vertex in this edge that is not the vertex we came from
            if(v != e.getA()) next = e.getA();
            else next = e.getB();


            if(next.getDistance() > v.getDistance() + e.getWeight()){   //when the current path to this next vertex is better
                if(next.getShortestEdge() != null) next.getShortestEdge().setState(EDGESTATE.RELAXED);  //relax the old edge
                e.setState(EDGESTATE.PATH); //makes the new edge a short distance edge
                next.setDistance(v.getDistance() + e.getWeight(), v, e);    //since the new edge is discovered we know how far it is
            }
            else e.setState(EDGESTATE.RELAXED);  //relaxes this edge and

        }

    }

    public LinkedList<Vertex<T>> shortestPath(T from, T to) {
        shortestPath(from); //finds the distances of every vertex from the beginning vertex
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
        LinkedList<Vertex<T>> path = shortestPath(from, to);
        Vertex<T> f = path.getFirst();
        Vertex<T> t = path.getLast();

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
