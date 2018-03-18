import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class BoardGameGo {
    enum Stone {
        EMPTY,
        BLACK,
        WHITE;

        Stone otherSide() {
            switch (this) {
                case BLACK: return Stone.WHITE;
                case WHITE: return Stone.BLACK;
                default: throw new UnsupportedOperationException();
            }
        }
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

    static int getCountCaptured(Stone[][] grid, int r, int c, Stone stone) {
        if (stone == Stone.EMPTY) {
            return 0;
        }

        grid[r][c] = stone;

        int[][] delta = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };
        Set<Pair> totalVisited = new HashSet<>();
        for (int i = 0; i < 4; ++i) {
            int nr = r + delta[i][0];
            int nc = c + delta[i][1];

            if (!valid(grid, nr, nc))
                continue;

            if (totalVisited.contains(new Pair(nr, nc)))
                continue;

            if (grid[nr][nc] != stone.otherSide())
                continue;

            Set<Pair> visited = new HashSet<>();
            if (surroundedByOpponent(grid, nr, nc, visited)) {
                totalVisited.addAll(visited);
            }
        }

        grid[r][c] = Stone.EMPTY;

        return totalVisited.size();
    }

    static boolean valid(Stone[][] grid, int r, int c) {
        return (0 <= r && r < grid.length) && (0 <= c && c < grid[0].length);
    }

    static boolean surroundedByOpponent(Stone[][] grid, int r, int c, Set<Pair> visited) {
        Stone stone = grid[r][c];
        Pair pos = new Pair(r, c);
        visited.add(pos);

        boolean surrounded = true;

        int[][] delta = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };
        for (int i = 0; i < 4; ++i) {
            int nr = r + delta[i][0];
            int nc = c + delta[i][1];
            if (!valid(grid, nr, nc))
                continue;

            if (visited.contains(new Pair(nr, nc)))
                continue;

            if (grid[nr][nc] == Stone.EMPTY) {
                surrounded = false;
                break;
            }

            if (grid[nr][nc] == stone && !surroundedByOpponent(grid, nr, nc, visited)) {
                surrounded = false;
                break;
            }
        }

        return surrounded;
    }

    static void solve(String strGrid, int r, int c, Stone stone) {
        InputStream is = new ByteArrayInputStream(strGrid.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        Scanner scanner = new Scanner(br);

        Stone[][] grid = new Stone[10][10];
        Stone[] type = {Stone.EMPTY, Stone.BLACK, Stone.WHITE};
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                grid[i][j] = type[scanner.nextInt()];
            }
        }

        int countCaptured = getCountCaptured(grid, r, c, stone);
        System.out.printf("Count captured = %d\n", countCaptured);
    }

    public static void main(String[] args) {
        {
            String strGrid =
                    "0 0 2 0 0 0 0 0 0 0 " +
                    "2 2 1 2 2 0 0 0 0 0 " +
                    "1 1 1 1 1 2 0 0 0 0 " +
                    "2 2 2 2 0 0 0 0 0 0 " +
                    "1 1 1 1 0 0 0 0 0 2 " +
                    "0 0 0 0 0 0 0 0 2 1 " +
                    "0 0 0 0 0 0 0 2 1 1 " +
                    "0 0 0 0 0 2 0 1 1 1 " +
                    "0 0 0 0 2 1 1 2 1 1 " +
                    "0 0 0 0 2 1 2 0 2 1 ";

            solve(strGrid, 7, 6, Stone.WHITE);
        }

        {
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

            solve(strGrid, 3, 4, Stone.BLACK);
            solve(strGrid, 7, 6, Stone.WHITE);
        }

        {
            String strGrid =
                    "0 1 1 1 1 1 1 1 1 1 " +
                    "1 1 1 1 1 1 1 1 1 1 " +
                    "1 1 1 1 1 1 1 1 1 1 " +
                    "1 1 1 1 1 1 1 1 1 1 " +
                    "1 1 1 1 1 1 1 1 1 1 " +
                    "1 1 1 1 1 1 1 1 1 1 " +
                    "1 1 1 1 1 1 1 1 1 1 " +
                    "1 1 1 1 1 1 1 1 1 1 " +
                    "1 1 1 1 1 1 1 1 1 1 " +
                    "1 1 1 1 1 1 1 1 1 1 ";

            solve(strGrid, 0, 0, Stone.WHITE);
        }

        {
            String strGrid =
                    "0 0 0 0 0 0 0 0 0 0 " +
                    "0 0 0 0 0 0 0 0 0 0 " +
                    "0 0 0 0 2 2 2 0 0 0 " +
                    "0 0 0 2 1 1 1 2 0 0 " +
                    "0 0 0 2 1 0 1 2 0 0 " +
                    "0 0 0 0 2 1 1 2 0 0 " +
                    "0 0 0 0 0 2 2 0 0 0 " +
                    "0 0 0 0 0 0 0 0 0 0 " +
                    "0 0 0 0 0 0 0 0 0 0 " +
                    "0 0 0 0 0 0 0 0 0 0 ";

            solve(strGrid, 4, 5, Stone.WHITE);
        }
    }
}
