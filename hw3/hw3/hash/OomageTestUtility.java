package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        int[] buckets = new int [M];
        for (int i = 0; i < M; i++) {
            buckets[i] = 0;
        }
        for (int j = 0; j < oomages.size(); j++) {
            int bucketNum = (oomages.get(j).hashCode() & 0x7FFFFFFF) % M;
            buckets[bucketNum]++;
        }
        for(int k = 0; k < M; k++) {
            if (!inRange(buckets[k], oomages.size())) return false;
        }
        return true;
    }

    private static boolean inRange(int bucket, int N) {
        return bucket <= (N / 2.5) && bucket >= (N / 50);
    }
}
