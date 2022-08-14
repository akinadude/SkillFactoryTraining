package module_7;

import java.util.ArrayList;
import java.util.List;

import Log.Logger;

public class SubstringOneErrorFinder {

    private final static int mod = 10000000 - 3;
    private final static int p = 3;

    //index 0: hash of prefix of length 0
    //index 1: hash of prefix of length 1
    //...
    private static final List<Long> sPrefixHashes = new ArrayList<>();
    private static final List<Long> tPrefixHashes = new ArrayList<>();

    // One error is allowed: assume s = "abcdef", t is one of "cde", "zde", "cze", "cdz"
    // Each of t values is a substring of s for this function.
    public static boolean isSubString(String s, String t) {
        calculatePrefixHashes(s, sPrefixHashes);
        calculatePrefixHashes(t, tPrefixHashes);

        int tLength = t.length();
        long tHash = tPrefixHashes.get(t.length());

        int start = 0;
        int end = tLength;

        while (end < s.length()) {
            end = start + tLength;

            String substring = s.substring(start, end);
            System.out.println("start " + start + ", end " + end + ", substring " + substring);

            long hashEnd = sPrefixHashes.get(end);
            long hashStart = sPrefixHashes.get(start);
            long sSubstringHash = hashEnd - hashStart;
            long tHashComplemented = tHash * power(p, start, mod);
            Logger.log("sSubstringHash: %d tHashComplemented %d", sSubstringHash, tHashComplemented);

            if (sSubstringHash == tHashComplemented) {
                Logger.log("True");
                return true;
            }

            for (int offset = 0; offset < t.length(); offset++) {
                int excludeIndex = start + offset;

                Logger.log("s exclude element: %s t exclude element: %s", s.charAt(excludeIndex), t.charAt(offset));
                long hashEndWithoutElement = sPrefixHashes.get(end) - (long) toInt(s.charAt(excludeIndex)) * power(p, excludeIndex, mod);
                long sSubstringHashWithoutElement = hashEndWithoutElement - hashStart;
                Logger.log("hashEndWithoutElement: %d sSubstringHashWithoutElement: %d", hashEndWithoutElement, sSubstringHashWithoutElement);

                long tHashWithoutElement = tHash - (long) toInt(t.charAt(offset)) * power(p, offset, mod);
                long tHashWithoutElementComplemented = tHashWithoutElement * power(p, start, mod);
                Logger.log("tHashWithoutElement: %d", tHashWithoutElement);

                Logger.log("sSubstringHashWithoutElement: %d tHashWithoutElementComplemented: %d", sSubstringHashWithoutElement, tHashWithoutElementComplemented);

                if (sSubstringHashWithoutElement == tHashWithoutElementComplemented) {
                    Logger.log("True");
                    return true;
                }
            }

            start = start + 1;
        }

        Logger.log("False");
        return false;
    }

    private static void calculatePrefixHashes(String s, List<Long> prefixHashes) {
        System.out.println("String " + s);
        for (int i = 0; i < s.length() + 1; i++) {
            String prefix = s.substring(0, i);
            long hash = calculatePolynomialHash(prefix, p, mod);
            prefixHashes.add(hash);

            System.out.println("Prefix hashes " + prefixHashes);
        }
    }

    private static int calculatePolynomialHash(String s, int p, int mod) {
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
