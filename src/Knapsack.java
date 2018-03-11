public class Knapsack {

    static int knapsack1(int[] values, int[] weights, int n, int capacity) {
        if (n == 0) return 0;
        if (capacity <= 0) return 0;

        int excluding = knapsack1(values, weights, n - 1, capacity);
        int including = 0;
        if (capacity >= weights[n - 1]) {
            including += knapsack1(values, weights, n - 1, capacity - weights[n - 1]) + values[n - 1];
        }

        return Math.max(excluding, including);
    }

    static int knapsack2(int[] values, int[] weights, int n, int capacity) {
        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; ++i) {
            for (int j = 0; j <= capacity; ++j) {
                int excluding = dp[i - 1][j];
                int including = 0;
                if (j >= weights[i - 1]) {
                    including += dp[i - 1][j - weights[i - 1]] + values[i - 1];
                }
                dp[i][j] = Math.max(excluding, including);
            }
        }

        return dp[n][capacity];
    }

    static int knapsack3(int[] values, int[] weights, int n, int capacity) {
        int[][] dp = new int[2][capacity + 1];
        int[] prev = dp[0];
        int[] curr = dp[1];

        for (int i = 1; i <= n; ++i) {
            for (int j = 0; j <= capacity; ++j) {
                int excluding = prev[j];
                int including = 0;
                if (j >= weights[i - 1]) {
                    including += prev[j - weights[i - 1]] + values[i - 1];
                }
                curr[j] = Math.max(excluding, including);
            }

            int[] t = prev;
            prev = curr;
            curr = t;
        }

        return prev[capacity];
    }


    static int unboundedKnapsack1( int[] values, int[] weights, int n, int capacity){
        if (n <= 0) return 0;
        if (capacity <= 0) return 0;

        int excluding = unboundedKnapsack1(values, weights, n - 1, capacity);
        int including = 0;
        if (capacity >= weights[n - 1]) {
            including = unboundedKnapsack1(values, weights, n, capacity - weights[n - 1]) + values[n - 1];
        }

        return Math.max(excluding, including);
    }

    static int unboundedKnapsack2(int[] values, int[] weights, int n, int capacity) {
        int[] dp = new int[capacity + 1];

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j <= capacity; ++j) {
                int excluding = dp[j];
                int including = 0;
                if (j >= weights[i]) {
                    including += dp[j - weights[i]] + values[i];
                }
                dp[j] = Math.max(excluding, including);
            }
        }

        return dp[capacity];
    }


    public static void main(String[] args) {
        int[] values = {10, 30, 20};
        int[] weights = {5, 10, 15};

        System.out.println(knapsack1(values, weights, 3, 8));
        System.out.println(knapsack2(values, weights, 3, 8));
        System.out.println(knapsack3(values, weights, 3, 8));

        System.out.println(unboundedKnapsack1(values, weights, 3, 8));
        System.out.println(unboundedKnapsack2(values, weights, 3, 8));
    }
}
