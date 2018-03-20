import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Tsp2 {

    static class Context {
        final int[][] d;
        int minLength = Integer.MAX_VALUE;
        LinkedList<Integer> minPath = null;
        int[] minOutEdgeWeights;
        int sumOfMinWeights = 0;

        Context(int[][] d) {
            this.d = d;

            int n = d.length;
            minOutEdgeWeights = new int[n];

            for (int i = 0; i < n; ++i) {
                int minWeight = Integer.MAX_VALUE;
                for (int j = 0; j < n; ++j) {
                    if (i == j) continue;
                    minWeight = Math.min(minWeight, d[i][j]);
                }
                minOutEdgeWeights[i] = minWeight;
                sumOfMinWeights += minWeight;
            }
        }

        int vertexCount() {
            return d.length;
        }
    }

    static abstract class TspSolver {
        Context context;

        TspSolver(int[][] d) {
            context = new Context(d);
        }

        int length() {
            return context.minLength;
        }

        List<Integer> path() {
            return context.minPath;
        }
    }

    static class BoundedBranchSolver extends TspSolver {
        State startState;

        BoundedBranchSolver(int[][] d) {
            super(d);
            startState = new State(context);
            solve(startState);
        }

        void solve(State start) {
            List<State> candidates = start.nextCandidates();
            if (candidates.isEmpty()) {
                start.checkAndUpdate();
                return;
            }

            Collections.sort(candidates, Comparator.comparing((State state) -> state.expLength));
            for (State candidate : candidates) {
                if (candidate.expLength > context.minLength) {
                    return;
                }

                solve(candidate);
            }
        }
    }

    static class AStarSolver extends TspSolver {
        PriorityQueue<State> queue;

        AStarSolver(int[][] d) {
            super(d);
            queue = new PriorityQueue<>(Comparator.comparing(s -> s.expLength));
            queue.add(new State(context));
            solve();
        }

        void solve() {
            while (!queue.isEmpty()) {
                State state = queue.poll();

                List<State> candidates = state.nextCandidates();
                if (candidates.isEmpty()) {
                    state.checkAndUpdate();
                    return;
                }

                queue.addAll(candidates);
            }
        }
    }

    static class State {
        final Context context;
        final boolean[] visited;
        int curLength;
        int expLength;
        LinkedList<Integer> curPath = new LinkedList<>();

        State(Context context) {
            this.context = context;
            int n = context.vertexCount();
            visited = new boolean[n];
            curLength = 0;
            expLength = context.sumOfMinWeights;
        }

        State(State other) {
            this.context = other.context;
            visited = other.visited.clone();
            curLength = other.curLength;
            expLength = other.expLength;
            curPath.addAll(other.curPath);
        }

        List<State> nextCandidates() {
            ArrayList<State> candidates = new ArrayList<>();
            int n = context.vertexCount();
            for (int i = 0; i < n; ++i) {
                if (!visited[i]) {
                    State state = new State(this);
                    state.visit(i);
                    candidates.add(state);
                    break;
                }
            }
            return candidates;
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
                curLength += context.d[last][v];
                expLength += context.d[last][v] - context.minOutEdgeWeights[last];
            }
        }

        void checkAndUpdate() {
            if (curPath.isEmpty())
                throw new AssertionError();

            int first = curPath.peekFirst();
            int last = curPath.peekLast();
            int newLength = curLength + context.d[last][first];
            if (newLength < context.minLength) {
                context.minLength = newLength;
                context.minPath = new LinkedList<>(curPath);
                context.minPath.add(first);
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


            {
                System.out.println("A-Star Solver");
                List<Integer> optimalPath = new AStarSolver(d).path();
                System.out.println(optimalPath);
            }

            {
                System.out.println("Bounded branch Solver");
                List<Integer> optimalPath = new BoundedBranchSolver(d).path();
                System.out.println(optimalPath);
            }
        }
    }
}
