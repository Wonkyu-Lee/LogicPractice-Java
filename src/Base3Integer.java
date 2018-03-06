import java.util.LinkedList;

public class Base3Integer {
    /**
     *
     * Google Telephonic Round 3
     *
     * Convert an integer to base-3 equivalent
     *
     */
    static String convertIntegerToBase3(int x) {
        if (x == 0) {
            return "0";
        }

        LinkedList<Character> list = new LinkedList<>();

        while (x != 0) {
            list.addFirst(String.valueOf(x % 3).charAt(0));
            x /= 3;
        }

        StringBuilder sb = new StringBuilder();
        for (char c : list) {
            sb.append(c);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(convertIntegerToBase3(55));
    }
}
