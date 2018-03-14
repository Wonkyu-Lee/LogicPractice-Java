import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class CaptureStonesInGo {
    enum Stone {
        EMPTY,
        BLACK,
        WHITE
    }

    static class Pair {
        public final int row;
        public final int col;
        Pair(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public int hashCode() {
            int r = 17;
            r = 31 * r + row;
            r = 31 * r + col;
            return r;
        }

        @Override
        public boolean equals(Object o) {
            Pair other = (Pair)o;
            if (other == null) return false;
            if (other == this) return true;
            return (row == other.row && col == other.col);
        }
    }

    static boolean willCapture(Stone[][] grid, int r, int c, Stone stone) {
        if (stone == Stone.EMPTY) {
            return false;
        }

        grid[r][c] = stone;

        Stone opponent = (stone == Stone.BLACK) ? Stone.WHITE : Stone.BLACK;

        int[][] delta = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}
        };

        for (int i = 0; i < 4; ++i) {
            int dr = delta[i][0];
            int dc = delta[i][1];

            int nr = r + dr;
            int nc = c + dc;
            if (!valid(grid, nr, nc))
                continue;
            if (grid[nr][nc] != opponent)
                continue;

            Set<Pair> visited = new HashSet<>();
            if (surroundedByOpponent(grid, nr, nc, visited))
                return true;
        }

        grid[r][c] = Stone.EMPTY;

        return false;
    }

    static boolean valid(Stone[][] grid, int r, int c) {
        return (0 <= r && r < grid.length) && (0 <= c && c < grid[0].length);
    }

    static boolean surroundedByOpponent(Stone[][] grid, int r, int c, Set<Pair> visited) {
        Stone stone = grid[r][c];
        Stone opponent = (stone == Stone.BLACK) ? Stone.WHITE : Stone.BLACK;
        Pair pos = new Pair(r, c);
        visited.add(pos);

        boolean surrounded = true;

        int[][] delta = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}
        };

        for (int i = 0; i < 4; ++i) {
            int dr = delta[i][0];
            int dc = delta[i][1];

            int nr = r + dr;
            int nc = c + dc;
            if (!valid(grid, nr, nc))
                continue;

            Pair neighbor = new Pair(nr, nc);
            if (visited.contains(neighbor))
                continue;

            if (grid[nr][nc] == stone) {
                return surroundedByOpponent(grid, nr, nc, visited);
            } else if (grid[nr][nc] == opponent) {
                continue;
            } else {
                surrounded = false;
                break;
            }
        }

        visited.remove(pos);
        return surrounded;
    }

    public static void main(String[] args) {
        String strGrid =
                "0 0 2 0 0 0 0 0 0 0 " +
                "2 2 1 2 2 0 0 0 0 0 " +
                "1 1 1 1 1 2 0 0 0 0 " +
                "2 2 2 2 0 0 0 0 0 0 " +
                "1 1 1 1 0 0 0 0 0 2 " +
                "0 0 0 0 0 0 0 0 2 1 " +
                "0 0 0 0 0 0 0 2 1 1 " +
                "0 0 0 0 0 0 0 1 1 1 " +
                "0 0 0 0 0 0 0 2 1 1 " +
                "0 0 0 0 0 0 0 0 2 1 ";

        InputStream is = new ByteArrayInputStream(strGrid.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        Scanner scanner = new Scanner(br);

        Stone[][] grid = new Stone[10][10];
        Stone[] type = {Stone.EMPTY, Stone.BLACK, Stone.WHITE};
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                int stone = scanner.nextInt();
                grid[i][j] = type[stone];
            }
        }

        boolean capturing = willCapture(grid, 7, 6, Stone.WHITE);
        System.out.printf("Will capture? = %s\n", (capturing ? "Y" : "N"));
    }
}
