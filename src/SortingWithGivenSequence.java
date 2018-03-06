import java.util.HashMap;

public class SortingWithGivenSequence {
    /**
     *
     * Google Telephonic Round 1
     *
     * Given two strings - S1 and S2.
     * Arrange the characters of S1 in same alphabetical order as the characters of S2.
     * If a character of S1 is not present in S2 - such characters should come at the end of
     * the result string, but make sure to retain the order of such characters
     * Case sensitivity is irrelevant
     * e.g. S1 = "Google", S2 = "dog"
     * Output = "ooggle"
     *
     * e.g. S1 = "abcdedadf", S2 = "cae"
     * Output = "caaebdddf"
     *
     */

    static String sortWithGivenSeq(String str, String seq) {
        str = str.toLowerCase();
        seq = seq.toLowerCase();

        HashMap<Character, Integer> counts = new HashMap<>();
        for (int i = 0; i < seq.length(); ++i) {
            counts.put(seq.charAt(i), 0);
        }

        StringBuilder remain = new StringBuilder();
        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            if (counts.containsKey(ch)) {
                counts.put(ch, counts.get(ch) + 1);
            } else {
                remain.append(ch);
            }
        }

        StringBuilder prefix = new StringBuilder();
        for (int i = 0; i < seq.length(); ++i) {
            char ch = seq.charAt(i);
            int count = counts.get(ch);
            for (int j = 0; j < count; ++j) {
                prefix.append(ch);
            }
        }

        prefix.append(remain);
        return prefix.toString();
    }

    public static void main(String[] args) {
        System.out.println(sortWithGivenSeq("abcdedadf", "cae"));
        System.out.println(sortWithGivenSeq("Google", "dog"));
    }
}
