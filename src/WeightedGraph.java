import java.util.*;

public class WeightedGraph<V> {
    private Map<V, Vertex<V>> vertices = new HashMap<>();

    public void addVertex(V data) {
        vertices.putIfAbsent(data, new Vertex<>(data));
    }

    public void addEdge(V source, V dest, double weight) {
        Vertex<V> sourceVertex = vertices.get(source);
        Vertex<V> destVertex = vertices.get(dest);
        if (sourceVertex != null && destVertex != null) {
            sourceVertex.addAdjacentVertex(destVertex, weight);
            destVertex.addAdjacentVertex(sourceVertex, weight); // for undirected graph
        }
    }

    public boolean hasVertex(V data) {
        return vertices.containsKey(data);
    }

    public boolean hasEdge(V source, V dest) {
        if (!hasVertex(source) || !hasVertex(dest)) return false;
        return vertices.get(source).getAdjacentVertices().containsKey(vertices.get(dest));
    }

    public List<V> getAdjacentVertices(V data) {
        if (!hasVertex(data)) return Collections.emptyList();
        Vertex<V> vertex = vertices.get(data);
        List<V> adjacent = new ArrayList<>();
        for (Vertex<V> v : vertex.getAdjacentVertices().keySet()) {
            adjacent.add(v.getData());
        }
        return adjacent;
    }

    public double getWeight(V source, V dest) {
        if (!hasEdge(source, dest)) return Double.POSITIVE_INFINITY;
        return vertices.get(source).getAdjacentVertices().get(vertices.get(dest));
    }

    public Set<V> getVertices() {
        return vertices.keySet();
    }
}