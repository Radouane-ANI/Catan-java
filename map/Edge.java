package map;


import java.util.ArrayList;
import java.util.HashSet;

import logic.Road;

class Edge extends Tuple<Node> {
    private Road road;

    private static ArrayList<Edge> edgeList;

    static {
        edgeList = new ArrayList<Edge>();
    }

    Edge(Node a, Node b) {
        super(a, b);
        road = null;
    }

    static void createEdge() {
        for (Node i : Node.getNodesIntern()) {
            for (Node j : Node.getNodesIntern()) {
                if (((Vector)i).isNeighbor((Vector)j)) {
                    edgeList.add(new Edge(i, j));
                }
            }
        }
    }

    static ArrayList<Edge> getEdgesIntern() {
        return edgeList;
    }

    public void putRoad(Road r) {
        road = r;
    }

    public Road getRoad() {
        return road;
    }
}
