package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    private int exprTimes;
    private int width;
    private  Percolation[] percolations;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0){
            throw new java.lang.IllegalArgumentException();
        }
        exprTimes = T;
        width = N;
        percolations = new Percolation[T];
        for(int i = 0; i < T; i += 1) {
            percolations[i] = pf.make(N);
            percolate(percolations[i]);
        }
    }

    private void percolate(Percolation pn) {
        if (!pn.percolates()) {
            int rand = generateNextRandom(pn);
            int[] pos = mapPn2Pos(rand);
            pn.open(pos[0], pos[1]);
            percolate(pn);
        }
    }

    private int[] mapPn2Pos(int n) {
        int row = n / width;
        int col = n % width;
        return new int[] {row, col};
    }

    private int generateNextRandom(Percolation pn) {
        int rand = StdRandom.uniform(width * width);
        int [] pos = mapPn2Pos(rand);
        if (pn.isOpen(pos[0], pos[1])) {
            return generateNextRandom(pn);
        }
        return rand;
    }

    private int[] openSites() {
        int[] sitesList = new int[exprTimes];
        for(int i = 0; i < exprTimes; i += 1) {
            sitesList[i] = percolations[i].numberOfOpenSites()/(width * width);
        }
        return sitesList;
    }

    public double mean() {
        return StdStats.mean(openSites());
    }

    public double stddev() {
        return StdStats.stddev(openSites());
    }

    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(exprTimes);
    }

    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(exprTimes);
    }
}
