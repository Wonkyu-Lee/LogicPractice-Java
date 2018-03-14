import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * For a two-dimensional array of arbitrary size (not guaranteed to be square) containing binary values,
 * find the total number of rectangles defined by four corners and parallel to the array's dimensions.
 */
public class RectanglesInGrid {

    static class OrderedPair {
        public final int first;
        public final int second;

        OrderedPair(int a, int b) {
            if (a <= b) {
                first = a;
                second = b;
            } else {
                first = b;
                second = a;
            }
        }

        @Override
        public int hashCode() {
            int r = 17;
            r = 31 * r + first;
            r = 31 * r + second;
            return r;
        }

        @Override
        public boolean equals(Object o) {
            OrderedPair other = (OrderedPair)o;
            if (other == null) return false;
            if (this == other) return true;
            return first == other.first && second == other.second;
        }
    }

    // O(R*C^2)
    static int countRectangles(int[][] grid) {
        int nRows = grid.length;
        int nCols = grid[0].length;
        Map<OrderedPair, Integer> map = new HashMap<>();

        for (int r = 0; r < nRows; ++r) {
            // O(R*C)
            ArrayList<Integer> points = new ArrayList<>();
            for (int c = 0; c < nCols; ++c) {
                if (grid[r][c] == 1) {
                    points.add(c);
                }
            }

            // O(R*C^2)
            for (int i = 0; i < points.size() - 1; ++i) {
                for (int j = i + 1; j < points.size(); ++j) {
                    int x1 = points.get(i);
                    int x2 = points.get(j);
                    OrderedPair key = new OrderedPair(x1, x2);
                    int count = map.getOrDefault(key, 0);
                    map.put(key, count + 1);
                }
            }
        }

        // O(C^2)
        int count = 0;
        for (int each : map.values()) {
            count += binomialCoeff(each, 2);
        }

        return count;
    }

    static int binomialCoeff(int n, int k) {
        if (n < k) {
            return 0;
        }

        if (k == 1) {
            return n;
        }

        if (n - k < k) {
            k = n - k;
        }

        int x = 1;
        for (int i = 0; i < k; ++i) {
            x *= n - i;
            x /= 1 + i;
        }

        return x;
    }

    public static void main(String[] args) {
        int[][] grid = {
                {0, 1, 0, 1, 0},
                {0, 0, 0, 0, 0},
                {0, 1, 0, 1, 1},
                {0, 0, 0, 0, 0},
                {0, 1, 0, 1, 1}
        };

        System.out.print("Count = " + countRectangles(grid));
    }
}
