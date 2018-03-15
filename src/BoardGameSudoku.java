/**
 * https://ide.geeksforgeeks.org/ajCO9oMNG8
 */

public class BoardGameSudoku {

    static class Position {
        public final int row;
        public final int col;
        Position(int row, int col) {
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
            Position other = (Position)o;
            if (other == null) return false;
            if (other == this) return true;
            return (row == other.row && col == other.col);
        }
    }

    static class Sudoku {
        static final int M = 3;
        static final int N = 9;
        int[][] table = new int[N][N];

        Sudoku(int[] input) {
            for (int i = 0; i < N * N; ++i) {
                table[i / N][i % N] = input[i];
            }
        }

        boolean solve() {
            Position p = findEmptySlot();
            if (p == null) {
                return true;
            }

            for (int i = 1; i <= 9; ++i) {
                if (safe(p, i)) {
                    table[p.row][p.col] = i;
                    if (solve()) {
                        return true;
                    }
                    table[p.row][p.col] = 0;
                }
            }

            return false;
        }

        Position findEmptySlot() {
            for (int i = 0; i < N; ++i) {
                for (int j = 0; j < N; ++j) {
                    if (table[i][j] == 0) {
                        return new Position(i, j);
                    }
                }
            }

            return null;
        }

        boolean safe(Position p, int number) {
            if (!rowSafe(p, number)) return false;
            if (!colSafe(p, number)) return false;
            if (!localSafe(p, number)) return false;
            return true;
        }

        boolean rowSafe(Position p, int number) {
            for (int i = 0; i < N; ++i) {
                if (table[p.row][i] == number)
                    return false;
            }
            return true;
        }

        boolean colSafe(Position p, int number) {
            for (int i = 0; i < N; ++i) {
                if (table[i][p.col] == number)
                    return false;
            }
            return true;
        }

        boolean localSafe(Position p, int number) {
            int cellRow = p.row / M;
            int cellCol = p.col / M;

            for (int i = 0; i < M; ++i) {
                for (int j = 0; j < M; ++j) {
                    int r = cellRow * M + i;
                    int c = cellCol * M + j;
                    if (table[r][c] == number)
                        return  false;
                }
            }

            return true;
        }

        void display() {
            for (int i = 0; i < N; ++i) {
                for (int j = 0; j < N; ++j) {
                    System.out.printf("%d ", table[i][j]);
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        int[] input = {
                3, 0, 6, 5, 0, 8, 4, 0, 0,
                5, 2, 0, 0, 0, 0, 0, 0, 0,
                0, 8, 7, 0, 0, 0, 0, 3, 1,
                0, 0, 3, 0, 1, 0, 0, 8, 0,
                9, 0, 0, 8, 6, 3, 0, 0, 5,
                0, 5, 0, 0, 9, 0, 6, 0, 0,
                1, 3, 0, 0, 0, 0, 2, 5, 0,
                0, 0, 0, 0, 0, 0, 0, 7, 4,
                0, 0, 5, 2, 0, 6, 3, 0, 0
        };

        Sudoku game = new Sudoku(input);
        game.display();

        System.out.println();

        if (game.solve())
            game.display();
    }
}
