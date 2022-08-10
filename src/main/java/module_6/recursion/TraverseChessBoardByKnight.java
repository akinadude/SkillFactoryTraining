package module_6.recursion;

import java.util.Arrays;

public class TraverseChessBoardByKnight {

    final static int width = 5;
    final static int height = 5;

    final static int[][] moves = {
            {2, 1},
            {2, -1},
            {-2, 1},
            {-2, -1},
            {1, 2},
            {1, -2},
            {-1, 2},
            {-1, -2},
    };
    // More simple case: board 2x2, possible moves: right, left, up, down.

    static int[][] board = new int[width][height];

    static {
        board[0][0] = 1;
    }

    /**
     * Returns amount of ways a knight can traverse the whole board.
     */
    public static int execute(int x, int y, int boardCellsAttendedNumber) {
        for (int[] line : board) {
            System.out.println(Arrays.toString(line));
        }
        System.out.println();

        if (boardCellsAttendedNumber == width * height) {
            return 1;
        }

        int count = 0;

        for (int[] move : moves) {
            int cellToMoveX = x + move[0];
            int cellToMoveY = y + move[1];

            if ((cellToMoveX >= 0 && cellToMoveX < width)
                    && (cellToMoveY >= 0 && cellToMoveY < height)
                    && (board[cellToMoveX][cellToMoveY] == 0)) {
                board[cellToMoveX][cellToMoveY] = 1;
                count += execute(cellToMoveX, cellToMoveY, boardCellsAttendedNumber + 1);
                board[cellToMoveX][cellToMoveY] = 0;
            }
        }

        System.out.println("Count: " + count);
        return count;
    }
}
