import java.util.Arrays;
import java.util.Scanner;

public class BoardGameTicTacToe {
    static class TicTacToe {
        enum State { WAITING_INPUT, DONE }

        enum Stone {
            EMPTY, O, X;

            Stone otherSide() {
                switch (this) {
                    case O: return X;
                    case X: return O;
                    default: throw new UnsupportedOperationException();
                }
            }
        }

        private final Stone[][] table;
        private Stone currentTurn = Stone.X;
        private State state = State.WAITING_INPUT;
        private int stoneCount = 0;
        private Stone winner = Stone.EMPTY;

        public TicTacToe(int size) {
            table = new Stone[size][size];
            for (Stone[] each : table) {
                Arrays.fill(each, Stone.EMPTY);
            }
        }

        public Stone whoseTurn() {
            return currentTurn;
        }

        public Stone whoWon() {
            return winner;
        }

        public boolean placeStone(int r, int c) {
            if (state != State.WAITING_INPUT)
                return false;

            if (!valid(r, c))
                return false;

            if (!available(r, c))
                return false;

            table[r][c] = currentTurn;
            ++stoneCount;
            if (complete(r, c)) {
                winner = currentTurn;
                state = State.DONE;
            } else if(full()) {
                state = State.DONE;
            } else {
                toggleTurn();
            }

            return true;
        }

        private boolean full() {
            return stoneCount == table.length * table.length;
        }

        private boolean valid(int r, int c) {
            int n = table.length;
            return (0 <= r && r < n) && (0 <= c && c < n);
        }

        private boolean available(int r, int c) {
            return table[r][c] == Stone.EMPTY;
        }

        private boolean complete(int r, int c) {
            return
                rowComplete(r, c) ||
                colComplete(r, c) ||
                diagonalLt2RbComplete(r, c) ||
                diagonalRt2LbComplete(r, c);
        }

        private boolean rowComplete(int r, int c) {
            Stone stone = table[r][c];
            for (int i = 0; i < table.length; ++i) {
                if (table[r][i] != stone)
                    return false;
            }

            return true;
        }

        private boolean colComplete(int r, int c) {
            Stone stone = table[r][c];
            for (int i = 0; i < table.length; ++i) {
                if (table[i][c] != stone)
                    return false;
            }

            return true;
        }

        private boolean diagonalLt2RbComplete(int r, int c) {
            Stone stone = table[r][c];
            for (int i = 0; i < table.length; ++i) {
                if (table[i][i] != stone)
                    return false;
            }

            return true;
        }

        private boolean diagonalRt2LbComplete(int r, int c) {
            Stone stone = table[r][c];
            for (int i = 0; i < table.length; ++i) {
                if (table[table.length - 1 - i][i] != stone)
                    return false;
            }

            return true;
        }

        private void toggleTurn() {
            currentTurn = currentTurn.otherSide();
        }

        public void display() {
            if (state == State.WAITING_INPUT) {
                System.out.printf("%s's turn:\n", currentTurn.toString());
            } else {
                switch (winner) {
                    case EMPTY:
                        System.out.println("Draw!");
                        break;
                    case O:
                        System.out.println("O won!");
                        break;
                    case X:
                        System.out.println("X won!");
                        break;
                }
            }

            System.out.print("  ");
            for (int i = 0; i < table.length; ++i) {
                System.out.printf("%d ", i);
            }
            System.out.println();

            for (int i = 0; i < table.length; ++i) {
                for (int j = 0; j  < table.length; ++j) {
                    if (j == 0)
                        System.out.printf("%d ", i);

                    Stone s = table[i][j];
                    char ch = '·';
                    switch (s) {
                        case X: ch = 'X'; break;
                        case O: ch = 'O'; break;
                    }

                    System.out.printf("%c ", ch);
                }
                System.out.println();
            }
        }

        public boolean done() {
            return state == State.DONE;
        }

        public State state() {
            return state;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        TicTacToe game = new TicTacToe(5);

        while (!game.done()) {
            game.display();
            System.out.println("Enter row and col:");
            int r = scanner.nextInt();
            int c = scanner.nextInt();
            if (!game.placeStone(r, c)) {
                switch (game.state()) {
                    case WAITING_INPUT:
                        System.out.println("Invalid input!");
                        break;
                }
            }
        }

        game.display();
    }
}
