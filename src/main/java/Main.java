import module_7.SubmatrixOneErrorFinder;
import module_7.SubstringOneErrorFinder;

public class Main {

    public static void main(String[] args) {
        //SkillFactory.printNumbers();

        /*if (args.length != 3) {
            System.out.println("Wrong params! It should be <param1 param2 param3>");
        }

        System.out.println("Params are OK!\nLet's find first presence of bisect in Python repo!");

        String[] params = new String[3];
        System.arraycopy(args, 0, params, 0, args.length);

        new GitBisect().execute(params);*/

        //TraverseChessBoardByKnight.execute(0, 0, 1);

        //CalculateRoomsNumber.findRooms();

        //StringHashing.calculatePolynomialHash("sfalgo", 2, 1000000000);

        //PolynomialHashing.getTwoStringsWithEqualPolynomialHash(3, 100);

        //todo Write proper tests for 7.3.4
        /*HashString hs1 = new HashString();
        HashString hs2 = new HashString();

        hs1.set("aba");
        hs2.set("aba");

        hs1.isEquals(hs2);
        hs1.pNumbers();*/

        /*hs1.isSubString(hs2);*/

        /*hs1.set("zabccbaf");
        hs1.isP(1,6);*/
        /*hs1.set("ababccbabcba");
        hs1.isP(0,11);

        hs1.pNumbers();*/

        /*hs1.set("aaa");
        hs2.set("aab");
        hs1.isEquals(hs2);

        hs1.set("baba");
        hs2.set("baba");
        hs1.isEquals(hs2);

        hs1.set("ababb");
        hs2.set("ababb");
        hs1.isEquals(hs2);*/

        /*hs1.set("abd");
        hs2.set("abb");

        hs1.isMore(hs2);*/

        //For rows use i, for columns j;
//        int[][] matrix = {
//                {2, 3, 5, 2},
//                {3, 2, 3, 1},
//                {4, 3, 5, 2},
//                {7, 2, 3, 1},
//        };
//        HashMatrix hm = new HashMatrix(matrix);
//        hm.isEqual(0, 2, 2, 4, 2, 4, 2, 4);
        //hm.isEqual(0,2,1,3,2,4,1,3);
        //hm.isEqual(0,2,0,2,1,3,1,3);

        //StringPeriodFinder.findPeriod("abcabc");

        //ZFinder.findZ("abacaba");

        //SubstringOneErrorFinder.isSubString("abcdef", "bcze");

        int[][] sMatrix = {
                {1, 2, 3, 6},
                {4, 5, 6, 6},
                {7, 8, 4, 3},
                {7, 3, 7, 9},
        };
        int[][] tMatrix = {
                {5, 6, 6},
                {8, 0, 3},
                {3, 7, 9},
        };
        SubmatrixOneErrorFinder.isSubMatrix(sMatrix, tMatrix);
    }
}
