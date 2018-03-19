import java.util.*;

/**
 * https://www.youtube.com/watch?v=WepWFGxiwRs&index=18&list=PLrmLmBdmIlpsHaNTPP_jHHDx_os9ItYXr
 */
public class WordBreakingProblem {
    /**
     * dic = {I, a, am, ace}
     * s = Iamace
     */
    static boolean splittable(Set<String> dic, String str) {
        int n = str.length();
        boolean[][] dp = new boolean[n][n];
        for (int i = 0; i < n; ++i) {
            String word = str.substring(i, i + 1);
            if (dic.contains(word)) {
                dp[i][i] = true;
            }
        }

        for (int length = 2; length <= n; ++length) {
            for (int i = 0; i <= n - length; ++i) {
                int j = i + length - 1;
                String word = str.substring(i, j + 1);
                if (dic.contains(word)) {
                    dp[i][j] = true;
                } else {
                    for (int k = i + 1; k <= j; ++k) {
                        boolean left = dp[i][k - 1];
                        boolean right = dp[k][j];
                        if (left && right) {
                            dp[i][j] = true;
                            break;
                        }
                    }
                }
            }
        }

        return dp[0][n - 1];
    }

    static class Pos {
        final int r;
        final int c;
        Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    static String wordSplitted(Set<String> dic, String str) {
        int n = str.length();
        boolean[][] dp = new boolean[n][n];
        int[][] splitIndex = new int[n][n];

        for (int[] each : splitIndex) {
            Arrays.fill(each, -1);
        }

        for (int i = 0; i < n; ++i) {
            String word = str.substring(i, i + 1);
            if (dic.contains(word)) {
                dp[i][i] = true;
            }
        }

        for (int length = 2; length <= n; ++length) {
            for (int i = 0; i <= n - length; ++i) {
                int j = i + length - 1;
                String word = str.substring(i, j + 1);
                if (dic.contains(word)) {
                    dp[i][j] = true;
                } else {
                    for (int k = i + 1; k <= j; ++k) {
                        boolean left = dp[i][k - 1];
                        boolean right = dp[k][j];
                        if (left && right) {
                            dp[i][j] = true;
                            splitIndex[i][j] = k;
                            break;
                        }
                    }
                }
            }
        }

        boolean splittable =dp[0][n - 1];
        if (!splittable)
            return str;

        StringBuilder sb = new StringBuilder();
        SortedSet<Integer> spaces = collectSpaces(splitIndex);
        int start = 0;
        for (int space : spaces) {
            sb.append(str.subSequence(start, space));
            sb.append(" ");
            start = space;
        }
        sb.append(str.substring(start));

        return sb.toString();
    }

    static SortedSet<Integer> collectSpaces(int[][] splitIndex) {
        SortedSet<Integer> spaces = new TreeSet<>();
        collectSpaces(splitIndex, spaces, 0, splitIndex.length - 1);
        return spaces;
    }

    static void collectSpaces(int[][] splitIndex, SortedSet<Integer> result, int i, int j) {
        if (i > j) return;

        int k = splitIndex[i][j];
        if (k != -1) {
            result.add(k);
            collectSpaces(splitIndex, result, i, k - 1);
            collectSpaces(splitIndex, result, k, j);
        }
    }

    public static void main(String[] args) {
        String[] dicArray = {"I", "a", "am", "ace"};
        String str = "Iamace";
        Set<String> dic = new HashSet<>(Arrays.asList(dicArray));
        System.out.println(splittable(dic, str));
        System.out.println(wordSplitted(dic, str));
    }
}
