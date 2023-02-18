package Graph;

import java.util.LinkedList;

/**
 * Graph Theory Directed Graphs that with the ability to have weighted edges
 *
 * @param <T>   The type for the graph
 * @author Riberiko Niyomwungere
 * @version 1.0
 */
public class UndirectedGraph<T extends Comparable<T>> implements GraphInterface<T> {

    private final LinkedList<Vertex<T>> vertices;

    private final LinkedList<Edge<T>> edgeLinkedList;

    /**
     * Creates a Directed Graph with no vertices or edges
     */
    public UndirectedGraph(){
        vertices = new LinkedList<>();
        edgeLinkedList = new LinkedList<>();
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
        vertices.removeIf(v -> v.getData() == data);
        edgeLinkedList.removeIf(e -> (e.getB() == data || e.getA() == data) );
    }

    /**
     * Sets the weight for the edge
     * @param a  the start vertex data and or label
     * @param b    the end vertex data and or label
     * @param weight    the desired weight to be changed to
     */
    public void setEdgeWeight(T a, T b, int weight){
        for(Edge<T> e : edgeLinkedList){
            if((e.getA().getData() == a && e.getB().getData() == b) || (e.getB().getData() == a && e.getA().getData() == b)) {
                e.setWeight(weight);
                return;
            }
        }
    }

    /**
     * Creates an edge with a default weight of 1
     * @param a  the vertex
     * @param b    the other vertex
     * @return  true only when the vertex was successfully added
     */
    public boolean addEdge(Vertex<T> a, Vertex<T> b){
        return addEdge(a, b, 1);
    }

    /**
     * Adds an edge with a specified weight
     * @param a  the vertex
     * @param b the other vertex
     * @param weight    the desired weight
     * @return  true only when the vertex was successfully added
     */
    public boolean addEdge(Vertex<T> a, Vertex<T> b, int weight){
        if(!ensureUniqueEdge(a.data, b.getData())) throw new IllegalArgumentException("The edge you are attempting to create already exists");
        edgeLinkedList.add(new Edge<>(a,b, weight));
        return false;
    }

    /**
     * Adds an edge with a weight of 1
     * @param a the vertex data and or label
     * @param b the vertex data and or label
     * @return true when edge added to graph
     */
    public boolean addEdge(T a, T b){
        return addEdge(a,b, 1);
    }

    /**
     * Adds an edge with a specified weight
     * @param a the vertex data and or label
     * @param b the vertex data and or label
     * @param weight    of the edge
     * @return true when added to graph
     */
    public boolean addEdge(T a, T b, int weight){
        Vertex<T> aT = null;
        Vertex<T> bT = null;

        for(Vertex<T> v : vertices){
            if(v.getData() == a) aT = v;
            if(v.getData() == b) bT = v;
            if(aT != null && bT != null) break;
        }
        if(aT != null && bT != null) {
            addEdge(aT, bT, weight);
            return true;
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
     * Checks to see if two vertices are already connected via edge
     * @param a the data and or label of one vertex
     * @param b the data and or label of the second vertex
     * @return  true if that edge does not already exist
     */
    public boolean ensureUniqueEdge(T a, T b){
        for(Edge<T> e : edgeLinkedList) if((e.getA().getData() == a && e.getB().getData() == b) || (e.getB().getData() == a && e.getA().getData() == b)) return false;
        return true;
    }

    /**
     * Removes the edge from the graph
     * @param a  the start vertex data and or label
     * @param b    the end vertex data and or label
     */
    public void removeEdge(T a, T b){
        edgeLinkedList.removeIf(e -> ((e.getA() == a && e.getB() == b) || (e.getA() == b && e.getA() == a) ));
    }

    /**
     * Retrieves all the vertices in the graph
     * @return  the vertices
     */
    public LinkedList<Vertex<T>> getVertices(){
        return vertices;
    }

    /**
     * Retrieves all teh edges in the graph
     * @return  all edges
     */
    public LinkedList<Edge<T>> getEdges(){
        return edgeLinkedList;
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
        str += "This Graph has "+edgeLinkedList.size()+" Undirected Edge(s)\n";
        for(Edge<T> e : edgeLinkedList) str+="\t\t"+e+"\n";
        return str;
    }


    /**
     * Vertex Representation specific to the Undirected Graph
     *
     * @param <T>   The type for the vertex
     */
    public static class Vertex <T extends Comparable<T>> implements Comparable<Vertex<T>> {

        private T data;

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
         * Sets the distance to this vertex and also what vertex was used to get to this vertex
         * @param distance  the distance
         * @param shortestEdge  the edge that got us here
         * @param shortestVertex    the vertex that got us here
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
        public Edge<T> getShortestEdge() {
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
        private final Vertex<T> a;
        private final Vertex<T> b;
        private int weight;

        private EDGESTATE state;

        /**
         * Creates an Edge with specified weight
         * @param a  the data and or label of the desired vertex
         * @param b    the data and or label fo the desired vertex
         * @param weight    the desired weight for this edge
         */
        public Edge(Vertex<T> a, Vertex<T> b, int weight){
            this.a = a;
            this.b = b;
            this.weight = weight;
            state = EDGESTATE.UNEXPLORED;
        }

        /**
         * Retrieves the data and or label of the vertex it points from
         * @return  the data and or label of the vertex it points form
         */
        public Vertex<T> getA(){
            return a;
        }

        /**
         * Retrieves the data dna dor label of the vertex it points to
         * @return  the data and or label of the vertex it points to
         */
        public Vertex<T> getB(){
            return b;
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

        public void setState(EDGESTATE state){
            this.state = state;
        }

        public EDGESTATE getState(){
            return state;
        }

        @Override
        public int compareTo(Edge edge){
            return Integer.compare(weight, edge.getWeight());
        }

        @Override
        public String toString(){
            String str = "";

            if(state == EDGESTATE.RELAXED) str = ConsoleColors.ANSI_RED +" <-( "+weight+" )-> ";
            if(state == EDGESTATE.PATH) str = ConsoleColors.ANSI_GREEN +" <-( "+weight+" )-> ";
            if(state == EDGESTATE.EXPLORED) str = ConsoleColors.ANSI_BLUE +" <-( "+weight+" )-> ";
            if(state == EDGESTATE.UNEXPLORED) str = ConsoleColors.ANSI_YELLOW +" <-( "+weight+" )-> ";

            return a + str + ConsoleColors.ANSI_RESET + b;
        }

    }

}
