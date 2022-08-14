package module_7;

import Log.Logger;

public class SubmatrixOneErrorFinder {

    private static final int mod = 1000000 - 3;
    private static final int p = 2;
    private static final int q = 3;

    private static int[][] sPrefixHashes;
    private static int[][] tPrefixHashes;

    // Returns if t is a submatrix of s
    public static boolean isSubMatrix(int[][] s, int[][] t) {
        int sHeight = s.length;
        int sWidth = s[0].length; //assume that the second dimension is equal for each array.

        int tHeight = t.length;
        int tWidth = t[0].length; //assume that the second dimension is equal for each array.

        sPrefixHashes = new int[sHeight + 1][sWidth + 1];
        tPrefixHashes = new int[tHeight + 1][tWidth + 1];

        calculatePrefixHashes(s, sPrefixHashes);
        calculatePrefixHashes(t, tPrefixHashes);

        int tHash = tPrefixHashes[tHeight][tWidth];

        Logger.log("tHeight = %d, tWidth = %d", tHeight, tWidth);

        //traversing s matrix
        for (int i = 0; i < sHeight; i++) {
            for (int j = 0; j < sWidth; j++) {
                if (i + tHeight < sHeight + 1 && j + tWidth < sWidth + 1) {
                    Logger.log("i = %d, j = %d", i, j);

                    int aboveMatrixHash = sPrefixHashes[i][j + tWidth];
                    int toTheLeftMatrixHash = sPrefixHashes[i + tHeight][j];
                    int smallCornerSubMatrixHash = sPrefixHashes[i][j];
                    int bigCornerSubMatrixHash = sPrefixHashes[i + tHeight][j + tWidth];
                    int sSubMatrixHash = bigCornerSubMatrixHash - aboveMatrixHash - toTheLeftMatrixHash + smallCornerSubMatrixHash;

                    int tHashComplemented = tHash * power(p, i) * power(q, j);
                    Logger.log("sSubMatrixHash = %d, tHashComplemented = %d", sSubMatrixHash, tHashComplemented);

                    if (sSubMatrixHash == tHashComplemented) {
                        return true;
                    }

                    boolean areEqualWithOneError = dropElementAndCompareMatrices(s, t, sSubMatrixHash, tHash, i, j, tHeight, tWidth);
                    if (areEqualWithOneError) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private static boolean dropElementAndCompareMatrices(
            int[][] s, int[][] t,
            int sSubMatrixHash, int tHash,
            int tStartRow, int tStartColumn,
            int tHeight, int tWidth) {

        Logger.log("dropElementAndCompareMatrices");

        for (int i = 0; i < tHeight; i++) {
            for (int j = 0; j < tWidth; j++) {
                Logger.log("i = %d, j = %d", i, j);

                int sRow = i + tStartRow;
                int sColumn = j + tStartColumn;
                Logger.log("s element = %d", s[sRow][sColumn]);
                int sSubMatrixHashWithoutElement = sSubMatrixHash - power(p, sRow) * power(q, sColumn) * s[sRow][sColumn];

                Logger.log("t element = %d", t[i][j]);
                int tHashWithoutElement = tHash - power(p, i) * power(q, j) * t[i][j];
                int tHashWithoutElementComplemented = tHashWithoutElement * power(p, tStartRow) * power(q, tStartColumn);

                Logger.log("sSubMatrixHashWithoutElement: %d, tHashWithoutElementComplemented: %d", sSubMatrixHashWithoutElement, tHashWithoutElementComplemented);

                if (sSubMatrixHashWithoutElement == tHashWithoutElementComplemented) {
                    return true;
                }
            }
        }

        return false;
    }

    private static void calculatePrefixHashes(int[][] matrix, int[][] prefixHashes) {
        int n = matrix.length + 1;
        int m = matrix[0].length + 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                calculatePrefixHash(matrix, prefixHashes, 0, i, 0, j, p, q, mod);
            }
        }

        Logger.log("Prefix hashes");
        Logger.logMatrix(prefixHashes);
    }

    // iEnd, jEnd are inclusive indices
    private static int calculatePrefixHash(int[][] matrix, int[][] prefixHashes,
                                           int iStart, int iEnd, int jStart, int jEnd,
                                           int p, int q, int mod) {
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

    private static int power(int x, int n) {
        int res = 1;

        for (int i = 0; i < n; i++) {
            res *= x;
        }

        return res;
    }
}
