import java.util.HashMap;
import java.util.LinkedList;

public class AllQuadruplesA3B3EqC3D3 {

    static class Pair {
        int x;
        int y;
        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // a^3 + b^3 = c^3 + d^3
    static void printAll(int n) {
        HashMap<Integer, LinkedList<Pair>> map = new HashMap<>();
        for (int c = 1; c <= n; ++c) {
            for (int d = 1; d <= n; ++d) {
                int v = (int)(Math.pow(c, 3) + Math.pow(d, 3));
                LinkedList<Pair> list = map.getOrDefault(v, new LinkedList<>());
                list.add(new Pair(c, d));
                map.put(v, list);
            }
        }

        for (int each : map.keySet()) {
            LinkedList<Pair> list = map.get(each);
            for (Pair left : list) {
                for (Pair right : list) {
                    System.out.printf("(a, b, c, d) = (%d, %d, %d, %d)\n", left.x, left.y, right.x, right.y);
                }
            }
        }
    }

    public static void main(String[] args) {
        printAll(4);
    }
}
