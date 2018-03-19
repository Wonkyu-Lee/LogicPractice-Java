public class LongestPalindromicSequence {
    /**
     * 0 1 2 3 4 5
     * a g b d b a
     *
     * for l = 1..n
     *    for (i, j)
     *        if (s[i] == s[j])
     *            T[i, j] = 2 + T[i + 1, j - 1]
     *        else
     *            T[i, j] = max(T[i + 1, j], T[i, j - 1])
     */
    static int lengthOfLongestPalindromicSequence(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];

        for (int i = 0; i < n; ++i) {
            dp[i][i] = 1;
        }

        for (int length = 2; length <= s.length(); ++length) {
            for (int i = 0; i <= n - length; ++i) {
                int j = i + length - 1;

                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = 2 + dp[i + 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[0][n - 1];
    }

    static String findLongestPalindromicSequence(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];

        for (int i = 0; i < n; ++i) {
            dp[i][i] = 1;
        }

        for (int length = 2; length <= s.length(); ++length) {
            for (int i = 0; i <= n - length; ++i) {
                int j = i + length - 1;

                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = 2 + dp[i + 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }

        int length = dp[0][n - 1];
        char[] palindrome = new char[length];

        int i = 0;
        int j = n - 1;
        int p = 0;
        int q = length - 1;
        while (i <= j) {
            if (s.charAt(i) == s.charAt(j)) {
                char ch = s.charAt(i);
                palindrome[p] = palindrome[q] = ch;
                ++p;
                --q;
                ++i;
                --j;
            } else {
                if (dp[i][j] == dp[i + 1][j]) {
                    ++i;
                } else {
                    --j;
                }
            }
        }

        return new String(palindrome);
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestPalindromicSequence("agbdba"));
        System.out.println(findLongestPalindromicSequence("agbdba"));
    }
}
