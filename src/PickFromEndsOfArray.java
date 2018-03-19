public class PickFromEndsOfArray {

    static class Pair {
        int first;
        int second;
        Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public String toString() {
            return String.format("(%d:%d)", first, second);
        }
    }

    static Pair getFirstWinScore(int[] game) {
        int n = game.length;
        Pair[][] dp = new Pair[n][n];

        for (int i = 0; i < n; ++i) {
            dp[i][i] = new Pair(game[i], 0);
        }

        for (int length = 2; length <= n; ++length) {
            for (int i = 0; i <= n - length; ++i) {
                int j = i + length - 1;

                int first, second;
                int option1 = game[i] + dp[i + 1][j].second;
                int option2 = game[j] + dp[i][j - 1].second;
                if (option1 >= option2) {
                    first = option1;
                    second = dp[i + 1][j].first;
                } else {
                    first = option2;
                    second = dp[i][j - 1].first;
                }

                dp[i][j] = new Pair(first, second);
            }
        }

        return dp[0][n - 1];
    }

    public static void main(String[] args) {
        int[] game = {3, 9, 1, 2};
        Pair score = getFirstWinScore(game);
        System.out.println(score);
    }
}
