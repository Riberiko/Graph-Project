package Graph;

public interface GraphInterface<T> {

    public void removeVertex(T v);

    public void addVertex(T v);

    public void removeEdge(T a, T b);

    public boolean addEdge(T a, T b);

    public void setEdgeWeight(T a, T b, int weight);

}
