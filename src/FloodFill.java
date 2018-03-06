import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class FloodFill {
    static boolean isValid(int[][] canvas, int r, int c) {
        if (r < 0 || canvas.length <= r) return false;
        if (c < 0 || canvas[0].length <= c) return false;
        return true;
    }

    static void floodFill(int[][] canvas, int r, int c, int color) {
        if (!isValid(canvas, r, c)) return;
        int fromColor = canvas[r][c];
        floodFill(canvas, r, c, fromColor, color);
    }

    static void floodFill(int[][] canvas, int r, int c, int fromColor, int toColor) {
        if (!isValid(canvas, r, c)) return;
        if (canvas[r][c] != fromColor) return;
        canvas[r][c] = toColor;
        floodFill(canvas, r - 1, c, fromColor, toColor);
        floodFill(canvas, r + 1, c, fromColor, toColor);
        floodFill(canvas, r, c - 1, fromColor, toColor);
        floodFill(canvas, r, c + 1, fromColor, toColor);
    }

    public static void main (String[] args) {

        String testStream =
                "3\n" +
                "3 4\n" +
                "0 1 1 0 1 1 1 1 0 1 2 3\n" +
                "0 1 5\n" +
                "2 2\n" +
                "1 1 1 1\n" +
                "0 1 8\n" +
                "4 4 \n" +
                "1 2 3 4 1 2 3 4 1 2 3 4 1 3 2 4\n" +
                "0 2 9";

        InputStream is = new ByteArrayInputStream(testStream.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        Scanner scanner = new Scanner(br);

        int testCount = scanner.nextInt();
        for (int t = 0; t < testCount; ++t) {
            int rows = scanner.nextInt();
            int cols = scanner.nextInt();
            int[][] canvas = new int[rows][cols];
            for (int i = 0; i < rows * cols; ++i) {
                canvas[i / cols][i % cols] = scanner.nextInt();
            }
            int r = scanner.nextInt();
            int c = scanner.nextInt();
            int color = scanner.nextInt();
            floodFill(canvas, r, c, color);

            for (r = 0; r < rows; ++r) {
                for (c = 0; c < cols; ++c) {
                    System.out.printf(canvas[r][c] + " ");
                }
            }
            System.out.println();
        }
    }
}
