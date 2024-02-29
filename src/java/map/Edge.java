package map;

import java.util.ArrayList;

import logic.Player;
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
        int length = Node.getNodesIntern().length;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                Vector vi = Node.getNodesIntern()[i];
                Vector vj = Node.getNodesIntern()[j];
                if (vi.isNeighbor(vj)) {
                    edgeList.add(new Edge(vi, vj));
                }
            }
        }
    }

    public static ArrayList<Edge> getEdgesIntern() {
        return edgeList;

    }

    public Road getRoad() {
        return road;
    }

    public static boolean canBuildRoad(Tuple<Vector> v, Player p) {
        for (Edge edge : edgeList) {
            if (edge.equals(v)) {
                if (edge.road != null) {
                    return false;
                }
                for (Edge edge2 : edgeList) {
                    if (edge2.getX().equals(edge.getX()) || edge2.getX().equals(edge.getY())
                            || edge2.getY().equals(edge.getX()) || edge2.getY().equals(edge.getY())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean equals(Tuple<Vector> v) {
        if (v == null) {
            return false;
        }
        return (this.getX().equals(v.getX()) || v.getX().equals(this.getX()))
                && (this.getY().equals(v.getY()) || v.getY().equals(this.getY()));
    }
}
