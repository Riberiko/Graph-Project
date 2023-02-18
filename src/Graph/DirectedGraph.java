package Graph;

import java.util.LinkedList;

/**
 * Graph Theory Directed Graphs that with the ability to have weighted edges
 *
 * @param <T>   The type for the graph
 * @author Riberiko Niyomwungere
 * @version 1.0
 */
public class DirectedGraph<T extends Comparable<T>> implements GraphInterface<T> {

    private final LinkedList<Vertex<T>> vertices;

    /**
     * Creates a Directed Graph with no vertices or edges
     */
    public DirectedGraph(){
        vertices = new LinkedList<>();
    }

    /**
     * Adds creates a vertex in the graph that can be identified using the data argument
     * @param data  The label and or data saved in the vertex to be created
     */
    public void addVertex(T data){
        ensureUnique(data);
        vertices.addLast(new Vertex<>(data));
    }

    /**
     * Removes the vertex in the graph with the data field of the argument passed in
     * @param data  The label and or data saved in the vertex to be deleted
     */
    public void removeVertex(T data){
        for(Vertex<T> v : vertices){
            v.removeEdge(data);
            if(v.getData() == data) {
                vertices.remove(v);
            }
        }
    }

    /**
     * Sets the weight for the edge
     * @param from  the start vertex data and or label
     * @param to    the end vertex data and or label
     * @param weight    the desired weight to be changed to
     */
    public void setEdgeWeight(T from, T to, int weight){
        for(Vertex<T> v : vertices){
            if(v.getData() == from){
                v.setWeight(to,weight);
                return;
            }
        }
    }

    /**
     * Creates an edge with a default weight of 1
     * @param from  the vertex data and or label
     * @param to    the vertex data and or label
     * @return  true only when the vertex was successfully added
     */
    public boolean addEdge(T from, T to){
        return addEdge(from, to, 1);
    }

    /**
     * Adds an edge with a specified weight
     * @param from  the vertex data and or label
     * @param to    the vertex data and or label
     * @param weight    the desired weight
     * @return  true only when the vertex was successfully added
     */
    public boolean addEdge(T from, T to, int weight){
        Vertex<T> f = null;
        Vertex<T> t = null;

        for(Vertex<T> v : vertices){

            if(v.getData() == from) f = v;
            if(v.getData() == to) t = v;
            if(f != null && t != null) {
                if(v.getEdge(to) != null) throw new IllegalArgumentException("The edge you are attempting to create already exists");
                f.addEdge(t.getData(), weight);
                return true;
            }
        }
        return false;
    }


    /**
     * Ensures that there is no existing vertex in the graph with that data and or label
     * @param data  to look for
     */
    private void ensureUnique(T data){
        for(Vertex<T> v : vertices){
            if(v.getData() == data) throw new IllegalArgumentException("A vertex with that value already exists");
        }
    }

    /**
     * Removes the edge from the graph
     * @param from  the start vertex data and or label
     * @param to    the end vertex data and or label
     */
    public void removeEdge(T from, T to){
        for(Vertex<T> v : vertices){
            if(v.getData() == from){
                v.removeEdge(to);
            }
        }
    }

    /**
     * Retrieves all the vertices in the graph
     * @return  the vertices
     */
    public LinkedList<Vertex<T>> getVertices(){
        return vertices;
    }

    /**
     * Retrieves the vertex in this graph that contains that data and or label
     * @param data  to look for
     * @return  the vertex found, can be null
     */
    public Vertex<T> getVertex(T data){
        for(Vertex<T> v : vertices){
            if(v.getData() == data) return v;
        }
        return null;
    }

