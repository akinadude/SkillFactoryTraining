package module_9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import Log.Logger;

public class Assignment_9_2_5 {

    private final int M = 100;
    private final int N = 100;
    private final List<List<Integer>> adjacentList = new ArrayList<>();

    private final String subStringToCount = "001001";

    public Assignment_9_2_5() {
        String fileName = "module_9/graph_adj_list_assignment_9_2_5.txt";

        FileUtils fileUtils = new FileUtils();
        InputStream inputStream = fileUtils.getFileFromResourceAsStream(fileName);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int lineNumber = -1;

            while ((line = br.readLine()) != null) {
                lineNumber++;

                int delimiterIndex = line.indexOf(" ");
                if (delimiterIndex == -1) {
                    Logger.log("Error while reading number of to-vertices list for line #%d.", lineNumber);
                    return;
                }

                String verticesToCountString = line.substring(0, delimiterIndex);
                int declaredVerticesToCount = Integer.parseInt(verticesToCountString);
                //Logger.log("Declared to-vertices count: %d", declaredVerticesToCount);

                String subLine = line.substring(delimiterIndex + 1);
                List<Integer> accumulated = new ArrayList<>();
                List<Integer> verticesTo = readAdjacentListLine(declaredVerticesToCount, subLine, accumulated);

                /*Logger.log("Vertices to:");
                Logger.logList(verticesTo);*/

                adjacentList.add(verticesTo);
            }
        } catch (IOException e) {
            Logger.log("Error while reading adjacent list file %s, error: %s", fileName, e.getMessage());
        }
    }

    public void execute() {
        int[][] adjacentMatrix = asAdjacentMatrix();
        //Logger.logMatrix(adjacentMatrix);

        List<String> adjacentMatrixTextTable = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            StringBuilder sb = new StringBuilder();

            for (int j = 0; j < M; j++) {
                if (adjacentMatrix[i][j] == 1) {
                    sb.append("1");
                } else {
                    sb.append("0");
                }
            }

            adjacentMatrixTextTable.add(sb.toString());
        }

        //Logger.logList(adjacentMatrixTextTable);
        int sum = 0;
        for (String row : adjacentMatrixTextTable) {
            if (row.contains(subStringToCount)) {
                sum++;
            }
        }
        Logger.log("Sum: %d", sum);
    }

    private int[][] asAdjacentMatrix() {
        int[][] matrix = new int[N][M];

        for (int i = 0; i < adjacentList.size(); i++) {
            List<Integer> verticesTo = adjacentList.get(i);
            for (int j : verticesTo) {
                matrix[i][j] = 1;
            }
        }

        return matrix;
    }

    private List<Integer> readAdjacentListLine(
            int declaredSize,
            String line,
            List<Integer> accumulated
    ) {
        //Logger.log("Line: %s\naccumulated size: %d\n", line, accumulated.size());

        String elementString;
        int end = line.indexOf(" ");
        if (end == -1 && accumulated.size() == declaredSize - 1) {
            elementString = line;
        } else {
            elementString = line.substring(0, end);
        }

        int element = Integer.parseInt(elementString); //vertex to
        accumulated.add(element);
        if (accumulated.size() == declaredSize) {
            return accumulated;
        }

        String subLine = line.substring(end + 1);
        return readAdjacentListLine(declaredSize, subLine, accumulated);
    }
}
