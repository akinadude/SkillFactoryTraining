package module_9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Log.Logger;

public class Assignment_9_2_4 {

    private List<Edge> edges = new ArrayList<>();

    public Assignment_9_2_4() {
        String fileName = "module_9/graph_adj_matrix_assignment_9_2_4.txt";

        FileUtils fileUtils = new FileUtils();
        InputStream inputStream = fileUtils.getFileFromResourceAsStream(fileName);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int lineNumber = -1;
            int edgesNumber;

            while ((line = br.readLine()) != null) {
                lineNumber++;

                if (lineNumber == 0) {
                    edgesNumber = tryParseEdgesNumber(line);

                    if (edgesNumber == 0) {
                        return;
                    } else {
                        continue;
                    }
                }

                int delimiterIndex = line.indexOf(" ");
                String fromSubstring = line.substring(0, delimiterIndex);
                String toSubstring = line.substring(delimiterIndex + 1);
                int from = Integer.parseInt(fromSubstring);
                int to = Integer.parseInt(toSubstring);

                Edge edge = new Edge(from, to);
                edges.add(edge);
            }

            Logger.logList(edges);
        } catch (IOException e) {
        }
    }

    public void execute() {
        Map<Integer, List<Integer>> adjacentList = asAdjacentList(edges);

        List<Integer> hashesList = new ArrayList<>();

        for (Map.Entry<Integer, List<Integer>> entry : adjacentList.entrySet()) {
            List<Integer> toVertices = entry.getValue();

            int sum = toVertices.stream().reduce(0, Integer::sum);
            int size = toVertices.size();
            int hash = size * sum;

            hashesList.add(hash);
        }

        int hashesSum = hashesList.stream().reduce(0, Integer::sum);

        Logger.log("Result: " + hashesSum);
    }

    private Map<Integer, List<Integer>> asAdjacentList(List<Edge> edges) {
        Map<Integer, List<Integer>> result = new HashMap<>();

        for (Edge e : edges) {
            int from = e.getFrom();
            int to = e.getTo();

            List<Integer> verticesByFrom = result.get(from);
            if (verticesByFrom == null) {
                verticesByFrom = new ArrayList<>();
                verticesByFrom.add(to);
                result.put(from, verticesByFrom);
            }

            verticesByFrom.add(to);
        }

        return result;
    }

    private int tryParseEdgesNumber(String line) {
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            Logger.log("Issue with reading edges number, error: %s", e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }
}
