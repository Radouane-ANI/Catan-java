package map;

import java.util.ArrayList;

import logic.Road;
import util.Tuple;
import logic.Player;
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

    public void setRoad(Road road) {
        this.road = road;
    }

    public static ArrayList<Edge> listBuildRoad(Player p) {
        ArrayList<Edge> possible = new ArrayList<>();System.out.println(p.getRoads());
        for (Road road : p.getRoads()) {
            Edge edge = getEdge(road);
            if (edge == null) {
                continue;
            }
            for (Edge edge2 : getEdgeNeighbor(edge.getX())) {
                if (edge2.road == null) {
                    possible.add(edge2);
                }
            }
            for (Edge edge2 : getEdgeNeighbor(edge.getY())) {
                if (edge2.road == null) {
                    possible.add(edge2);
                }
            }
        }
        System.out.println(possible);
        return possible;
    }

    public boolean equals(Tuple<Vector> v) {
        if (v == null) {
            return false;
        }
        return (this.getX().equals(v.getX()) || v.getX().equals(this.getX()))
                && (this.getY().equals(v.getY()) || v.getY().equals(this.getY()));
    }

    public static Edge getEdge(Road road) {
        for (Edge edge : edgeList) {
            if (edge.road == road) {
                return edge;
            }
        }
        return null;
    }

    public static ArrayList<Edge> getEdgeNeighbor(Vector v) {
        ArrayList<Edge> l = new ArrayList<>();
        for (Edge edge : edgeList) {
            if ((edge.getX().equals(v) || edge.getY().equals(v)) && edge.road == null) {
                l.add(edge);
            }
        }
        return l;
    }
}
