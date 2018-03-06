import java.util.HashMap;

public class LargestWordInDictionary {

    //https://practice.geeksforgeeks.org/problems/find-largest-word-in-dictionary/0

    static HashMap<Character, Integer> encode(String str) {
        HashMap<Character, Integer> encoded = new HashMap<>();
        String lower = str.toLowerCase();
        for (int i = 0; i < lower.length(); ++i) {
            char ch = lower.charAt(i);
            encoded.put(ch, encoded.getOrDefault(ch, 0) + 1);
        }
        return encoded;
    }

    static boolean includes(HashMap<Character, Integer> container, HashMap<Character, Integer> containee) {
        for (Character key : containee.keySet()) {
            int count = containee.get(key);
            if (!container.containsKey(key)) {
                return false;
            }

            if (count > container.get(key)) {
                return false;
            }
        }

        return true;
    }

    static boolean includes(String container, String containee) {
        return includes(encode(container), encode(containee));
    }

    static String findLargestWord(String[] dic, String word) {
        int maxLen = Integer.MIN_VALUE;
        String maxLenString = null;
        for (int i = 0; i < dic.length; ++i) {
            if (includes(word, dic[i])) {
                if (maxLen < dic[i].length()) {
                    maxLen = dic[i].length();
                    maxLenString = dic[i];
                }
            }
        }

        return maxLenString;
    }
}