    @Override
    public String toString(){
        String str = "";
        str += "Edges in " + ConsoleColors.ANSI_RED + "Red" + ConsoleColors.ANSI_RESET + " are " + ConsoleColors.ANSI_RED +"Relaxed"+ConsoleColors.ANSI_RESET+", " +
                ConsoleColors.ANSI_GREEN+"Green" + ConsoleColors.ANSI_RESET+ " are in the " +ConsoleColors.ANSI_GREEN+ "Path" + ConsoleColors.ANSI_RESET +", " +
                ConsoleColors.ANSI_BLUE+"Blue" +ConsoleColors.ANSI_RESET+ " have been " + ConsoleColors.ANSI_BLUE + "Explored" + ConsoleColors.ANSI_RESET + " and " +
                ConsoleColors.ANSI_YELLOW + "Yellow" + ConsoleColors.ANSI_RESET + " have " + ConsoleColors.ANSI_YELLOW + "not been Explored\n"+ConsoleColors.ANSI_RESET;
        for(Vertex<T> v : vertices){
            str += v;
            if(v.getEdgeList().size() > 0) {
                str += "\n\t\tThis Vertex has " + v.getEdgeList().size() + " edge(s)\n";
                for(Edge<T> e : v.getEdgeList()){
                    str += "\t\t" + e + ")\n";
                }
            }
            else str += "\n\t\tThis Vertex had no edges\n";

        }
        return str;
    }


    /**
     * Vertex Representation specific to the Directed Graph
     *
     * @param <T>   The type for the vertex
     */
    public static class Vertex <T extends Comparable<T>> implements Comparable<Vertex<T>> {

        private T data;
        private final LinkedList<Edge<T>> edgeList;

        private int distance;
        private boolean isVisited;

        private Edge<T> shortestEdge;
        private Vertex<T> shortestVertex;

        /**
         * Creates a Vertex
         * @param data the data and or label for this vertex
         */
        public Vertex(T data) {
            this.data = data;
            edgeList = new LinkedList<>();
            //weightList = new ArrayList<>();
            distance = Integer.MAX_VALUE;
            isVisited = false;
        }

        /**
         * Retrieves the data and or label for this vertex
         * @return  the data and or label
         */
        public T getData() {
            return data;
        }

        /**
         * Sets teh data and or label for this vertex
         * @param data  the desired new data and or label
         */
        public void setData(T data) {
            this.data = data;
        }

        /**
         * Creates an edge from this edge to the specified edge with a default weight of 1
         * @param data  the data and or label for the specified edge
         */
        public void addEdge(T data) {
            addEdge(data, 1);
        }

        /**
         * Creates an edge from this edge to the specified edge with a desired weight
         * @param data  the data and or label for the specified edge
         * @param weight    the desired weight
         */
        public void addEdge(T data, int weight) {
            if (data == this.data) throw new IllegalArgumentException("You can not make an edge to yourself");
            edgeList.addLast(new Edge<>(this.data, data, weight));
        }

        /**
         * Removes the specified edge from this vertex
         * @param data  the data and or label for the desired edge
         * @return  true only when an edge was removed
         */
        public boolean removeEdge(T data) {
            for(Edge<T> e : edgeList) {
                if (e.getTo() == data) {
                    edgeList.remove(e);
                    return true;
                }
            }
            return false;
        }

        /**
         * Sets the distance to this vertex and also what vertex was used to get to this vertex
         * @param distance  the distance
         * @param shortestVertex  the vertex that got us here
         * @param shortestEdge  the edge that got us here
         */
        public void setDistance(int distance, Vertex<T> shortestVertex, Edge<T> shortestEdge) {
            this.distance = distance;
            this.shortestEdge = shortestEdge;
            this.shortestVertex = shortestVertex;
        }

        /**
         * Retrieves the distance to this vertex
         * @return  the distance
         */
        public int getDistance() {
            return distance;
        }

        /**
         * Retrieves the edge that got us here | only when an algorithm is used to find the shortest path
         * @return  the edge | can be null
         */
        public Edge<T> getShortestFrom() {
            return shortestEdge;
        }

        /**
         * Retrieves the vertex that got us here | only when an algorithm is used to find the shortest path
         * @return  the vertex | can be null
         */
        public Vertex<T> getShortestVertex(){
            return shortestVertex;
        }

