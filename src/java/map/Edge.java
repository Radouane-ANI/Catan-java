package map;

import util.Tuple;
import java.util.ArrayList;

import logic.Road;
import logic.Player;

public class Edge extends Tuple<Node> {
    private Road road;

    private static ArrayList<Edge> edgeList;

    static {
        edgeList = new ArrayList<Edge>();
    }

    public Edge(Node a, Node b) {
        super(a, b);
        road = null;
    }

    static void createEdge() {
        int length = Node.getNodesIntern().length;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                Node vi = Node.getNodesIntern()[i];
                Node vj = Node.getNodesIntern()[j];
                if (vi.isNeighbor(vj)) {
                    Edge e = new Edge(vi, vj);
                    edgeList.add(e);
                    vi.addNeighbor(e);
                    vj.addNeighbor(e);
                }
            }
        }
    }

    public static Edge getEdge(Road road) {
        for (Edge edge : edgeList) {
            if (edge.road == road) {
                return edge;
            }
        }
        return null;
    }

    public static ArrayList<Edge> getEdgesIntern() {
        return edgeList;
    }

    public void setRoad(Road road) {
        this.road = road;
    }

    public Road getRoad() {
        return road;
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

    public static ArrayList<Edge> listBuildRoad(Player p) {
        ArrayList<Edge> possible = new ArrayList<>();
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
        return possible;
    }
}