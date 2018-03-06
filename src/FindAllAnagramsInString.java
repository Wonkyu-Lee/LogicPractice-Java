import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


/**
 * https://www.geeksforgeeks.org/anagram-substring-search-search-permutations/
 * https://ide.geeksforgeeks.org/fRGrznz87k
 *
 * TODO: 없는 글자를 포함하고 있으면 그 다음으로 더 빨리 skip 할 수 있을텐데, 대신 새로 counts 계산해줘야 함.
 */
public class FindAllAnagramsInString {

    static void addAmount(HashMap<Character, Integer> counts, char c, int amount) {
        int count = counts.getOrDefault(c, 0) + amount;
        if (count == 0)
            counts.remove(c);
        else
            counts.put(c, count);
    }

    static boolean equals(HashMap<Character, Integer> counts1, HashMap<Character, Integer> counts2) {
        if (counts1.size() != counts2.size())
            return false;

        for (char c : counts1.keySet()) {
            if (counts1.get(c) != counts2.get(c))
                return false;
        }

        return true;
    }

    static List<Integer> findAnagramsInString(String small, String large) {
        int m = small.length();
        int n = large.length();

        LinkedList<Integer> result = new LinkedList<>();
        if (m > n) {
            return result;
        }

        HashMap<Character, Integer> sCounts = new HashMap<>();
        HashMap<Character, Integer> lCounts = new HashMap<>();

        for (int i = 0; i < m; ++i) {
            addAmount(sCounts, small.charAt(i), 1);
            addAmount(lCounts, large.charAt(i), 1);
        }

        if (equals(sCounts, lCounts)) {
            result.add(0);
        }

        for (int i = 1; i + m <= n; ++i) {
            addAmount(lCounts, large.charAt(i - 1), -1);
            addAmount(lCounts, large.charAt(i + m - 1), 1);
            if (equals(sCounts, lCounts)) {
                result.add(i);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        List<Integer> result = findAnagramsInString("ABCD", "BACDGABCDA");
        for (int index : result) {
            System.out.println(index + " ");
        }
    }
}
