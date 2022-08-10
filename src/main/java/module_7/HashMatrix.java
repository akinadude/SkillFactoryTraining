package module_7;

import Log.Logger;

public class HashMatrix {
    private int[][] matrix;

    private final int mod = 1000000 - 3;
    private final int p = 2;//213
    private final int q = 3;//117

    private long[][] prefixHashes;

    public HashMatrix(int[][] matrix) {
        this.matrix = matrix;

        int n = matrix.length;
        int m = matrix[0].length;

        prefixHashes = new long[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                calculatePrefixHash(0, i, 0, j, p, q, mod);

                Logger.log("up to i: %d, up to j: %d (inclusively)", i, j);
                Logger.logMatrix(prefixHashes);
            }
        }
    }

    // iEnd, jEnd, iEnd2, jEnd2 are exclusive indices
    public boolean isEqual(int iStart, int iEnd, int jStart, int jEnd,
                           int iStart2, int iEnd2, int jStart2, int jEnd2) {
        long aboveMatrixHash = prefixHashes[iStart][jEnd - 1];
        long toTheLeftMatrixHash = prefixHashes[iEnd - 1][jStart];
        long smallCornerSubMatrixHash = prefixHashes[iStart][jStart];
        long bigCornerSubMatrixHash = prefixHashes[iEnd - 1][jEnd - 1];

        long aboveMatrixHash2 = prefixHashes[iStart2][jEnd2 - 1];
        long toTheLeftMatrixHash2 = prefixHashes[iEnd2 - 1][jStart2];
        long smallCornerSubMatrixHash2 = prefixHashes[iStart2][jStart2];
        long bigCornerSubMatrixHash2 = prefixHashes[iEnd2 - 1][jEnd2 - 1];

        long subMatrixHash = bigCornerSubMatrixHash - toTheLeftMatrixHash - aboveMatrixHash + smallCornerSubMatrixHash;
        long subMatrixHash2 = bigCornerSubMatrixHash2 - toTheLeftMatrixHash2 - aboveMatrixHash2 + smallCornerSubMatrixHash2;
        Logger.log("bigCornerSubMatrixHash %d, toTheLeftMatrixHash %d, aboveMatrixHash %d, smallCornerSubMatrixHash %d", bigCornerSubMatrixHash, toTheLeftMatrixHash, aboveMatrixHash, smallCornerSubMatrixHash);
        Logger.log("bigCornerSubMatrixHash2 %d, toTheLeftMatrixHash2 %d, aboveMatrixHash2 %d, smallCornerSubMatrixHash2 %d", bigCornerSubMatrixHash2, toTheLeftMatrixHash2, aboveMatrixHash2, smallCornerSubMatrixHash2);
        Logger.log("subMatrixHash: %d, subMatrixHash2: %d", subMatrixHash, subMatrixHash2);

        long iMultiplier;
        long jMultiplier;
        if (iStart < iStart2) {
            // left part needs multiplying
            iMultiplier = power(p, iStart2 - iStart);
            Logger.log("Left part needs multiplying by i on %d", iMultiplier);
            subMatrixHash *= iMultiplier;
        } else if (iStart > iStart2) {
            // right part needs multiplying
            iMultiplier = power(p, iStart - iStart2);
            Logger.log("Right part needs multiplying by i on %d", iMultiplier);
            subMatrixHash2 *= iMultiplier;
        }
        if (jStart < jStart2) {
            // left part needs multiplying
            jMultiplier = power(q, jStart2 - jStart);
            Logger.log("Left part needs multiplying by j on %d", jMultiplier);
            subMatrixHash *= jMultiplier;
        } else if (jStart > jStart2) {
            // right part needs multiplying
            jMultiplier = power(q, jStart - jStart2);
            Logger.log("Right part needs multiplying by j on %d", jMultiplier);
            subMatrixHash2 *= jMultiplier;
        }

        Logger.log("Considering multipliers, subMatrixHash: %d, subMatrixHash2: %d", subMatrixHash, subMatrixHash2);
        return subMatrixHash == subMatrixHash2;
    }

    // iEnd, jEnd are inclusive indices
    private int calculatePrefixHash(int iStart, int iEnd, int jStart, int jEnd, int p, int q, int mod) {
        int res = 0;

        for (int i = iStart; i < iEnd; i++) {

            for (int j = jStart; j < jEnd; j++) {
                res += power(p, i) * power(q, j) * matrix[i][j];
                res %= mod;

            }
        }

        prefixHashes[iEnd][jEnd] = res;

        return res;
    }

    private int power(int x, int n) {
        int res = 1;

        for (int i = 0; i < n; i++) {
            res *= x;
        }

        return res;
    }
}
