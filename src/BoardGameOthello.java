import java.util.*;

public class BoardGameOthello {

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

    static class Othello {
        public enum State {
            WAITING_INPUT,
            WAITING_PASS,
            DONE,
        }

        enum Stone {
            EMPTY, BLACK, WHITE;

            Stone otherSide() {
                switch (this) {
                    case BLACK: return Stone.WHITE;
                    case WHITE: return Stone.BLACK;
                    default: throw new IllegalArgumentException();
                }
            }
        }

        private final Stone[][] slots;
        private final Set<Position> candidates = new HashSet<>();
        private Stone currentTurn = Stone.BLACK;
        private State state = State.WAITING_INPUT;

        public Othello(int rows, int cols) {
            slots = new Stone[rows][cols];
            for (int i = 0; i < rows; ++i) {
                for (int j = 0; j < cols; ++j) {
                    slots[i][j] = Stone.EMPTY;
                }
            }

            int mR = rows / 2;
            int mC = cols / 2;

            slots[mR - 1][mC - 1] = Stone.BLACK;
            slots[mR - 1][mC] = Stone.WHITE;
            slots[mR][mC - 1] = Stone.WHITE;
            slots[mR][mC] = Stone.BLACK;

            updateCandidates();
        }

        public Stone whoseTurn() {
            return currentTurn;
        }

        public Collection<Position> candidates() {
            return Collections.unmodifiableCollection(candidates);
        }

        public boolean placeStone(int r, int c) {
            if (state != State.WAITING_INPUT) {
                return false;
            }

            if (!candidates.contains(new Position(r, c))) {
                return false;
            }

            flipStones(r, c);
            toggleTurn();
            updateCandidates();

            if (candidates.isEmpty()) {
                state = State.WAITING_PASS;
                return false;
            }

            return true;
        }

        public void pass() {
            if (state == State.WAITING_PASS) {
                toggleTurn();
                updateCandidates();

                if (candidates.isEmpty()) {
                    state = State.DONE;
                } else {
                    state = State.WAITING_INPUT;
                }
            }
        }

        public State state() {
            return state;
        }

        private boolean valid(int r, int c) {
            return (0 <= r && r < slots.length) && (0 <= c && c < slots[0].length);
        }

        private void toggleTurn() {
            currentTurn = currentTurn.otherSide();
        }

        public Map<Stone, Integer> countSlots() {
            Map<Stone, Integer> counts = new HashMap<>();
            for (int i = 0; i < slots.length; ++i) {
                for (int j = 0; j < slots[0].length; ++j) {
                    Stone s = slots[i][j];
                    counts.put(s, counts.getOrDefault(s, 0) + 1);
                }
            }

            return counts;
        }

        private void flipStones(int r, int c) {
            slots[r][c] = currentTurn;

            int[][] directions = { {-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1} };
            for (int i = 0; i < 8; ++i) {
                int dr = directions[i][0];
                int dc = directions[i][1];

                int nr = r + dr;
                int nc = c + dc;

                LinkedList<Position> stonesToFlip = new LinkedList<>();
                while (valid(nr, nc) && slots[nr][nc] == currentTurn.otherSide()) {
                    stonesToFlip.add(new Position(nr, nc));
                    nr += dr;
                    nc += dc;
                }

                if (valid(nr, nc) && slots[nr][nc] == currentTurn && !stonesToFlip.isEmpty()) {
                    for (Position p : stonesToFlip) {
                        slots[p.row][p.col] = currentTurn;
                    }
                }
            }
        }

        private void updateCandidates() {
            candidates.clear();

            for (int i = 0; i < slots.length; ++i) {
                for (int j = 0; j < slots[0].length; ++j) {
                    if (slots[i][j] != Stone.EMPTY) continue;;
                    if (canFlip(i, j)) {
                        candidates.add(new Position(i, j));
                    }
                }
            }
        }

        private boolean canFlip(int r, int c) {
            int[][] directions = { {-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1} };
            for (int i = 0; i < 8; ++i) {
                int dr = directions[i][0];
                int dc = directions[i][1];

                int nr = r + dr;
                int nc = c + dc;

                int count = 0;
                while (valid(nr, nc) && slots[nr][nc] == currentTurn.otherSide()) {
                    ++count;
                    nr += dr;
                    nc += dc;
                }

                if (valid(nr, nc) && slots[nr][nc] == currentTurn && count > 0) {
                    return true;
                }
            }

            return false;
        }

        public void display(boolean markCandidates) {
            System.out.printf("Turn of %s:\n", currentTurn.toString());
            for (int j = 0; j < slots[0].length; ++j) {
                if (j == 0)
                    System.out.print("  ");

                System.out.printf("%d ", j);
            }
            System.out.println();
            for (int i = 0; i < slots.length; ++i) {
                for (int j = 0; j < slots[0].length; ++j) {
                    if (j == 0) {
                        System.out.printf("%d ", i);
                    }

                    char ch = '·';

                    if (markCandidates && candidates.contains(new Position(i, j))) {
                        ch = '?';
                    }

                    switch(slots[i][j]) {
                        case EMPTY:
                            break;
                        case BLACK:
                            ch = 'B';
                            break;
                        case WHITE:
                            ch = 'W';
                            break;
                    }

                    System.out.printf("%c ", ch);
                }
                System.out.println();
            }
        }

        public boolean done() {
            return state == State.DONE;
        }

        public Stone whoWon() {
            if (state != State.DONE) {
                return Stone.EMPTY;
            }

            Map<Stone, Integer> count = countSlots();
            int blacks = count.getOrDefault(Stone.BLACK, 0);
            int whites = count.getOrDefault(Stone.WHITE, 0);
            if (blacks > whites) {
                return Stone.BLACK;
            } else if (blacks < whites) {
                return Stone.WHITE;
            } else {
                return Stone.EMPTY;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Othello game = new Othello(10, 10);

        while (!game.done()) {
            game.display(true);

            System.out.println("Enter row and col:");
            int r = scanner.nextInt();
            int c = scanner.nextInt();

            if (!game.placeStone(r, c)) {
                switch (game.state()) {
                    case WAITING_INPUT:
                        System.out.println("Invalid position!");
                        break;
                    case WAITING_PASS:
                        System.out.printf("No place available for %s, pass!\n", game.whoseTurn().toString());
                        game.pass();
                        if (game.state() == Othello.State.DONE) {
                            System.out.printf("No place available for %s, pass!\n", game.whoseTurn().toString());
                        }
                        break;
                }
            }
        }

        Othello.Stone winner = game.whoWon();
        System.out.printf("%s won!\n", winner);
    }
}
