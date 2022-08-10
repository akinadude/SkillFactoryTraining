package module_7;

import java.util.ArrayList;
import java.util.List;

import Log.Logger;

public class HashString {

    private String s;

    private final int mod = 1000000 - 3;
    private final int p = 213;

    //index 0: hash of prefix of length 0
    //index 1: hash of prefix of length 1
    //...
    private final List<Long> prefixHashes = new ArrayList<>();
    private final List<Long> suffixHashes = new ArrayList<>();

    public HashString() {
        this.s = "";
    }

    public void set(String s) {
        this.s = s;

        for (int i = 0; i < s.length() + 1; i++) {
            String prefix = s.substring(0, i);
            long hash = calculatePolynomialHash(prefix, p, mod);
            prefixHashes.add(hash);

            String postfix = s.substring(s.length() - i);
            //int startOffset = s.length() - i;
            String postfixReversed = getReversedString(postfix);
            long postfixHash = calculateSuffixHash(postfixReversed, p, mod);
            suffixHashes.add(postfixHash);
            System.out.println("Postfix " + postfixReversed + ", postfix hash " + postfixHash);

            //System.out.println("Prefix " + prefix + ", hash " + hash);
            System.out.println("Accumulated prefix hashes " + prefixHashes);
            System.out.println("Accumulated postfix hashes " + suffixHashes);
        }
    }

    public String get() {
        return s;
    }

    public long getHash() {
        int lastIndex = prefixHashes.size() - 1;
        return prefixHashes.get(lastIndex);
    }

    public long getPrefixHash(int index) {
        return prefixHashes.get(index);
    }

    public boolean isSubString(HashString hs) {
        long hsHash = hs.getHash();
        int hsLength = hs.get().length();

        int start = 0, end = 0;
        while (end < s.length()) {
            end = start + hsLength;

            String substring = s.substring(start, end);
            System.out.println("start " + start + ", end " + end + ", substring " + substring);

            long hashEnd = prefixHashes.get(end);
            long hashStart = prefixHashes.get(start);
            long substringHash = hashEnd - hashStart;
            System.out.println("hashEnd " + hashEnd + ", hashStart " + hashStart);
            System.out.println("substringHash " + substringHash);

            long hsHashComplemented = (hsHash * power(p, start, mod)) % mod;
            System.out.println("hsHash: " + hsHash + ", hsHashComplemented " + hsHashComplemented);
            if (substringHash == hsHashComplemented) {
                //imply no collisions happened

                System.out.println(hs.get() + " is a substring of " + s);
                return true;
            }

            start++;
        }

        return false;
    }

    public boolean isEquals(HashString hs) {
        long hash = getHash();
        long hsHash = hs.getHash();

        if (hash == hsHash) {
            System.out.println(s + " equals to " + hs.get());
        } else {
            System.out.println(s + " doesn't equal to " + hs.get());
        }
        return hash == hsHash;
    }

    // Let's start from the simplest solution ;)
    public boolean isP(int l, int r /*inclusively*/) {
        String pCandidate = s.substring(l, r + 1);
        String pCandidateReversed = getReversedString(pCandidate);

        if (pCandidate.equals(pCandidateReversed)) {
            System.out.printf("Substring %s is a palindrome%n", pCandidate);
        } else {
            //System.out.printf("Substring %s is not a palindrome%n", candidate);
        }
        return pCandidate.equals(pCandidateReversed);
    }

    public boolean isPOld(int l, int r /*inclusively*/) {
        int end = r + 1;
        int start = l;

        if (end - start < 1) {
            return false;
        }

        long hashEnd = prefixHashes.get(end);
        long hashStart = prefixHashes.get(start);
        long prefixHash = hashEnd - hashStart;

        hashEnd = suffixHashes.get(end);
        hashStart = suffixHashes.get(start);
        long suffixHash = hashEnd - hashStart;

        String debug = String.format("prefix hash %d, postfix string hash: %d", prefixHash, suffixHash);
        System.out.println(debug);

        if (prefixHash == suffixHash) {
            System.out.printf("Substring %s is a palyndrom%n", s.substring(start, end));
        } else {
            System.out.printf("Substring %s is not a palyndrom%n", s.substring(start, end));
        }

        return prefixHash == suffixHash;
    }

    public int pNumbers() {
        int pLength = 1;
        int pCount = 0;

        while (pLength < s.length() + 1) {
            int start = 0, end = 0;

            while (end < s.length()) {
                end = start + pLength;

                //String substring = s.substring(start, end);
                //System.out.println("Palyndrom candidate:" + substring);
                if (isP(start, end - 1)) {
                    pCount++;
                }

                start++;
            }

            pLength++;
        }

        System.out.printf("Palindrome counter is %s%n", pCount);
        return pCount;
    }

    // "More" means more in lexicographical sense.
    public boolean isMore(HashString t) {
        int minLength = Math.min(s.length(), t.get().length());

        int l = 0;
        int r = minLength;

        while (l < r - 1) {
            int mid = (l + r) / 2;

            if (t.getPrefixHash(mid) == getPrefixHash(mid)) {
                l = mid;
            } else {
                r = mid;
            }
        }

        int sChar = s.charAt(l);
        int tChar = t.get().charAt(l);

        Logger.log("index = %d", l);
        Logger.log("sChar = %s, tChar = %s", s.charAt(l), t.get().charAt(l));

        if (s.length() != t.get().length() && l == minLength - 1 && tChar == sChar) {
            if (s.length() > t.get().length()) {
                Logger.log("True");
            } else {
                Logger.log("False");
            }

            return s.length() > t.get().length();
        }

        if (sChar > tChar) {
            Logger.log("True");
            return true;
        } else {
            Logger.log("False");
            return false;
        }
    }

    private int calculatePolynomialHash(String s, int p, int mod) {
        if (s.isEmpty()) {
            return 0;
        }

        int res = 0;

        for (int i = 0; i < s.length(); i++) {
            res += power(p, i, mod) * toInt(s.charAt(i));
            res %= mod;
        }

        return res;
    }

    private int calculateSuffixHash(String postfix, int p, int mod) {
        if (s.isEmpty()) {
            return 0;
        }

        int res = 0;

        for (int i = 0; i < postfix.length(); i++) {
            res += power(p, i, mod) * toInt(postfix.charAt(i));
            res %= mod;
        }

        return res;
    }

    private int toInt(char c) {
        return c - 'a';
    }

    private int power(int x, int n, int mod) {
        int res = 1;

        for (int i = 0; i < n; i++) {
            res *= x;
            res %= mod;
        }

        return res;
    }

    private String getReversedString(String string) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = string.length() - 1; i > -1; i--) {
            stringBuilder.append(string.charAt(i));
        }

        return stringBuilder.toString();
    }
}
