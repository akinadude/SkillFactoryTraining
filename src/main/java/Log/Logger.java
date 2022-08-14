package Log;

public class Logger {

    public static void logArray(int[] array) {
        for (int j = 0; j < array.length; j++) {
            System.out.print(array[j] + ", ");
        }
    }

    public static void logMatrix(long[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n ; i++) {

            int m = matrix[i].length;
            for (int j = 0; j < m; j++) {
                System.out.print(matrix[i][j] + ", ");
            }
            System.out.println();
        }
    }

    public static void logMatrix(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n ; i++) {

            int m = matrix[i].length;
            for (int j = 0; j < m; j++) {
                System.out.print(matrix[i][j] + ", ");
            }
            System.out.println();
        }
    }

    public static void log(String s, Object... args) {
        System.out.printf((s) + "%n", args);
    }
}
