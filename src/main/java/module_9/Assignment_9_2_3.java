package module_9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import Log.Logger;

public class Assignment_9_2_3 {

    private int[][] adjMatrix = new int[100][100];

    public Assignment_9_2_3() {
        String fileName = "module_9/graph_adj_matrix_assignment_9_2_3.txt";

        FileUtils fileUtils = new FileUtils();
        InputStream inputStream = fileUtils.getFileFromResourceAsStream(fileName);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int rowNumber = 0;

            while ((line = br.readLine()) != null) {
                //Logger.log("Line read: %s", line);

                int[] row = new int[100];
                for (int i = 0, j = 0; i < line.length(); i = i + 2, j++) {
                    String elementString = String.valueOf(line.charAt(i));
                    int element = Integer.parseInt(elementString);
                    row[j] = element;
                }

                adjMatrix[rowNumber] = row;
                rowNumber++;
            }

            //Logger.logMatrix(adjMatrix);
        } catch (IOException e) {

        }
    }

    public void execute() {
        List<Edge> edges = asEdgeList();

        long count1 = edges.stream()
                .filter(this::predicate1).count();
        long count2 = edges.stream()
                .filter(this::predicate2).count();
        long count3 = edges.stream()
                .filter(this::predicate3).count();

        Logger.log("count1 = %d, count2 = %d, count3 = %d", count1, count2, count3);
    }

    private boolean predicate1(Edge edge) {
        int from = edge.getFrom();
        int to = edge.getTo();

        return (from + to) % 34 == 0;
    }

    private boolean predicate2(Edge edge) {
        int from = edge.getFrom();
        int to = edge.getTo();

        return (from - to) == 1 || (to - from) == 1;
    }

    private boolean predicate3(Edge edge) {
        int from = edge.getFrom();
        int to = edge.getTo();

        return from == to;
    }

    public List<Edge> asEdgeList() {
        List<Edge> result = new ArrayList<>();

        int n = adjMatrix.length;
        int m = adjMatrix[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (adjMatrix[i][j] == 1) {
                    Edge edge = new Edge(i, j);
                    result.add(edge);
                }
            }
        }

        return result;
    }
}
