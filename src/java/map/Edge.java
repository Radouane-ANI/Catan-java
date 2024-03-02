package map;

import java.util.ArrayList;
import logic.Road;
import util.Tuple;

public class Edge extends Tuple<Vector> {
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
<<<<<<< src/java/map/Edge.java
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
=======
        for (Vector i : Node.getNodesIntern()) {
            for (Vector j : Node.getNodesIntern()) {
                if (i.isNeighbor(j)) {
                    edgeList.add(new Edge(i, j));
>>>>>>> src/java/map/Edge.java
                }
            }
        }
    }
<<<<<<< src/java/map/Edge.java
    

    static ArrayList<Edge> getEdgesIntern() {
        return edgeList;
=======

    public static ArrayList<Edge> getEdgesIntern() {
        return edgeList;

    }

    public Road getRoad() {
        return road;
>>>>>>> src/java/map/Edge.java
    }
}
