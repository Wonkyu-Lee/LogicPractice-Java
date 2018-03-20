import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Tsp2 {

    static List<Integer> solveTsp(int[][] d) {
        Tsp tsp = new Tsp(d);
        return tsp.minPath;
    }

    static class Tsp {
        final int[][] d;
        final boolean[] visited;
        int curLength;
        int minLength = Integer.MAX_VALUE;
        LinkedList<Integer> curPath = new LinkedList<>();
        LinkedList<Integer> minPath = null;

        Tsp(int[][] d) {
            int n = d.length;
            this.d = new int[n][n];
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    this.d[i][j] = d[i][j];
                }
            }

            visited = new boolean[n];
            curLength = 0;

            solve();
        }

        int vertexCount() {
            return d.length;
        }

        void solve() {
            int v = getToVisit();
            if (v == -1) {
                checkAndUpdate();
                return;
            }

            visit(v);
            solve();
            rollback();
        }

        // TODO: 이 부분을 개선해보자.
        int getToVisit() {
            int v = -1;
            int n = vertexCount();
            for (int i = 0; i < n; ++i) {
                if (!visited[i]) {
                    v = i;
                    break;
                }
            }
            return v;
        }

        void visit(int v) {
            if (visited[v])
                throw new AssertionError();

            visited[v] = true;

            if (curPath.isEmpty()) {
                curPath.add(v);
            } else {
                int last = curPath.peekLast();
                curPath.add(v);
                curLength += d[last][v];
            }
        }

        void checkAndUpdate() {
            if (curPath.isEmpty())
                throw new AssertionError();

            int first = curPath.peekFirst();
            int last = curPath.peekLast();
            int newLength = curLength + d[last][first];
            if (newLength < minLength) {
                minLength = newLength;
                minPath = new LinkedList<>(curPath);
                minPath.add(first);
            }
        }

        void rollback() {
            if (curPath.isEmpty())
                throw new AssertionError();

            int v = curPath.peekLast();

            visited[v] = false;
            curPath.removeLast();

            if (!curPath.isEmpty()) {
                int u = curPath.peekLast();
                curLength -= d[u][v];
            }
        }
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

            List<Integer> optimalPath = solveTsp(d);
            System.out.println(optimalPath);
        }
    }
}
