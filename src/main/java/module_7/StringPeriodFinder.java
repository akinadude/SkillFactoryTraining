package module_7;

import java.util.ArrayList;
import java.util.List;

import Log.Logger;

public class StringPeriodFinder {

    private final static int mod = 10000000 - 3;
    private final static int p = 3;

    //index 0: hash of prefix of length 0
    //index 1: hash of prefix of length 1
    //...
    private static final List<Long> prefixHashes = new ArrayList<>();

    public static String findPeriod(String s) {
        calculatePrefixHashes(s);

        int windowLength = 1;

        while (windowLength < (s.length() / 2) + 1) {
            Logger.log("windowLength = %d", windowLength);

            boolean result = doHardStuff(s, windowLength);

            if (result) {
                String period = s.substring(0, windowLength);
                Logger.log("Period is %s", period);
                return period;
            }

            windowLength++;
        }

        Logger.log("Period is empty");
        return "";
    }

    private static boolean doHardStuff(String s, int windowLength) {
        int lStart = 0, lEnd = 0;
        int rStart = s.length() - 1, rEnd = s.length() - 1;
        String periodCandidate;

        lEnd = lStart + windowLength; //exclusively
        periodCandidate = s.substring(lStart, lEnd);
        long periodCandidateHash = prefixHashes.get(lEnd - lStart);
        Logger.log("periodCandidate = %s, periodCandidateHash = %d", periodCandidate, periodCandidateHash);

        while (lEnd <= rEnd) {
            lEnd = lStart + windowLength;
            rEnd = rStart - windowLength;

            String s1 = s.substring(lStart, lEnd);
            String s2 = s.substring(rEnd + 1, rStart + 1);
            Logger.log("lStart, lEnd = %d %d, rStart, rEnd = %d %d", lStart, lEnd, rStart, rEnd);
            Logger.log("s1 = %s, s2 = %s", s1, s2);

            long hash1 = prefixHashes.get(lEnd) - prefixHashes.get(lStart);
            long hash2 = prefixHashes.get(rStart + 1) - prefixHashes.get(rEnd + 1);
            long hash1Complemented = (hash1 * power(p, rEnd + 1 - lStart, mod)) % mod;
            Logger.log("hash1 = %s, hash2 = %d, hash1Complemented = %d", hash1, hash2, hash1Complemented);

            long periodCandidateHashComplemented = (periodCandidateHash * power(p, lStart, mod)) % mod;
            Logger.log("periodCandidateHashComplemented = %d", periodCandidateHashComplemented);

            //Two hashes are the same && the first hash is equal to the period candidate hash
            boolean areSubstringsTheSame = hash1Complemented == hash2;
            boolean isFirstHashEqualToPeriodCandidateHash = hash1 == periodCandidateHashComplemented;
            if (!areSubstringsTheSame || !isFirstHashEqualToPeriodCandidateHash) {
                return false;
            }

            lStart = lStart + windowLength;
            rStart = rStart - windowLength;
        }

        return true;
    }

    private static void calculatePrefixHashes(String s) {
        for (int i = 0; i < s.length() + 1; i++) {
            String prefix = s.substring(0, i);
            long hash = calculatePolynomialHash(prefix, p, mod);
            prefixHashes.add(hash);

            System.out.println("Accumulated prefix hashes " + prefixHashes);
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
