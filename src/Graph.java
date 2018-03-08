import com.google.common.collect.ForwardingSortedMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.TreeMultiset;

import java.util.*;

public class Graph {

    static class EdgePropertyMap<T> {
        final HashMap<Integer, HashMap<Integer, T>> properties = new HashMap<>();
        final boolean directed;

        EdgePropertyMap(boolean directed) {
            this.directed = directed;
        }

        T get(int u, int v) {
            int f = u;
            int s = v;
            if (directed == false) {
                if (u <= v) {
                    f = u;
                    s = v;
                } else {
                    f = v;
                    s = u;
                }
            }

            HashMap<Integer, T> map = properties.get(f);
            if (map == null) {
                return null;
            }

            return map.get(s);
        }

        void put(int u, int v, T property) {
            int f = u;
            int s = v;
            if (directed == false) {
                if (u <= v) {
                    f = u;
                    s = v;
                } else {
                    f = v;
                    s = u;
                }
            }

            HashMap<Integer, T> map = properties.get(f);
            if (map == null) {
                map = new HashMap<>();
                properties.put(f, map);
            }

            map.put(s, property);
        }
    }

    static class Pair {
        final int first;
        final int second;
        Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    static class VertexHeap<T> {
        final TreeMap<T, Set<Integer>> heap;
        final HashMap<Integer, T> map;

        static class EmptyHeapException extends RuntimeException {
            public EmptyHeapException() {
                super("Empty heap!");
            }
        }

        VertexHeap() {
            this.heap = new TreeMap<>();
            this.map = new HashMap<>();
        }

        boolean isEmpty() {
            return map.isEmpty();
        }

        int size() {
            return map.size();
        }

        boolean contains(int vertex) {
            return map.containsKey(vertex);
        }

        int poll() {
            if (isEmpty()) throw new EmptyHeapException();

            T key = heap.keySet().iterator().next();
            Set<Integer> set = heap.get(key);
            int v = set.iterator().next();
            remove(v);
            return v;
        }

        void addOrUpdate(int vertex, T value) {
            remove(vertex);
            add(vertex, value);
        }

        void add(int vertex, T value) {
            map.put(vertex, value);

            Set<Integer> set = heap.get(value);
            if (set == null) {
                set = new HashSet<>();
                heap.put(value, set);
            }
            set.add(vertex);
        }

        void remove(int vertex) {
            T value = map.get(vertex);
            if (value == null) return;

            map.remove(vertex);

            Set<Integer> set = heap.get(value);
            set.remove(vertex);
            if (set.isEmpty()) {
                heap.remove(value);
            }
        }
    }

    static abstract class AbstractGraph {
        abstract boolean addEdge(int u, int v);
        abstract int getVertexCount();

        final boolean hasVertex(int v) {
            return 0 <= v && v < getVertexCount();
        }

        abstract boolean hasEdge(int u, int v);

        final Collection<Integer> getAdjacencies(int u) {
            LinkedList<Integer> adjacencies = new LinkedList<>();
            for (int v = 0; v < getVertexCount(); ++v) {
                if (hasEdge(u, v)) {
                    adjacencies.add(v);
                }
            }
            return Collections.unmodifiableCollection(adjacencies);
        }

        void addEdges(int[][] edges) {
            for (int[] each : edges) {
                addEdge(each[0], each[1]);
            }
        }

        abstract int[][] getEdges();

        interface Visitor {
            void visit(int prev, int curr);
        }

        void dfsRecurse(int prev, int curr, Visitor visitor, boolean[] visited) {
            visited[curr] = true;
            visitor.visit(prev, curr);

            Collection<Integer> adjacencies = getAdjacencies(curr);
            for (int v : adjacencies) {
                if (!visited[v]) {
                    dfsRecurse(curr, v, visitor, visited);
                }
            }
        }

        void dfs(Visitor visitor) {
            boolean[] visited = new boolean[getVertexCount()];
            for (int i = 0; i < visited.length; ++i) {
                if (!visited[i]) {
                    dfsRecurse(-1, i, visitor, visited);
                }
            }
        }