        /**
         * Sets the weight for the edge from this vertex to the specified one
         * @param data  the data and or label for the desired vertex
         * @param weight    the desired weight
         */
        public void setWeight(T data, int weight) {
            for(Edge<T> e : edgeList) {
                if (e.getTo() == data) {
                    e.setWeight(weight);
                    break;
                }
            }
        }

        /**
         * Retrieves the weight of the edge form this vertex to the specified one
         * @param data  the data and or label for the desired Vertex
         * @return  the weight of that edge | can be null when edge doesn't exist
         */
        public Integer getWeightTo(T data) {
            for(Edge<T> e : edgeList) if (e.getTo() == data) return e.getWeight();
            return null;
        }

        /**
         * Flags the vertex as visited or not visited
         * @param isVisited true when visited
         */
        public void setVisited(boolean isVisited) {
            this.isVisited = isVisited;
        }

        /**
         * Retrieves the status of if we have visited this Vertex
         * @return  true when we have visited
         */
        public boolean getIsVisited(){
            return isVisited;
        }

        /**
         * Retrieves the edge list for this Vertex
         * @return  the list
         */
        public LinkedList<Edge<T>> getEdgeList() {
            return edgeList;
        }

        /**
         * Retrieves the edge from this vertex to the specified Vertex
         * @param to    the data and or label of the specified Vertex
         * @return  the Edge when found | can be null
         */
        public Edge<T> getEdge(T to) {
            for(Edge<T> e : edgeList) if (e.getTo() == to) return e;
            return null;
        }

        @Override
        public String toString() {
            return  "Vertex (" + ((isVisited) ? ConsoleColors.ANSI_BLUE : ConsoleColors.ANSI_YELLOW) + data.toString() + ConsoleColors.ANSI_RESET + ")";
        }

        @Override
        public int compareTo(Vertex<T> v) {
            return data.compareTo(v.getData());
        }
    }


    /**
     * Edge Representation specific to the Directed Graph
     * @param <T>   the type for the edge
     */
    public static class Edge<T extends Comparable<T>> implements Comparable<Edge<T>> {
        private final T from;
        private final T to;
        private int weight;

        private EDGESTATE state;

        /**
         * Creates an edge with default weight of 1
         * @param from  the data and or label of the desired vertex
         * @param to    the data and or label of the desired vertex
         */
        public Edge(T from, T to){
            this(from, to, 1);
        }

        /**
         * Creates an Edge with specified weight
         * @param from  the data and or label of the desired vertex
         * @param to    the data and or label fo the desired vertex
         * @param weight    the desired weight for this edge
         */
        public Edge(T from, T to, int weight){
            this.from = from;
            this.to = to;
            this.weight = weight;
            state = EDGESTATE.UNEXPLORED;
        }

        /**
         * Retrieves the data and or label of the vertex it points from
         * @return  the data and or label of the vertex it points form
         */
        public T getFrom(){
            return from;
        }

        /**
         * Retrieves the data dna dor label of the vertex it points to
         * @return  the data and or label of the vertex it points to
         */
        public T getTo(){
            return to;
        }

        /**
         * The weight of this edge
         * @return  the weight of this edge
         */
        public int getWeight(){
            return weight;
        }

        /**
         * Sets the weight for this edge
         * @param weight    the desired edge
         */
        public void setWeight(int weight){
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge edge){
            return Integer.compare(weight, edge.getWeight());
        }

        @Override
        public String toString(){
            if(state == EDGESTATE.PATH) return ConsoleColors.ANSI_GREEN +from +" -( "+weight+" )-> " + to+ConsoleColors.ANSI_RESET;
            if(state == EDGESTATE.RELAXED) return ConsoleColors.ANSI_RED +from +" -( "+weight+" )-> " + to+ConsoleColors.ANSI_RESET;
            if(state == EDGESTATE.EXPLORED) return ConsoleColors.ANSI_BLUE +from +" -( "+weight+" )-> " + to+ConsoleColors.ANSI_RESET;
            if(state == EDGESTATE.UNEXPLORED) return ConsoleColors.ANSI_YELLOW +from +" -( "+weight+" )-> " + to+ConsoleColors.ANSI_RESET;
            return "";
        }

    }

}
