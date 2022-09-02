package module_9;

public class Edge {
    private final int from;
    private final int to;

    public Edge(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "[" + from + ", " + to + "]";
    }
}
