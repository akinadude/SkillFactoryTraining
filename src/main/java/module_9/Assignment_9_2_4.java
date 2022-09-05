package module_9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import Log.Logger;

public class Assignment_9_2_4 {

    private List<Edge> edges = new ArrayList<>();

    public Assignment_9_2_4() {
        String fileName = "module_9/graph_edge_list_assignment_9_2_4.txt";

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
        List<List<Integer>> adjacentList = asAdjacentList(edges);

        List<Integer> hashesList = new ArrayList<>();

        for (List<Integer> toVertices : adjacentList) {
            int sum = toVertices.stream().reduce(0, Integer::sum);
            int size = toVertices.size();
            int hash = size * sum;

            hashesList.add(hash);
        }

        int hashesSum = hashesList.stream().reduce(0, Integer::sum);

        Logger.log("Result: " + hashesSum);
    }

    private List<List<Integer>> asAdjacentList(List<Edge> edges) {
        int fromMax = edges.stream()
                .map(Edge::getFrom)
                .max(Comparator.naturalOrder())
                .orElse(0);

        int toMax = edges.stream()
                .map(Edge::getTo)
                .max(Comparator.naturalOrder())
                .orElse(0);

        int max = Math.max(fromMax, toMax);
        Logger.log("Max vertex number: %d", max);

        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < max + 1; i++) {
            List<Integer> verticesByFrom = new ArrayList<>();
            result.add(verticesByFrom);
        }

        for (Edge e : edges) {
            int from = e.getFrom();
            int to = e.getTo();

            result.get(from).add(to);
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
