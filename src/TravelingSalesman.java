import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class TravelingSalesman {

    static class Tsp {
        private final int[][] mDistances;
        private final int mVertexCount;
        private final int[] mMinOutEdgeWeight;

        private List<Integer> mMinPath;
        private int mMinLength = Integer.MAX_VALUE;

        public Tsp(int[][] distances) {
            mDistances = distances;
            mVertexCount = distances.length;
            mMinOutEdgeWeight = new int[mVertexCount];
            int lowerBoundToEnd = 0;
            for (int i = 0; i < mVertexCount; ++i) {
                int minOutEdgeWeight = Integer.MAX_VALUE;
                for (int j = 0; j < mVertexCount; ++j) {
                    if (i == j)
                        continue;

                    if (mDistances[i][j] < minOutEdgeWeight) {
                        minOutEdgeWeight = mDistances[i][j];
                    }
                }
                mMinOutEdgeWeight[i] = minOutEdgeWeight;
                lowerBoundToEnd += minOutEdgeWeight;
            }

            Set<Integer> visited = new HashSet<>();
            LinkedList<Integer> path = new LinkedList<>();
            visited.add(0);
            path.add(0);
            solve(0, 0, lowerBoundToEnd, path, visited);
        }

        public List<Integer> path() {
            return mMinPath;
        }

        public int length() {
            return mMinLength;
        }

        private void solve(int u, int lengthFromStart, int lowerBoundToEnd, LinkedList<Integer> path, Set<Integer> visited) {
            if (visited.size() == mVertexCount) {
                int length = lengthFromStart + mDistances[u][0];

                if (mMinLength > length) {
                    mMinPath = new LinkedList<>(path);
                    mMinPath.add(0);
                    mMinLength = length;
                }

                return;
            }

            PriorityQueue<Integer> queue = new PriorityQueue<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer v1, Integer v2) {
                    return estimate(v1) - estimate(v2);
                }

                int estimate(int v) {
                    int newLengthFromStart = lengthFromStart + mDistances[u][v];
                    int newLowerBoundToEnd = lowerBoundToEnd - mMinOutEdgeWeight[u];
                    return newLengthFromStart + newLowerBoundToEnd;
                }
            });

            // TODO: queue를 다른 level의 상태 노드를 저장하는데 쓰는 형태로 변경할 수 있다.
            for (int v = 0; v < mVertexCount; ++v) {
                if (visited.contains(v)) {
                    continue;
                }
                queue.add(v);
            }

            while (!queue.isEmpty()) {
                int v = queue.poll();
                int newLengthFromStart = lengthFromStart + mDistances[u][v];
                int newLowerBoundToEnd = lowerBoundToEnd - mMinOutEdgeWeight[u];
                if (newLengthFromStart + newLowerBoundToEnd >= mMinLength) {
                    break;
                }

                visited.add(v);
                path.add(v);
                solve(v, newLengthFromStart, newLowerBoundToEnd, path, visited);
                visited.remove(v);
                path.removeLast();
            }

        }

        @Override
        public String toString() {
            return String.format("Length: %d, Path: %s", length(), path());
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

        Scanner scanner = new Scanner(br); // 요거 다른데서 가져오는 거 알았었는데.. 한번 찾아보자
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
