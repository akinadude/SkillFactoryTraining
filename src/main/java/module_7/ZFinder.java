package module_7;

import java.util.ArrayList;
import java.util.List;

import Log.Logger;

public class ZFinder {

    private final static int mod = 10000000 - 3;
    private final static int p = 3;

    //index 0: hash of prefix of length 0
    //index 1: hash of prefix of length 1
    //...
    private static final List<Long> prefixHashes = new ArrayList<>();

    public static int[] findZ(String s) {
        int[] result = new int[s.length()];
        calculatePrefixHashes(s);

        for (int i = 0; i < s.length(); i++) {
            int zValue = findZForElement(i, s);
            Logger.log("z value %d", zValue);

            result[i] = zValue;
        }

        Logger.logArray(result);

        return result;
    }

    private static int findZForElement(int index, String s) {
        if (index == 0) {
            return s.length();
        }

        int start = index;
        int offset = Math.min(index + index, s.length() - index); //exclusive
        int end = start + offset;

        while (start < end) {
            end = start + offset;
            Logger.log("start, end, substring = %d %d %s", start, end, s.substring(start, end));

            long substringHash = prefixHashes.get(end) - prefixHashes.get(start);
            long prefixHashComplemented = (prefixHashes.get(offset) * power(p, start, mod)) % mod;

            if (substringHash == prefixHashComplemented) {
                return offset;
            }

            offset = offset - 1;
        }

        return 0;
    }

    private static void calculatePrefixHashes(String s) {
        for (int i = 0; i < s.length() + 1; i++) {
            String prefix = s.substring(0, i);
            long hash = calculatePolynomialHash(prefix, p, mod);
            prefixHashes.add(hash);

            Logger.log("Accumulated prefix hashes " + prefixHashes);
        }
    }

    public static int calculatePolynomialHash(String s, int p, int mod) {
        int res = 0;

        for (int i = 0; i < s.length(); i++) {
            res += power(p, i, mod) * toInt(s.charAt(i));
            res %= mod;
        }

        return res;
    }

    private static int toInt(char c) {
        return c - 'a' + 13;
    }

    private static int power(int x, int n, int mod) {
        int res = 1;

        for (int i = 0; i < n; i++) {
            res *= x;
            res %= mod;
        }

        return res;
    }
}
