package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static boolean OPEN = true;
    private static boolean BLOCK = false;
    private static boolean FULL = true;
    private static boolean EMPTY = false;

    private int width;
    private int openSites;
    private boolean percolated;
    private WeightedQuickUnionUF uf;
    private boolean[][] grids;
    private boolean[][] fullInd;
    private boolean[][] toBottom;

    public Percolation(int N) {
        if (N <= 0) throw new java.lang.IllegalArgumentException();
        width = N;
        openSites = 0;
        percolated = false;
        uf = new WeightedQuickUnionUF(N * N);

        grids = new boolean[N][];
        fullInd = new boolean[N][];
        toBottom = new boolean[N][];
        for (int i = 0; i < N; i += 1) {
            grids[i] = new boolean[N];
            fullInd[i] = new boolean[N];
            toBottom[i] = new boolean[N];
            for(int j = 0; j < N; j += 1) {
                grids[i][j] = BLOCK;
                fullInd[i][j] = EMPTY;
                toBottom[i][j] = false;
            }
        }
    }

    private boolean boundTest(int row, int col) {
        if (row < 0 || row > width - 1 || col < 0 || col > width - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return true;
    }

    private int mapPos2uf(int row, int col) {
        return row * width + col;
    }

    private int[] mapUf2Pos(int n) {
      int[] pos = new int[2];
      pos[0] = n / width;
      pos[1] = n % width;
      return pos;
    };

    private int[] find(int row, int col) {
        int pos = mapPos2uf(row, col);
        int findUf = uf.find(pos);
        return mapUf2Pos(findUf);
    }

    private void union(int row1, int col1, int row2, int col2) {
        if (isOpen(row1, col1) && isOpen(row2, col2)) {
            int ufPos1 = mapPos2uf(row1, col1);
            int ufPos2 = mapPos2uf(row2, col2);
            boolean full = isFull(row1, col1) || isFull(row2, col2);
            int[] findPos1 = find(row1, col1);
            int[] findPos2 = find(row2, col2);
            boolean bottom = toBottom[findPos1[0]][findPos1[1]] || toBottom[findPos2[0]][findPos2[1]];
            uf.union(ufPos1, ufPos2);
            int[] pos = find(row1, col1);
            fullInd[pos[0]][pos[1]] = full;
            toBottom[pos[0]][pos[1]] = bottom;
            if (bottom && full) percolated = true;
        }
    }

    private void connectByCol(int row, int col) {
        if (col == 0) {
            union(row, col, row, col + 1);
        } else if (col == width - 1) {
            union(row, col, row, col - 1);
        } else {
            union(row, col, row, col - 1);
            union(row, col, row, col + 1);
        }
    }

    /*
        * connect border
     */
    private void connect(int row, int col) {
        int ufPos = mapPos2uf(row, col);
        if (row == 0) {
            union(row, col, row + 1, col);
            connectByCol(row, col);

        } else if (row == width - 1) {
            union(row, col, row - 1, col);
            connectByCol(row, col);
        } else {
            union(row, col, row - 1, col);
            union(row, col, row + 1, col);
            connectByCol(row, col);
        }
    }

    private void watering(int row, int col) {
        if (isOpen(row, col)) {
            int[] pos = find(row, col);
            if (row == 0) {
                fullInd[row][col] = FULL;
                fullInd[pos[0]][pos[1]] = FULL;
            } else {
                if(isFull(pos[0], pos[1])) {
                    fullInd[row][col] = FULL;
                }
            }
        }
    }

    public void open(int row, int col) {
        if (boundTest(row, col)) {
            if (!isOpen(row, col)) {
                openSites++;
                grids[row][col] = OPEN;
                connect(row, col);
                watering(row, col);
                if (row == width - 1) {
                    int[] pos = find(row, col);
                    toBottom[pos[0]][pos[1]] = true;
                    if (isFull(pos[0], pos[1])) percolated = true;
                };
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (boundTest(row, col)) {
            return grids[row][col];
        }
        return false;
    }

    public boolean isFull(int row, int col) {
        int[] pos = find(row, col);
        return fullInd[pos[0]][pos[1]];
    }

    public int numberOfOpenSites() { return openSites; }

    public boolean percolates() {
        return percolated;
    }
}