        void bfs(int start, Visitor visitor, boolean[] visited) {
            Queue<Integer> queue = new LinkedList<>();

            queue.add(start);
            visited[start] = true;
            visitor.visit(-1, start);

            while (!queue.isEmpty()) {
                int u = queue.poll();
                Collection<Integer> adjacencies = getAdjacencies(u);
                for (int v : adjacencies) {
                    if (visited[v]) continue;
                    visited[v] = true;
                    visitor.visit(u, v);
                    queue.add(v);
                }
            }
        }

        void bfs(Visitor visitor) {
            boolean[] visited = new boolean[getVertexCount()];
            for (int i = 0; i < getVertexCount(); ++i) {
                if (!visited[i]) {
                    bfs(i, visitor, visited);
                }
            }
        }

        int[] prim(EdgePropertyMap<Integer> distances, int start) {
            int[] parents = new int[getVertexCount()];
            Arrays.fill(parents, -1);

            int[] values = new int[getVertexCount()];
            Arrays.fill(values, Integer.MAX_VALUE);
            values[start] = 0;

            HashSet<Integer> Q = new HashSet<>();
            for (int i = 0; i < getVertexCount(); ++i) {
                Q.add(i);
            }

            PriorityQueue<Integer> minHeap = new PriorityQueue<>(Comparator.comparing(v -> values[v]));
            for (int i = 0; i < getVertexCount(); ++i) {
                minHeap.add(i);
            }

            while (!Q.isEmpty()) {
                int u = minHeap.poll();
                Q.remove(u);

                Collection<Integer> adjacencies = getAdjacencies(u);
                for (int v : adjacencies) {
                    int d = distances.get(u, v);
                    if (Q.contains(v) && d < values[v]) {
                        values[v] = d;
                        parents[v] = u;
                        minHeap.remove(v);
                        minHeap.add(v);
                    }
                }
            }

            return parents;
        }

        ArrayList<LinkedList<Integer>> dijktra(EdgePropertyMap<Integer> distances, int start) {
            int[] previous = new int[getVertexCount()];
            Arrays.fill(previous, -1);

            int[] length = new int[getVertexCount()];
            Arrays.fill(length, Integer.MAX_VALUE);
            length[start] = 0;

            HashSet<Integer> Q = new HashSet<>();
            for (int i = 0; i < getVertexCount(); ++i) {
                Q.add(i);
            }

            PriorityQueue<Integer> minHeap = new PriorityQueue<>(Comparator.comparing(v -> length[v]));
            for (int i = 0; i < getVertexCount(); ++i) {
                minHeap.add(i);
            }

            while (!Q.isEmpty()) {
                int u = minHeap.poll();
                Q.remove(u);

                Collection<Integer> adjacencies = getAdjacencies(u);
                for (int v : adjacencies) {
                    int l = length[u] + distances.get(u, v);
                    if (Q.contains(v) && l < length[v]) {
                        length[v] = l;
                        previous[v] = u;
                        minHeap.remove(v);
                        minHeap.add(v);
                    }
                }
            }

            ArrayList<LinkedList<Integer>> paths = new ArrayList<>(getVertexCount());
            for (int i = 0; i < getVertexCount(); ++i) {
                LinkedList<Integer> path = new LinkedList<>();
                int current = i;
                while (current != start) {
                    path.addFirst(current);
                    current = previous[current];
                }
                path.addFirst(start);
                paths.add(path);
            }

            return paths;
        }

