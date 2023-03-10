package Algorithms;

import Graph.DirectedGraph;
import Graph.DirectedGraph.Edge;
import Graph.DirectedGraph.Vertex;
import Graph.EDGESTATE;
import Graph.GraphInterface;

import java.util.LinkedList;
public class BellmanFord<T extends Comparable<T>> {

    private final LinkedList<Vertex<T>> vertices;
    private final DirectedGraph<T> graph;

    /**
     * Bellman Ford algorithm implementation
     * @param graph
     */
    public BellmanFord(GraphInterface<T> graph){
        if(graph.getClass() != DirectedGraph.class) throw new IllegalArgumentException("The graph must be Directed");
        this.graph = (DirectedGraph<T>) graph;
        vertices = this.graph.getVertices();
    }


    /**
     * Finds the shortest path from vertex start label to vertex end label
     *
     * Runtime : O(V + E)
     *
     * @param from
     * @param to
     * @return
     */
    public LinkedList<Vertex<T>> shortestPath(T from, T to){

        LinkedList<Vertex<T>> path = new LinkedList<>();
        Vertex<T> cur = graph.getVertex(to);
        Vertex<T> start = graph.getVertex(from);

        if(cur == null || start == null) return null;

        boolean isComplete = false;
        reset();

        start.setDistance(0, null, null);
        start.setVisited(true);

        while(!isComplete) {
            isComplete = true;

            for (Vertex<T> v : vertices) if (v.getIsVisited()) {
                for (Edge<T> e : v.getEdgeList()) if (e.getEdgeState() == EDGESTATE.UNEXPLORED) {   //for all edges of the current vertex
                    Vertex<T> next = e.getTo(); //the edge it is pointing to
                    e.setEdgeState(EDGESTATE.EXPLORED);
                    if (next.getDistance() > v.getDistance() + e.getWeight()) {   //if new path is better
                        if (next.getShortestEdge() != null)
                            next.getShortestEdge().setEdgeState(EDGESTATE.RELAXED);  //old edge relaxed
                        next.setDistance(v.getDistance() + e.getWeight(), v, e);    //saves current path
                        next.getShortestEdge().setEdgeState(EDGESTATE.PATH);    //new path is set as the current best path
                        isComplete = false; //a new loop will need to be done
                        next.setVisited(true);
                    } else if (next.getDistance() < v.getDistance() + e.getWeight())
                        e.setEdgeState(EDGESTATE.RELAXED); //old path is better
                }
            }
        }

        do{
           path.addFirst(cur);
           cur = cur.getShortestVertex();   //back tracking to
        }while(cur != start && cur != null);
        if(cur != null) path.addFirst(cur);

        return (path.getFirst() == start) ? path : null;    //returns the list only if it found the start vertex
    }

    /**
     * Solves for the cost of the graph path from start to finish
     *
     * Runtime O(V + E)
     *
     * @param start vertex start label
     * @param end   vertex end label
     * @return  path cost
     */
    public Integer shortestPathCost(T start, T end){
        return (shortestPath(start, end) != null) ? graph.getVertex(end).getDistance() - graph.getVertex(start).getDistance()  : null;
    }

    /**
     * Runtime : O(V + E)
     */
    public void reset(){
        for(Vertex<T> v : vertices){
            for(Edge<T> e : v.getEdgeList()){
                e.setEdgeState(EDGESTATE.UNEXPLORED);
            }
            v.setDistance(Integer.MAX_VALUE, null, null);
            v.setVisited(false);
        }
    }
}
