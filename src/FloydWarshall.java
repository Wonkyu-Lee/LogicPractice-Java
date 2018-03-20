import java.util.*;

public class FloydWarshall {

    static class Pair {
        public final int s;
        public final int e;
        public Pair(int s, int e) {
            this.s = s;
            this.e = e;
        }

        @Override
        public boolean equals(Object o) {
            Pair other = (Pair)o;
            if (other == null) return false;
            if (this == other) return true;

            return s == other.s && e == other.e;
        }

        @Override
        public int hashCode() {
            int r = 17;
            r = 31 * r + s;
            r = 31 * r + e;
            return r;
        }

        @Override
        public String toString() {
            return String.format("(%d -> %d)", s, e);
        }
    }

    static class Path implements Cloneable {
        public final double distance;
        public final LinkedList<Integer> vertices;

        public Path(double distance, LinkedList<Integer> vertices) {
            this.distance = distance;
            this.vertices = vertices;
        }

        public Path(int v) {
            this.distance = 0;
            vertices = new LinkedList();
            vertices.add(v);
        }

        public static Path concat(Path p1, Path p2) {
            int v1 = p1.vertices.peekLast();
            int v2 = p2.vertices.peekFirst();

            if (v1 != v2)
                return null;

            LinkedList<Integer> vertices = new LinkedList();
            vertices.addAll(p1.vertices);

            Iterator<Integer> it = p2.vertices.iterator();
            it.next();
            while (it.hasNext()) {
                vertices.add(it.next());
            }

            return new Path(p1.distance + p2.distance, vertices);
        }

        @Override
        protected Object clone() {
            return new Path(distance, (LinkedList<Integer>)vertices.clone());
        }

        @Override
        public String toString() {
            return String.format("%s: distance = %.0f", vertices.toString(), distance);
        }
    }

    /**
     * Floyd-Warshall algorithm
     * @param n: vertex count
     * @param w: edge weights
     * @return paths from start to destination
     */
    static Map<Pair, Path> findAllShortestPaths(int n, Map<Pair, Double> w) {
        Map<Pair, Path> result = new HashMap();
        for (int i = 0; i < n; ++i) {
            result.put(new Pair(i, i), new Path(i));
        }

        double[][] d1 = new double[n][n];
        double[][] d2 = new double[n][n];

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                Pair edge = new Pair(i, j);
                if (i == j) {
                    d1[i][j] = 0;
                    result.put(new Pair(i, j), new Path(i));
                }
                else {
                    double weight = w.getOrDefault(edge, Double.POSITIVE_INFINITY);
                    d1[i][j] = weight;
                    LinkedList<Integer> list = new LinkedList<>();
                    list.add(i);
                    list.add(j);
                    result.put(new Pair(i, j), new Path(weight, list));
                }
            }
        }

        for (int k = 0; k < n; ++k) {
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    d2[i][j] = d1[i][j];
                    if (d2[i][j] > d1[i][k] + d1[k][j]) {
                        d2[i][j] = d1[i][k] + d1[k][j];
                        Path i2k = result.get(new Pair(i, k));
                        Path k2j = result.get(new Pair(k, j));
                        Path i2j = Path.concat(i2k, k2j);
                        result.put(new Pair(i, j), i2j);
                    }
                }
            }

            double[][] t = d1;
            d1 = d2;
            d2 = t;
        }

        return result;
    }
    
    public static void main(String[] args) {
        int n = 8;
        Map<Pair, Double> w = new HashMap();
        w.put(new Pair(0, 3), 10.0);
        w.put(new Pair(1, 0), 8.0);
        w.put(new Pair(1, 2), 9.0);
        w.put(new Pair(1, 4), 11.0);
        w.put(new Pair(2, 0), -15.0);
        w.put(new Pair(2, 3), 1.0);
        w.put(new Pair(2, 4), 3.0);
        w.put(new Pair(3, 7), 2.0);
        w.put(new Pair(4, 5), 8.0);
        w.put(new Pair(4, 6), 8.0);
        w.put(new Pair(5, 6), -7.0);
        w.put(new Pair(6, 7), 5.0);
        w.put(new Pair(7, 5), 4.0);

        Map<Pair, Path> allPaths = findAllShortestPaths(n, w);

        for (Pair pair : allPaths.keySet()) {
            Path path = allPaths.get(pair);
            if (path.distance == 0 || Double.isInfinite(path.distance))
                continue;

            System.out.println(pair + ": " + allPaths.get(pair));
        }
    }
}
