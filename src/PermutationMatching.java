import java.util.HashMap;

public class PermutationMatching {
    /**
     *
     * Google Telephonic Round 2
     *
     * Given any uppercase string. Report the starting index at which any valid permutation of
     * ABCDEF starts. If not found, then report -1.
     * Possible permutations of ABCDEF are ABCDFE, BCDAFE, FEDCAB etc (a total of 6! = 720 permutations)
     *
     */

    /**
     * Clarification:
     * 첫 번째 발견된 index만 리턴하면 되나? 아니면 발견되는 모든 index를 리턴해야 하나?
     * 만약 모든 문자가 매칭되었으면 이전 hash table에서 첫 글자만 false로 세팅한 뒤 재사용하는 것이 좋다.
     * ex. BCDAFECDB -> {0, 3}
     *
     * 그냥 첫 번째 index만 리턴한다고 하자.
     * KHBCZBCDFEABROQ
     *
     * Time complexity:
     * S: KHBCZBCDFEABROQ
     * P: ABCDEF
     *
     * Worst case
     * S: ABCDEZABCDEZABCDEZ -> O(S + P)
     */

    String pattern = "ABCDEF";

    class Result {
        boolean matched = false;
        int nextIndex = -1;
        Result(boolean matched, int nextIndex) {
            this.matched = matched;
            this.nextIndex = nextIndex;
        }
    }

    Result isMatched(String str, int s) {
        HashMap<Character, Boolean> counts = new HashMap<>();
        for (int i = 0; i < pattern.length(); ++i) {
            counts.put(pattern.charAt(i), false);
        }

        for (int i = s; i < pattern.length(); ++i) {
            char c = str.charAt(i);
            if (!counts.containsKey(c)) {
                return new Result(false, i + 1);
            }

            if (counts.get(c)) {
                return new Result(false, i);
            }

            counts.put(c, true);
        }

        return new Result(true, s + 1);
    }

    int findStartOfPermutation(String str) {
        if (str.length() < pattern.length())
            return -1;

        int i = 0;
        while (i <= str.length() - pattern.length()) {
            Result r = isMatched(str, i);
            if (r.matched) {
                return i;
            } else {
                i = r.nextIndex;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        PermutationMatching pm = new PermutationMatching();
        String str = "KHBCZBCDFEABROQ";
        System.out.println("Start index of permutation ABCDEF for " + str + ": " + pm.findStartOfPermutation("KHBCZBCDFEABROQ"));
    }
}
