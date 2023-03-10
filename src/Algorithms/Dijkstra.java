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

    private final PriorityQueue<Vertex<T>> hold;    //ensure that the closest vertex comes next

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

    /**
     * Runtime : O(E)
     */
    private void ensureWeights(){   //ensures that no weights for the graph are under 1
        for (Edge<T> e : edges) if (e.getWeight() < 1) throw new IllegalStateException("There can be no weights below 1 for Dijkstra's Algorithm");
    }

    /**
     * Solves for the shortest path using the dijkstra algorithm
     *
     * Runtime : O( (V+E) log(V) )
     *
     * @param from  the starting vertex
     */
    private void shortestPath(Vertex<T> from) {
        hold.clear();
        reset();
        ensureWeights();

        from.setDistance(0, null, null); //all the other vertices are infinite distance away, and our starting point is zero
        from.setVisited(true);
        hold.add(from);

        Vertex<T> current;
        Vertex<T> next;
        //O(n) all vertex
        while(!hold.isEmpty()){
            current = hold.poll();
            current.setVisited(true);
            //O(m) all edges
            for(Edge<T> e : edges) if (e.getA() == current || e.getB() == current){

                if (e.getA() == current)  next = e.getB(); else next = e.getA();    //ensure the next vertex is not the one we were just looking at

                if(e.getState() != EDGESTATE.UNEXPLORED) continue;  //we only are interested in edges we haven't seen

                e.setState(EDGESTATE.EXPLORED);

                //current path is better than old
                if(current.getDistance()+e.getWeight() < next.getDistance()){
                    if(next.getShortestEdge() != null) next.getShortestEdge().setState(EDGESTATE.RELAXED);  //in the event that
                    e.setState(EDGESTATE.PATH);
                    next.setDistance(current.getDistance()+e.getWeight(), current, e);
                }
                else e.setState(EDGESTATE.RELAXED); //old path is better


                //only adds next once if we haven't seen it
                if (!next.getIsVisited()) hold.add(next);
                next.setVisited(true);
            }

        }
    }

    /**
     * Solves for the shortest path using the dijkstra algorithm
     * @param from  starting vertex label
     * @param to    ending vertex label
     * @return  the path from to finish
     */
    public LinkedList<Vertex<T>> shortestPath(T from, T to) {

        Vertex<T> cur = graph.getVertex(to);
        Vertex<T> start = graph.getVertex(from);

        if(cur == null || start == null) return null;

        shortestPath(start); //finds the distances of every vertex from the beginning vertex
        LinkedList<Vertex<T>> path = new LinkedList<>();

        do{
            path.addFirst(cur);
            cur = cur.getShortestVertex();
        }while(cur != null && cur.getShortestVertex() != start);
        if (cur != null && cur.getShortestVertex() == start) path.addFirst(start);  //Resolves fence post issue

        return (path.getFirst() == start) ? path : null;    //only returns the path when one exists
    }

    public Integer shortestPathCost(T from, T to){
        LinkedList<Vertex<T>> path = shortestPath(from, to);
        if(path == null) return null;
        return path.getLast().getDistance();
    }

    /**
     * Resets the graph
     *
     * Runtime : O(n)
     */
    public void reset(){
        for(Vertex<T> v : vertices) {
            v.setDistance(Integer.MAX_VALUE, null, null);
            v.setVisited(false);
        }
        for(Edge<T> e : edges) e.setState(EDGESTATE.UNEXPLORED);
    }
}
