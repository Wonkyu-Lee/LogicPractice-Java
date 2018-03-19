import java.util.Arrays;
import java.util.LinkedList;

/**
 * https://www.youtube.com/watch?v=RORuwHiblPc&index=19&list=PLrmLmBdmIlpsHaNTPP_jHHDx_os9ItYXr
 */
public class TextJustification {

    static String applyWordWrap(String[] wordSequence, int lineWidth) {
        int n = wordSequence.length;

        int[][] cost = new int[n][n];
        for (int[] each : cost) {
            Arrays.fill(each, -1);
        }

        for (int i = 0; i < n; ++i) {
            String word = wordSequence[i];
            int spaces = Math.max(0, lineWidth - word.length());
            cost[i][i] = spaces * spaces;
        }

        for (int length = 2; length <= n; ++length) {
            for (int i = 0; i <= n - length; ++i) {
                int j = i + length - 1;
                int totalCharacters = 0;
                for (int k = i; k <= j; ++k) {
                    totalCharacters += wordSequence[k].length();
                }
                totalCharacters += length - 1;
                if (totalCharacters <= lineWidth) {
                    int spaces = lineWidth - totalCharacters;
                    cost[i][j] = spaces * spaces;
                }
            }
        }

        int[] minCost = new int[n];
        int[] wrap = new int[n];

        for (int i = n - 1; i >= 0; --i) {
            minCost[i] = Integer.MAX_VALUE;

            if (cost[i][n - 1] != -1) {
                minCost[i] = cost[i][n - 1];
                wrap[i] = n;
                continue;
            }

            for (int j = n - 1; j > i; --j) {
                int first = cost[i][j - 1];
                int second = minCost[j];
                if (first == -1)
                    continue;

                if (first + second < minCost[i]) {
                    minCost[i] = first + second;
                    wrap[i] = j;
                }
            }
        }

        LinkedList<Integer> wraps = new LinkedList<>();
        {
            int i = 0;
            while (wrap[i] < n) {
                wraps.add(wrap[i]);
                i = wrap[i];
            }
        }

        // TODO: 띄어쓰기 컨트롤을 좀더 잘 해보자.
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            if (!wraps.isEmpty() && wraps.peekFirst() == i) {
                sb.append("\n");
                wraps.removeFirst();
            }
            sb.append(wordSequence[i] + " ");
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        String[] wordSequence = {
                "Wonkyu",
                "Lee",
                "likes",
                "to",
                "code"
        };

        int lineWidth = 10;

        String result = applyWordWrap(wordSequence, lineWidth);
        System.out.println(result);
    }
}
