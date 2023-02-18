package Algorithms;

import Graph.EDGESTATE;
import Graph.GraphInterface;
import Graph.DirectedGraph;
import Graph.DirectedGraph.Edge;
import Graph.DirectedGraph.Vertex;

import java.util.LinkedList;

/**
 * Bellman-Ford Path Finding Algorithm
 * @author Riberiko Niyomwungere
 * @version 1.0
 * @param <T>
 */
public class BellmanFord<T extends Comparable<T>> {

    DirectedGraph<T> graph;
    LinkedList<Vertex<T>> vertices;

    public BellmanFord(GraphInterface<T> graph){
        if(graph.getClass() == DirectedGraph.class){
            this.graph = (DirectedGraph<T>) graph;
            vertices = this.graph.getVertices();
        } else throw new IllegalArgumentException("The graph must be Directed");
    }


    public LinkedList<Vertex<T>> shortestPath(T from, T to){
        reset();

        Vertex<T> f = graph.getVertex(from);
        Vertex<T> t = graph.getVertex(to);

        if(f == null || t == null) return null; //when the vertices do not exist

        LinkedList<Vertex<T>> order = graph.getTopologicalOrder();  //gets the topological order
        if(order.size() == 0) return null;  //when the graph is empty
        order.get(0).setDistance(0, null, null);

        for(Vertex<T> v : order){   //going in topological order
            v.setVisited(true);
            for(Edge<T> e : v.getEdgeList()){   //all the current vertices edges
                e.setEdgeState(EDGESTATE.EXPLORED); //we have begun exporting them
                if(e.getTo().getDistance() > v.getDistance() + e.getWeight()){  //the current path to this node is better than the previous
                    if(e.getTo().getShortestEdge() != null) e.getTo().getShortestEdge().setEdgeState(EDGESTATE.RELAXED);    //when old edge exist set to relaxed
                    e.getTo().setDistance(v.getDistance() + e.getWeight(), v, e);   //sets new distances for the current vertex at the end of this edge
                    e.getTo().getShortestEdge().setEdgeState(EDGESTATE.PATH);   //flags this current edge as the best to get to this vertex so far
                }
                else e.setEdgeState(EDGESTATE.RELAXED); //when the old vertex is better, this edge will relax
            }
        }

        //Returns the path going from designation to beginning
        LinkedList<Vertex<T>> path = new LinkedList<>();
        Vertex<T> cur = t;

        do{
            path.addFirst(cur);
            cur = cur.getShortestVertex();
            if(cur == null) return null;
        }while(cur != f);
        path.addFirst(f);

        return path;
    }

    public Integer shortestPathCost(T from, T to){
        LinkedList<Vertex<T>> path = shortestPath(from, to);
        return path.getLast().getDistance() - path.getFirst().getDistance();
    }

    public void reset(){
        for(Vertex<T> v : vertices) {
            v.setDistance(Integer.MAX_VALUE, null, null);
            v.setVisited(false);
            for (Edge<T> e : v.getEdgeList()) e.setEdgeState(EDGESTATE.UNEXPLORED);
        }
    }

}
