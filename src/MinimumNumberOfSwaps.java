import java.util.Arrays;
import java.util.HashSet;

/**
 * Minimum number of swaps of chars in only one string to make two strings the same
 */

public class MinimumNumberOfSwaps {
    static int getMinSwapCount(String from, String to) {
        HashSet<String> pairs = new HashSet<>();

        int count = 0;
        for (int i = 0; i < from.length(); ++i) {
            char c1 = from.charAt(i);
            char c2 = to.charAt(i);
            if (c1 == c2) continue;

            char[] arr = {c1, c2};
            Arrays.sort(arr);
            String pair = new String(arr);
            if (pairs.contains(pair)) {
                ++count;
                pairs.remove(pair);
            } else {
                pairs.add(pair);
            }
        }

        count += pairs.size() - 1;
        return count;
    }

    public static void main(String[] args) {
        String from = "zzzeloxyzhl";
        String to = "zzzhelxyzlo";
        System.out.println(getMinSwapCount(from, to));
    }
}
