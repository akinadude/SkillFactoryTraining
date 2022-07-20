public class SkillFactory {

    public static void printNumbers() {
        String s1 = "1 2 0";
        String s2 = "1 2 3 4 5 6 7 8 9 0";

        /*String substring = "a".substring(1);
        System.out.print("substring is " + substring);*/

        //printNumbersSequence(s1);
        //printNumbersSequence(s2);

        /*int gcd1 = gcd(6,3);
        System.out.print("GCD 1 " + gcd1);
        int gcd2 = gcd(170,100);
        System.out.print("GCD 2 " + gcd2);*/

        /*int result = n_gcd(new int[] {6,9,15}, 3);
        int result1 = n_gcd(new int[] {30,170,1200}, 3);
        int result2 = n_gcd(new int[] {10,53}, 2);
        int result3 = n_gcd(new int[] {100,1}, 2);
        int result4 = n_gcd(new int[] {10, 45, 15, 20}, 4);
        System.out.println("n_gcd " + result1);
        System.out.println("n_gcd " + result2);
        System.out.println("n_gcd " + result3);
        System.out.println("n_gcd " + result4);*/

        //gen_permutation(4, 2, new int[4], 0, new boolean[5]);

        subsets(3);
    }

    public static void printNumbersSequence(String sequence) {
        Character character = sequence.charAt(0);
        if (character != '0') {
            printNumbersSequence(sequence.substring(1));
            System.out.print(character);
        }
    }

    static int gcd(int a, int b) {
        int divider = Math.min(a, b);
        int r = Math.max(a, b) % divider;
        if (r > 0) {
            return gcd(divider, r);
        } else {
            return divider;
        }
    }

    public static int n_gcd(int[] array, int n) {
        int result = array[0];
        int m = 0;
        while (m < n) {
            int element = array[m];
            result = gcd(result, element);
            m++;
        }
        return result;
    }

    //Сочетания из N по K
    public static void gen_permutation(int N, int K, int[] perm, int k, boolean[] used) {
        if (k == K) {
            for (int i = 0; i < K; i++) {
                System.out.print(perm[i]);
            }
            System.out.println();
        } else {
            for (int x = 1; x <= N; x++) {
                if (!used[x]) {
                    perm[k] = x;
                    used[x] = true;
                    gen_permutation(N, K, perm, k + 1, used);
                    used[x] = false;
                }
            }
        }
    }

    /*public static char[] brackets = {'(', '{', '['};

    public static char getNextBracket() {
        for (int i = 0; i < 2; i++) { // 2 это N/2
            for (int j = 0; j < brackets.length; j++) { //bracket types

            }
        }
    }*/

    public static void subsets(int N) {
        for (int i = 0; i < Math.pow(2, N); i++) {
            String binaryString = String.format("%3s", Integer.toBinaryString(i)).replace(' ', '0');
            System.out.print(binaryString + " ");
        }
    }
}
