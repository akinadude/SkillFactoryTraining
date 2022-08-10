package module_7;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import kotlin.Pair;

public class PolynomialHashing {

    public static Pair<String, String> getTwoStringsWithEqualPolynomialHash(int p, int mod) {
        String s1 = "a", s2 = "";
        Set<Integer> hashes = new HashSet<>();
        Map<Integer, String> hashesToStrings = new HashMap<>();

        int count = 0;
        String s = "";
        while (count < 100) {
            count++;
            String nextString = getNextString(s);
            System.out.println("count = " + count + ", string = " + nextString);

            int nextStringHash = calculatePolynomialHash(nextString, p, mod);

            if (hashes.contains(nextStringHash)) {
                System.out.println("The same hash detected: " + nextStringHash);

                System.out.println("String with such hash is " + hashesToStrings.get(nextStringHash));
                s1 = nextString;
                s2 = hashesToStrings.get(nextStringHash);

                break;
            }

            hashes.add(nextStringHash);
            hashesToStrings.put(nextStringHash, nextString);
            System.out.println(nextString + ", " + nextStringHash);

            s = nextString;
        }

        return new Pair<>(s1, s2);
    }

    private static String getNextString(String s) {
        if (s.isEmpty()) {
            return "a";
        }

        int length = s.length();

        StringBuilder stringBuilder = new StringBuilder();
        if (s.charAt(length - 1) == 'z') {
            int index = length - 1;
            int zSequenceCount = 0;
            while (index >= 0) {
                if (s.charAt(index) == 'z') {
                    zSequenceCount++;
                }
                index--;
            }

            if (zSequenceCount == length) {
                stringBuilder.append("b");
                for (int i = 0; i < length; i++) {
                    stringBuilder.append("a");
                }
            } else {
                int firstNonZIndex = length - zSequenceCount - 1;
                stringBuilder.append(s, 0, firstNonZIndex);

                char firstNonZChar = s.charAt(firstNonZIndex);
                char nextChar = (char) (firstNonZChar + 1);
                stringBuilder.append(nextChar);

                for (int i = length - zSequenceCount; i < length; i++) {
                    stringBuilder.append("a");
                }
            }

            String increasedSizeString = stringBuilder.toString();
            return increasedSizeString;
        } else {
            stringBuilder.append(s, 0, length - 1);

            char lastChar = s.charAt(length - 1);
            char nextLastChar = (char) (lastChar + 1);
            stringBuilder.append(nextLastChar);

            String theSameSizeString = stringBuilder.toString();
            return theSameSizeString;
        }
    }

    public static int calculatePolynomialHash(String s, int p, int mod) {
        int res = 0;

        for (int i = 0; i < s.length(); i++) {
            res += power(p, i, mod) * toInt(s.charAt(i));
            res %= mod;
        }

        System.out.println("res: " + res);

        return res;
    }

    private static int toInt(char c) {
        return c - 'a';
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
