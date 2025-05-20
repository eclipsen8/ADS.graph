import java.util.*;

public class DijkstraSearch<V> implements Search<V> {
    private Map<V, Double> distTo;
    private Map<V, V> edgeTo;
    private PriorityQueue<VertexDistance<V>> pq;

    private static class VertexDistance<V> implements Comparable<VertexDistance<V>> {
        V vertex;
        double distance;

        public VertexDistance(V vertex, double distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        @Override
        public int compareTo(VertexDistance<V> other) {
            return Double.compare(this.distance, other.distance);
        }
    }

    public DijkstraSearch(WeightedGraph<V> graph, V source) {
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        pq = new PriorityQueue<>();

        for (V v : graph.getVertices()) {
            distTo.put(v, Double.POSITIVE_INFINITY);
        }
        distTo.put(source, 0.0);
        pq.add(new VertexDistance<>(source, 0.0));

        while (!pq.isEmpty()) {
            relax(graph, pq.poll().vertex);
        }
    }

    private void relax(WeightedGraph<V> graph, V v) {
        for (V w : graph.getAdjacentVertices(v)) {
            double weight = graph.getWeight(v, w);
            if (distTo.get(w) > distTo.get(v) + weight) {
                distTo.put(w, distTo.get(v) + weight);
                edgeTo.put(w, v);
                pq.add(new VertexDistance<>(w, distTo.get(w)));
            }
        }
    }

    @Override
    public List<V> pathTo(V target) {
        if (!edgeTo.containsKey(target) && !distTo.get(target).equals(0.0))
            return Collections.emptyList();

        List<V> path = new LinkedList<>();
        for (V x = target; x != null && (x.equals(edgeTo.get(x)) || edgeTo.get(x) != null); x = edgeTo.get(x)) {
            path.add(0, x);
        }
        path.add(0, edgeTo.get(path.get(0)));
        return path;
    }
}