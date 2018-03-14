import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class PatternMatchingWithFsm {

    static class Pair<First, Second> {
        public final First first;
        public final Second second;

        Pair(First first, Second second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public boolean equals(Object o) {
            Pair<First, Second> other = (Pair<First, Second>)o;
            if (other == null)
                return false;

            if (this == other) {
                return true;
            }

            return first == other.first && second == other.second;
        }

        @Override
        public int hashCode() {
            int r = 17;
            r = r * 31 + first.hashCode();
            r = r * 31 + second.hashCode();
            return r;
        }
    }

    static HashMap<Pair<Integer, Character>, Integer> buildFsm(String pattern) {
        HashMap<Pair<Integer, Character>, Integer> fsm = new HashMap<>();

        HashSet<Character> alphabets = new HashSet<>();
        for (int i = 0; i < pattern.length(); ++i) {
            alphabets.add(pattern.charAt(i));
        }

        for (int i = 0; i <= pattern.length(); ++i) {
            for (char c : alphabets) {
                int nextState = findNextState(pattern, i, c);
                fsm.put(new Pair<>(i, c), nextState);
            }
        }

        return fsm;
    }

    static int findNextState(String pattern, int curState, char input) {
        String str = pattern.substring(0, curState) + input;

        int maxLength = 0;
        for (int i = str.length() - 1; 0 <= i; --i) {
            String suffix = str.substring(i, str.length());
            if (suffix.length() > pattern.length())
                break;

            boolean equals = true;
            for (int j = 0; j < suffix.length(); ++j) {
                if (suffix.charAt(j) != pattern.charAt(j)) {
                    equals = false;
                    break;
                }
            }

            if (equals)
                maxLength = Math.max(maxLength, suffix.length());
        }

        return maxLength;
    }

    static List<Integer> match(String pattern, String text) {
        List<Integer> matched = new LinkedList<>();
        HashMap<Pair<Integer, Character>, Integer> fsm = buildFsm(pattern);

        int state = 0;
        for (int i = 0; i < text.length(); ++i) {
            char input = text.charAt(i);
            Pair<Integer, Character> key = new Pair<>(state, input);
            state = fsm.getOrDefault(key, 0);
            if (state == pattern.length()) {
                matched.add(i - state + 1);
            }
        }

        return matched;
    }

    public static void main(String[] args) {
//        HashMap<Pair<Integer, Character>, Integer> fsm = buildFsm("ACACAGA");
//
//        int[] rows = {0, 1, 2, 3, 4, 5, 6, 7};
//        char[] cols = {'A', 'C', 'G'};
//
//        for (int i = 0; i < rows.length; ++i) {
//            System.out.printf("%d: ", i);
//            for (int j = 0; j < cols.length; ++j) {
//                Pair<Integer, Character> key = new Pair<>(rows[i], cols[j]);
//                int nextState = fsm.getOrDefault(key, -1);
//                System.out.printf(" %d", nextState);
//
//            }
//            System.out.println();
//        }

        List<Integer> result = match("AABA", "AABAACAADAABAAABAA");
        System.out.println(result);
    }
}
