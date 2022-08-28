package module_8;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;

//Find continuous subarray of sum equals k
public class Assignment_8_2_5 {

    //index 0: sum of elements by indices [0, 1)
    //index 1: sum of elements by indices [0, 2)
    //index 2: sum of elements by indices [0, 3)
    //...
    private final static List<Integer> prefixSums = new ArrayList<>();

    private static void calculatePrefixSums(Integer[] v) {
        int sum = 0;

        prefixSums.add(0);

        for (int i = 1; i < v.length + 1; i++) {
            sum = sum + v[i - 1];
            prefixSums.add(sum);
        }
    }

    public static Pair<Integer, Integer> solution(Integer[] v, int k) {
        calculatePrefixSums(v);

        for (int anchor = 0; anchor < v.length; anchor++) {
            int l = anchor;
            int r = v.length;
            int wholeArraySum = prefixSums.get(r) - prefixSums.get(anchor);

            if (wholeArraySum < k) {
                continue;
            }

            System.out.println("l, r " + l + ", " + r);

            boolean isStandardMode = true;
            boolean isTwoMidsMode = false;
            int leftMid = -1;
            int rightMid = -1;
            int mid = -1;
            int subSum = 0;

            while ((isStandardMode && l < r)
                    || (isTwoMidsMode && leftMid < rightMid)) {

                System.out.println("standardMode, twoMidsMode " + isStandardMode + ", " + isTwoMidsMode);

                if (isStandardMode) {
                    mid = (r + l) / 2;
                    subSum = prefixSums.get(mid + 1) - prefixSums.get(anchor);
                }

                if (isTwoMidsMode) {
                    System.out.println("leftMid = " + leftMid + ", rightMid = " + rightMid);
                    mid = (rightMid + leftMid) / 2;
                    subSum = prefixSums.get(mid + 1) - prefixSums.get(anchor);
                }

                System.out.println("mid = " + mid);
                System.out.println("subSum = " + subSum);

                if (subSum == k) {
                    return new Pair<>(l, mid);
                }

                if (subSum > k) {
                    if (isTwoMidsMode) {
                        leftMid = (leftMid + mid) / 2;
                        rightMid = mid;
                    } else {
                        r = mid;
                    }
                } else {
                    if (isStandardMode) {
                        isStandardMode = false;
                        isTwoMidsMode = true;

                        leftMid = mid;
                        rightMid = r;

                        continue;
                    }

                    if (isTwoMidsMode) {
                        leftMid = mid;
                        rightMid = (mid + rightMid) / 2;
                    }
                }
            }
        }

        return new Pair<>(-1, -1); // l, r not found
    }
}
