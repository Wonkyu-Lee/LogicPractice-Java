import java.util.HashSet;

public class SeatSwapInCinema {
    /**
     * https://www.careercup.com/question?id=4725199160213504
     * N different couple go to cinema with 2N different seats. They take their place randomly.
     * You could make swap operations.
     * Write a code for given input what is the minimum number of swap operations for sitting all couples with their partners?
     * Additionally, be sure that no one swaps more than 2 times.
     *
     * N = 5
     * 0: 3, 1
     * 1: 0, 4
     * 2: 2, 0
     * 3: 4, 2
     * 4: 1, 3
     * -> 3
     *
     */

    static int countSwaps(int[][] seats) {
        HashSet<String> pairs = new HashSet<>();

        int count = 0;
        for (int i = 0; i < seats.length; ++i) {
            int l = seats[i][0];
            int r = seats[i][1];
            if (l == r) continue;

            int smaller = Math.min(l, r);
            int larger = Math.max(l, r);
            String pair = smaller + "_" + larger;
            if (pairs.contains(pair)) {
                count += 1;
                pairs.remove(pair);
            } else {
                pairs.add(pair);
            }
        }

        count += pairs.size() - 1;
        return count;
    }

    public static void main(String[] args) {
        int[][] seats = {
                {3, 1},
                {1, 3},
                {2, 0},
                {4, 2},
                {0, 4}
        };

        System.out.println(countSwaps(seats));
    }
}
