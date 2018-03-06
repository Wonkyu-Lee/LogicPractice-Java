import java.util.HashMap;

/**
 * https://practice.geeksforgeeks.org/problems/longest-k-unique-characters-substring/0
 * https://ide.geeksforgeeks.org/HakK8Ohoa3
 */
public class LongKUniqueCharactersSubstring {

    static class Window {
        private HashMap<Character, Integer> counts = new HashMap<>();
        private String str;
        private int start;
        private int end;
        private int k;
        private int maxSize = -1;
        private int startAtMaxSize = -1;

        public Window(String str, int k) {
            if (str.isEmpty()) {
                throw new IllegalArgumentException();
            }

            this.str = str;
            this.k = k;
            start = 0;
            end = 0;
            addAmount(str.charAt(start), 1);

            while (next());
        }

        private int getUniqueCharacters() {
            return counts.size();
        }

        public int getStartIndexAtMaxSize() {
            return startAtMaxSize;
        }

        public int getMaxSize() {
            return maxSize;
        }

        private boolean next() {
            if (end == str.length() - 1) {
                return false;
            }

            ++end;
            addAmount(str.charAt(end), 1);

            while (getUniqueCharacters() > k) {
                addAmount(str.charAt(start), -1);
                ++start;
            }

            int length = end - start + 1;
            if (getUniqueCharacters() == k && length > maxSize) {
                maxSize = length;
                startAtMaxSize = start;
            }

            return true;
        }

        private void addAmount(char c, int amount) {
            int newCount = counts.getOrDefault(c, 0) + amount;
            if (newCount == 0) {
                counts.remove(c);
            } else {
                counts.put(c, newCount);
            }
        }
    }

    public static void main(String[] args) {
        String str = "pxrjxkitzyxacbhhkicqcoendtomfgdwdwfcgpxiqvkuytdlcgdewhtacio";
        int k = 3;
        Window window = new Window(str, k);
        System.out.printf("Start: %d, Length: %d\n", window.getStartIndexAtMaxSize(), window.getMaxSize());
    }
}
