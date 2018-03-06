import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;


// https://ide.geeksforgeeks.org/dv1DL4WyEP
// https://ide.geeksforgeeks.org/m3YZAXGQOg

// Time limit 넘어갔다 다시 풀어보자.

public class TaskAssignment {
    static class Context {
        final int[][] times;
        int[] minTimeForTask;
        HashSet<Integer> assignedPeople = new HashSet<>();
        int minTime = Integer.MAX_VALUE;
        int callCount = 0;

        Context(int[][] times) {
            this.times = times;
            int n = times.length;

            minTimeForTask = new int[n];
            for (int t = 0; t < n; ++t) {
                minTimeForTask[t] = Integer.MAX_VALUE;
                for (int p = 0; p < n; ++p) {
                    minTimeForTask[t] = Math.min(minTimeForTask[t], times[p][t]);
                }
            }
        }

        int minTimeSum() {
            int n = times.length;
            int sum = 0;
            for (int t = 0; t < n; ++t) {
                sum += minTimeForTask[t];
            }
            return sum;
        }

        class Expectation implements Comparable<Expectation> {
            final int person;
            final int timeFromStart;
            final int timeToEnd;

            Expectation(int timeFromStart, int timeToEnd, int p, int t) {
                this.timeFromStart = timeFromStart + times[p][t];
                this.timeToEnd = timeToEnd - minTimeForTask[t];
                person = p;
            }

            @Override
            public int compareTo(Expectation other) {
                return time() - other.time();
            }

            private int time() {
                return timeFromStart + timeToEnd;
            }
        }

        void assignTask(int taskIndex, int timeFromStart, int timeToEnd) {
            ++callCount;

            int n = times.length;
            if (taskIndex == n) {
                minTime = Math.min(minTime, timeFromStart);
                return;
            }

            PriorityQueue<Expectation> minHeap = new PriorityQueue<>();

            for (int p = 0; p < n; ++p) {
                if (!assignedPeople.contains(p)) {
                    minHeap.add(new Expectation(timeFromStart, timeToEnd, p, taskIndex));
                }
            }

            while (!minHeap.isEmpty()) {
                Expectation expectation = minHeap.poll();

                if (expectation.time() >= minTime) {
                    continue;
                }

                assignedPeople.add(expectation.person);
                assignTask(taskIndex + 1, expectation.timeFromStart, expectation.timeToEnd);
                assignedPeople.remove(expectation.person);
            }
        }
    }

    static int getMinTime(int[][] times) {
        Context context = new Context(times);

        int timeFromStart = 0;
        int timeToEnd = context.minTimeSum();
        int taskIndex = 0;

        context.assignTask(taskIndex, timeFromStart, timeToEnd);

        System.out.println("Call count: " + context.callCount);

        return context.minTime;
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
            int[][] times = new int[n][n];
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    times[i][j] = scanner.nextInt();
                }
            }

            System.out.println(getMinTime(times));
        }
    }
}