        ArrayList<LinkedList<Integer>> dijktra2(EdgePropertyMap<Integer> distances, int start) {
            int[] previous = new int[getVertexCount()];
            Arrays.fill(previous, -1);

            int[] length = new int[getVertexCount()];
            Arrays.fill(length, Integer.MAX_VALUE);
            length[start] = 0;

            VertexHeap<Integer> Q = new VertexHeap<>();
            for (int i = 0; i < getVertexCount(); ++i) {
                Q.add(i, length[i]);
            }

            while (!Q.isEmpty()) {
                int u = Q.poll();
                Q.remove(u);

                Collection<Integer> adjacencies = getAdjacencies(u);
                for (int v : adjacencies) {
                    int l = length[u] + distances.get(u, v);
                    if (Q.contains(v) && l < length[v]) {
                        length[v] = l;
                        previous[v] = u;
                        Q.addOrUpdate(v, length[v]);
                    }
                }
            }

            ArrayList<LinkedList<Integer>> paths = new ArrayList<>(getVertexCount());
            for (int i = 0; i < getVertexCount(); ++i) {
                LinkedList<Integer> path = new LinkedList<>();
                int current = i;
                while (current != start) {
                    path.addFirst(current);
                    current = previous[current];
                }
                path.addFirst(start);
                paths.add(path);
            }

            return paths;
        }
    }



    static class GraphUsingMatrix extends AbstractGraph {
        final boolean[][] matrix;
        final boolean directed;

        GraphUsingMatrix(int vertexCount, boolean directed) {
            matrix = new boolean[vertexCount][vertexCount];
            this.directed = directed;
        }

        @Override
        boolean addEdge(int u, int v) {
            if (!hasVertex(u)) return false;
            if (!hasVertex(v)) return false;

            matrix[u][v] = true;

            if (!directed) {
                matrix[v][u] = true;
            }

            return true;
        }

        @Override
        int getVertexCount() {
            return matrix.length;
        }

        @Override
        boolean hasEdge(int u, int v) {
            if (!hasVertex(u)) return false;
            if (!hasVertex(v)) return false;
            return matrix[u][v];
        }

        @Override
        int[][] getEdges() {
            LinkedList<Pair> pairs = new LinkedList<>();

            if (directed) {
                for (int i = 0; i < getVertexCount(); ++i) {
                    for (int j = 0; j < getVertexCount(); ++j) {
                        if (hasEdge(i, j)) {
                            pairs.add(new Pair(i, j));
                        }
                    }
                }
            } else {
                for (int i = 0; i < getVertexCount() - 1; ++i) {
                    for (int j = i + 1; j < getVertexCount(); ++j) {
                        if (hasEdge(i, j)) {
                            pairs.add(new Pair(i, j));
                        }
                    }
                }
            }

            int[][] edges = new int[pairs.size()][2];
            int i = 0;
            for (Pair each : pairs) {
                edges[i][0] = each.first;
                edges[i][1] = each.second;
                ++i;
            }

            return edges;
        }
    }

    static class GraphUsingList extends AbstractGraph {
        static class Vertex {
            final Set<Integer> adjacencies = new HashSet<>();
        }

        final Vertex[] vertices;
        final boolean directed;

        GraphUsingList(int vertexCount, boolean directed) {
            vertices = new Vertex[vertexCount];

            // TODO: java에서는 배열 원소마다 new 필요하다. 잊지 말자.
            for (int i = 0; i < vertexCount; ++i) {
                vertices[i] = new Vertex();
            }

            this.directed = directed;
        }

        @Override
        boolean addEdge(int u, int v) {
            if (!hasVertex(u)) return false;
            if (!hasVertex(v)) return false;

            vertices[u].adjacencies.add(v);

            if (!directed) {
                vertices[v].adjacencies.add(u);
            }

            return true;
        }

        @Override
        int getVertexCount() {
            return vertices.length;
        }

        @Override
        boolean hasEdge(int u, int v) {
            if (!hasVertex(u)) return false;
            if (!hasVertex(v)) return false;
            return vertices[u].adjacencies.contains(v);
        }

