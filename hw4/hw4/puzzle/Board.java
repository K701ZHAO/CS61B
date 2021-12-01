package hw4.puzzle;
import edu.princeton.cs.algs4.Stack;

public class Board implements WorldState {

    private int[][] tiles;
    private int N;
    private int emptyRow;
    private int emptyCol;

    public Board(int[][] tiles) {
        N = tiles.length;
        this.tiles = new int[N][];
        for(int i = 0; i < N; i++) {
            this.tiles[i] = new int[N];
            for(int j = 0; j < N; j++) {
                this.tiles[i][j] = tiles[i][j];
                if (tiles[i][j] == 0) {
                    emptyRow = i;
                    emptyCol = j;
                }
            }
        }
    }

    public int tileAt(int i, int j) {
        if (i < 0 || i >= size() || j < 0 || j >= size()) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return tiles[i][j];
    }

    public int size() {
        return N;
    }

    public Iterable<WorldState> neighbors() {
        Stack<WorldState> stack = new Stack<>();
        if (emptyRow == 0) {
            moveDown(stack);
        } else if (emptyRow == N - 1) {
            moveUp(stack);
        } else {
            moveDown(stack);
            moveUp(stack);
        }

        if (emptyCol == 0) {
            moveRight(stack);
        } else if (emptyCol == N - 1) {
            moveLeft(stack);
        } else {
            moveRight(stack);
            moveLeft(stack);
        }

        tiles[emptyRow][emptyCol] = 0;
        return stack;
    }

    private void moveRight(Stack<WorldState> stack) {
        tiles[emptyRow][emptyCol] = tileAt(emptyRow, emptyCol + 1);
        tiles[emptyRow][emptyCol + 1] = 0;
        stack.push(new Board(tiles));
        tiles[emptyRow][emptyCol + 1] = tileAt(emptyRow, emptyCol);
    }

    private void moveLeft(Stack<WorldState> stack) {
        tiles[emptyRow][emptyCol] = tileAt(emptyRow, emptyCol - 1);
        tiles[emptyRow][emptyCol - 1] = 0;
        stack.push(new Board(tiles));
        tiles[emptyRow][emptyCol - 1] = tileAt(emptyRow, emptyCol);
    }

    private void moveUp(Stack<WorldState> stack) {
        tiles[emptyRow][emptyCol] = tileAt(emptyRow - 1, emptyCol);
        tiles[emptyRow - 1][emptyCol] = 0;
        stack.push(new Board(tiles));
        tiles[emptyRow - 1][emptyCol] = tileAt(emptyRow, emptyCol);
    }

    private void moveDown(Stack<WorldState> stack) {
        tiles[emptyRow][emptyCol] = tileAt(emptyRow + 1, emptyCol);
        tiles[emptyRow + 1][emptyCol] = 0;
        stack.push(new Board(tiles));
        tiles[emptyRow + 1][emptyCol] = tileAt(emptyRow, emptyCol);
    }

    public int hamming() {
        int dist = 0;
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if (tileAt(i, j) != goalAt(i, j) && tileAt(i, j) != 0) {
                    dist += 1;
                }
            }
        }
        return dist;
    }

    private int goalAt(int i, int j) {
        return i * N + j + 1;
    }

    public int manhattan() {
        int dist = 0;
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                dist += distToGoal(i, j);
            }
        }
        return dist;
    }

    private int distToGoal(int i, int j) {
        int num = tileAt(i, j);
        if (num == 0) return 0;
        int goalRow = (num - 1) / N;
        int goalCol = (num - 1) % N;
        return Math.abs(i - goalRow) + Math.abs(j - goalCol);
    }

    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        Board other = (Board) o;
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if (other.tileAt(i, j) != tileAt(i, j))
                    return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return 0;
    }

    /** Returns the string representation of the board.
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
