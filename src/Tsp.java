import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Tsp {

    // input
    private int[][] weights;
    private int nodeCount;
    private int[] minOutWeights;

    // tracking
    private int minCost = Integer.MAX_VALUE;
    private LinkedList<Integer> minCostPath;
    private HashSet<Integer> visited = new HashSet<>();
    private LinkedList<Integer> curPath = new LinkedList<>();

    public Tsp(int[][] weights) {
        this.weights = weights;
        nodeCount = weights.length;
        minOutWeights = new int[nodeCount];

        for (int i = 0; i < nodeCount; ++i) {
            minOutWeights[i] = Integer.MAX_VALUE;
            for (int j = 0; j < nodeCount; ++j) {
                if (i != j) {
                    minOutWeights[i] = Math.min(minOutWeights[i], weights[i][j]);
                }
            }
        }

        int costFromStart = 0;
        int costToEnd = 0;
        for (int i = 0; i < nodeCount; ++i) {
            costToEnd += minOutWeights[i];
        }

        visit(0, costFromStart, costToEnd);
    }

    private void visit(int u, int costFromStart, int costToEnd) {
        if (visited.contains(u)) {
            return;
        }

        if (!curPath.isEmpty()) {
            int last = curPath.getLast();
            costFromStart += weights[last][u];
            costToEnd -= minOutWeights[last];
            if (costFromStart + costToEnd >= minCost) {
                return;
            }
        }

        visited.add(u);
        curPath.add(u);

        if (visited.size() == nodeCount) {
            int cost = costFromStart + weights[u][0];
            if (cost < minCost) {
                minCost = cost;

                LinkedList<Integer> path = new LinkedList<>(curPath);
                path.add(0);
                minCostPath = path;
            }

            return;
        }

        final int _costFromStart = costFromStart;
        final int _costToEnd = costToEnd;
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer v1, Integer v2) {
                return lowerBound(v1) - lowerBound(v2);
            }

            int lowerBound(int v) {
                int newCostFromStart = _costFromStart + weights[u][v];
                int newCostToEnd = _costToEnd - minOutWeights[u];
                return newCostFromStart + newCostToEnd;
            }
        });

        for (int v = 0; v < nodeCount; ++v) {
            if (!visited.contains(v)) {
                minHeap.add(v);
            }
        }

        while (!minHeap.isEmpty()) {
            int v = minHeap.poll();
            visit(v, costFromStart, costToEnd);
        }

        visited.remove(u);
        curPath.removeLast();
    }

    public int getMinimumCost() {
        return minCost;
    }

    public LinkedList<Integer> getMinimumCostPath() {
        return minCostPath;
    }

    @Override
    public String toString() {
        return String.format("Length: %d, Path: %s", getMinimumCost(), getMinimumCostPath());
    }

    public static void main(String[] args) {
        String testStream =
                "2\n" +
                "2\n" +
                "0 111 112 0\n" +
                "3\n" +
                "0 1000 5000 5000 0 1000 1000  5000  0";

        InputStream is = new ByteArrayInputStream(testStream.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        Scanner scanner = new Scanner(br);
        int tests = scanner.nextInt();
        for (int t = 0; t < tests; ++t) {
            int n = scanner.nextInt();
            int[][] d = new int[n][n];
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    d[i][j] = scanner.nextInt();
                }
            }

            Tsp tsp = new Tsp(d);
            System.out.println(tsp);
        }
    }
}
