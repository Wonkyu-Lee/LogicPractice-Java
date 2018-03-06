import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

// https://ide.geeksforgeeks.org/E9re3MmjdA

public class PartitionsOfPainters {
    static int getMaxLength(int[] boards, int s, int e) {
        int maxLength = Integer.MIN_VALUE;
        for (int i = s; i <= e; ++i) {
            Math.max(maxLength, boards[i]);
        }

        return maxLength;
    }

    static int getSum(int[] boards, int s, int e) {
        int sum = 0;
        for (int i = s; i <= e; ++i) {
            sum += boards[i];
        }

        return sum;
    }

    static int getOptimalPaintTime(int[] boards, int s, int e, int painters) {
        int n = e - s + 1;
        if (n <= 0) return 0;
        if (n <= painters) return getMaxLength(boards, s, e);
        if (painters == 1) return getSum(boards, s, e);


        int minTime = Integer.MAX_VALUE;
        for (int i = s; i <= s + n - painters; ++i) {
            int left = getSum(boards, s, i);
            int right = getOptimalPaintTime(boards, i + 1, e, painters - 1);
            minTime = Math.min(minTime, Math.max(left, right));
        }

        return minTime;
    }

    static int getOptimalPaintTime(int[] boards, int painters) {
        return getOptimalPaintTime(boards, 0, boards.length - 1, painters);
    }

    public static void main (String[] args) {

        String testStream =
            "2\n" +
            "2 4\n" +
            "10 10 10 10\n" +
            "2 4\n" +
            "10 20 30 40";

        InputStream is = new ByteArrayInputStream(testStream.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        Scanner scanner = new Scanner(br);
        int tests = scanner.nextInt();
        for (int t = 0; t < tests; ++t) {
            int painters = scanner.nextInt();
            int boardCount = scanner.nextInt();

            int[] boards = new int[boardCount];
            for (int b = 0; b < boardCount; ++b) {
                boards[b] = scanner.nextInt();
            }

            int minTime = getOptimalPaintTime(boards, painters);
            System.out.println(minTime);
        }
    }
}
