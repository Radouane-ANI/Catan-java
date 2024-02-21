package map;

import java.util.ArrayList;
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
        Node[] nodes = Node.getNodesIntern();
        for (Node node : nodes) {
            if (node != null) {
                Node[] neighbors = node.getNeighbors();
                if (neighbors != null) {
                    for (Node neighbor : neighbors) {
                        if (neighbor != null) {
                            edgeList.add(new Edge(node, neighbor));
                        }
                    }
                }
            }
        }
    }
    

    static ArrayList<Edge> getEdgesIntern() {
        return edgeList;
    }
}
