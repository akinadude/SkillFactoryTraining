package module_6.recursion;

import kotlin.Pair;

public class CalculateRoomsNumber {

    /* 1. Find zero
     * 2. Check adjacent cells for zeros
     *       There are zero cells: add one to room's array and proceed with recursion.
     *       There are no zero cells: end of recursion. Set room's array cells into ones. Return room size.*/

    final static int width = 6;
    final static int height = 6;

    final static int[][] moves = {
            {1, 0},
            {-1, 0},
            {0, 1},
            {0, -1},
    };

    final static int[][] apartment = {
            {1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 1, 1},
            {1, 0, 0, 0, 1, 1},
            {1, 1, 1, 1, 0, 1},
            {1, 0, 1, 0, 1, 1},
            {1, 1, 1, 1, 1, 1},
    };

    //Suppose it's get called on zero cell
    public static Pair<Integer, Integer> outlineRoom(int x, int y) {
        int size = 1;

        apartment[x][y] = 1;

        for (int[] move : moves) {
            int cellMoveToX = x + move[0];
            int cellMoveToY = y + move[1];

            if (apartment[cellMoveToX][cellMoveToY] == 0) {
                Pair<Integer, Integer> pair = outlineRoom(cellMoveToX, cellMoveToY);
                size += pair.getSecond();
            }
        }

        return new Pair<>(1, size);
    }

    public static void findRooms() {
        int roomsNumber = 0;

        for (int i = 0; i < apartment.length; i++) {
            for (int j = 0; j < apartment.length; j++) {
                if (apartment[i][j] == 0) {

                    Pair<Integer, Integer> pair = outlineRoom(i, j);

                    roomsNumber += pair.getFirst();
                    System.out.println("Room size: " + pair.getSecond());
                }
            }
        }

        System.out.println("Rooms number: " + roomsNumber);
    }
}
