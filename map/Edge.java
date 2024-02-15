package map;


import java.util.ArrayList;
import java.util.HashSet;

import logic.Road;

class Edge extends Tuple<Vector> {
    private Road road;

    private static ArrayList<Edge> edgeList;

    static {
        edgeList = new ArrayList<Edge>();
    }

    Edge(Vector a, Vector b) {
        super(a, b);
        road = null;
    }

    static void createEdge() {
        for (Vector i : Node.getNodesIntern()) {
            for (Vector j : Node.getNodesIntern()) {
                if (i.isNeighbor(j)) {
                    edgeList.add(new Edge(i, j));
                }
            }
        }
    }

    static ArrayList<Edge> getEdgesIntern() {
        return edgeList;
    }
}