        @Override
        int[][] getEdges() {
            LinkedList<Pair> pairs = new LinkedList<>();

            if (directed) {
                for (int i = 0; i < getVertexCount(); ++i) {
                    for (int j : getAdjacencies(i)) {
                        pairs.add(new Pair(i, j));
                    }
                }
            } else {
                HashSet<String> edges = new HashSet<>();
                for (int i = 0; i < getVertexCount(); ++i) {
                    for (int j : getAdjacencies(i)) {
                        int f = Math.min(i, j);
                        int s = Math.max(i, j);
                        String key = String.format("%d-%d", f, s);
                        if (edges.contains(key)) {
                            continue;
                        } else {
                            edges.add(key);
                            pairs.add(new Pair(i, j));
                        }
                    }
                }
            }

            int[][] edges = new int[pairs.size()][2];
            int i = 0;
            for (Pair each : pairs) {
                edges[i][0] = each.first;
                edges[i][1] = each.second;
                ++i;
            }

            return edges;
        }
    }

    void testGraph() {
        AbstractGraph graph = new GraphUsingList(9, false);
        {
            int[][] edges = {
                    {1, 2}, {1, 3}, {1, 4}, {2, 3}, {3, 4}, {3, 5}, {4, 6}, {4, 7}, {6, 7}, {6, 8}, {7, 8}
            };
            graph.addEdges(edges);
        }

        System.out.println("DFS:");
        graph.dfs((prev, curr) -> System.out.printf("(%d -> %d)", prev, curr));
        System.out.println();

        System.out.println("BFS:");
        graph.bfs((prev, curr) -> System.out.printf("(%d -> %d)", prev, curr));
        System.out.println();

        EdgePropertyMap<Integer> edgePropertyMap = new EdgePropertyMap<>(false);
        int[][] edges = graph.getEdges();
        for (int[] edge : edges) {
            edgePropertyMap.put(edge[0], edge[1], edge[0] + edge[1]);
        }

        System.out.println("Edge properties:");
        for (int[] edge : edges) {
            int property = edgePropertyMap.get(edge[0], edge[1]);
            System.out.printf("(%d + %d = %d)", edge[0], edge[1], property);
        }
    }

    static void testPrim() {
        AbstractGraph graph = new GraphUsingList(7, false);
        int[][] edges = {
                {0, 1},
                {0, 3},
                {1, 2},
                {1, 4},
                {2, 3},
                {2, 4},
                {2, 6},
                {4, 5},
                {5, 6}
        };
        graph.addEdges(edges);

        EdgePropertyMap<Integer> distances = new EdgePropertyMap<>(false);
        distances.put(0, 1, 8);
        distances.put(0, 3, 10);
        distances.put(1, 2, 9);
        distances.put(1, 4, 11);
        distances.put(2, 3, 5);
        distances.put(2, 4, 13);
        distances.put(2, 6, 12);
        distances.put(4, 5, 8);
        distances.put(5, 6, 7);

        int[] parents = graph.prim(distances, 0);
        for (int i = 0; i < parents.length; ++i) {
            System.out.printf("[%d <- %d]\n", parents[i], i);
        }
    }

    static void testDijkstra() {
        AbstractGraph graph = new GraphUsingList(7, false);
        int[][] edges = {
                {0, 1},
                {0, 3},
                {1, 2},
                {1, 4},
                {2, 3},
                {2, 4},
                {2, 6},
                {4, 5},
                {5, 6}
        };
        graph.addEdges(edges);

        EdgePropertyMap<Integer> distances = new EdgePropertyMap<>(false);
        distances.put(0, 1, 8);
        distances.put(0, 3, 10);
        distances.put(1, 2, 9);
        distances.put(1, 4, 11);
        distances.put(2, 3, 5);
        distances.put(2, 4, 13);
        distances.put(2, 6, 12);
        distances.put(4, 5, 8);
        distances.put(5, 6, 7);

        {
            ArrayList<LinkedList<Integer>> paths = graph.dijktra(distances, 0);
            for (LinkedList<Integer> path : paths) {
                System.out.println(path);
            }
        }

        {
            ArrayList<LinkedList<Integer>> paths = graph.dijktra2(distances, 0);
            for (LinkedList<Integer> path : paths) {
                System.out.println(path);
            }
        }

    }

    public static void main(String[] args) {
        testDijkstra();
    }
}
