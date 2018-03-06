import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class JobAssignment {

    static class State implements Comparable<State> {
        State parent = null;
        int worker = -1;
        int job = -1;
        int pathCost = 0;
        int lowerBoundCost = 0;
        boolean[] jobAssigned = null;

        public State(int[][] costs, State parent, int worker, int job) {
            if (parent == null) {
                jobAssigned = new boolean[costs.length];
                Arrays.fill(jobAssigned, false);
                return;
            }

            jobAssigned = Arrays.copyOf(parent.jobAssigned, parent.jobAssigned.length);
            jobAssigned[job] = true;
            this.parent = parent;
            this.worker = worker;
            this.job = job;
            this.pathCost = parent.pathCost + costs[worker][job];
            this.lowerBoundCost = this.pathCost + getRemainLowerBoundCost(costs);
        }

        private State(int[][] costs) {
            this(costs, null, -1, -1);
        }

//        private int getRemainLowerBoundCost(int[][] costs) {
//            int cost = 0;
//
//            int n = costs.length;
//            boolean[] available = new boolean[n];
//            Arrays.fill(available, true);
//
//            for (int i = worker + 1; i < n; ++i) {
//                int minCost = Integer.MAX_VALUE;
//                int minCotsJob = -1;
//                for (int j = 0; j < n; ++j) {
//                    if (!jobAssigned[j] && available[j] && costs[i][j] < minCost) {
//                        minCost = costs[i][j];
//                        minCotsJob = j;
//                    }
//                }
//
//                available[minCotsJob] = false;
//                cost += minCost;
//            }
//
//            return cost;
//        }

        private int getRemainLowerBoundCost(int[][] costs) {
            int cost = 0;

            int n = costs.length;

            for (int i = worker + 1; i < n; ++i) {
                int minCost = Integer.MAX_VALUE;
                for (int j = 0; j < n; ++j) {
                    if (!jobAssigned[j] && costs[i][j] < minCost) {
                        minCost = costs[i][j];
                    }
                }

                cost += minCost;
            }

            return cost;
        }

        @Override
        public int compareTo(State other) {
            return lowerBoundCost - other.lowerBoundCost;
        }
    }

    static void printAssignments(State state) {
        if (state.parent != null) {
            printAssignments(state.parent);
        }

        if (state.worker != -1)
            System.out.printf("[Worker:%d][Job:%d]\n", state.worker, state.job);
    }

    static int findMinCost(int[][] costs) {
        int callCount = 0;

        final int n = costs.length;

        PriorityQueue<State> minHeap = new PriorityQueue<>();
        minHeap.add(new State(costs));

        while (!minHeap.isEmpty()) {
            callCount++;

            State state = minHeap.poll();

            //System.out.println("LB cost: " + state.lowerBoundCost);

            int worker = state.worker + 1;

            if (worker == n) {
                //printAssignments(state);
                System.out.println("Call count: " + callCount);
                return state.pathCost;
            }

            for (int j = 0; j < n; ++j) {
                if (!state.jobAssigned[j]) {
                    State child = new State(costs, state, worker, j);
                    minHeap.add(child);
                }
            }
        }


        return Integer.MAX_VALUE;
    }

    public static void main(String[] args) {
        String testStream =
                "1\n" +
                "16\n" +
                "19 22 22 79 31 2 77 47 8 28 9 57 54 81 18 8 2 61 78 98 51 47 63 55 7 93 27 59 49 24 56 27 4 22 70 68 93 75 68 35 68 13 27 80 29 87 9 72 36 87 60 76 5 98 5 37 50 29 52 73 18 17 77 95 87 68 9 9 29 94 93 28 25 65 62 50 73 77 22 92 1 71 94 71 71 36 36 20 66 88 95 76 23 39 84 73 96 28 19 50 54 81 31 67 50 2 34 65 22 77 16 51 100 24 30 17 27 45 54 60 14 43 29 6 50 66 80 43 43 93 23 52 13 54 7 87 95 18 70 100 40 77 40 30 53 16 60 68 19 48 88 37 73 86 69 10 13 74 26 84 88 9 14 18 51 38 44 52 27 34 39 40 95 6 66 35 97 29 49 16 57 3 17 96 37 29 37 81 94 42 73 33 75 34 31 65 44 25 20 19 68 21 48 19 83 96 57 37 78 72 41 63 19 40 50 44 81 4 61 22 8 55 98 88 29 52 51 87 4 78 35 75 49 73 50 44 69 14 66 33 33 37 11 95 80 88 82 46 97 62 14 13 67 33 97 47";

        InputStream is = new ByteArrayInputStream(testStream.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        Scanner scanner = new Scanner(br); // 요거 다른데서 가져오는 거 알았었는데.. 한번 찾아보자

        int tests = scanner.nextInt();
        for (int t = 0; t < tests; ++t) {

            int n = scanner.nextInt();
            int[][] costs = new int[n][n];
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    costs[i][j] = scanner.nextInt();
                }
            }

            System.out.println(findMinCost(costs));
        }

//        int[][] costs = {
//                {9, 2, 7, 8},
//                {6, 4, 3, 7},
//                {5, 8, 1, 8},
//                {7, 6, 9, 4}
//        };
//
//        System.out.println(findMinCost(costs));
    }
}
