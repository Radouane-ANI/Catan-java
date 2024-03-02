package map;

import java.util.ArrayList;

import logic.HumanGroup;

class Node extends Vector {
    private HumanGroup group;

    private ArrayList<Edge> neighEdges = new ArrayList<Edge>();

    private static Node[] nodeArray;

    static {
        nodeArray = new Node[54];
    }

    Node(int x, int y) {
        super(x, y);
        group = null;
    }

    static void createNodes() {
        int counter = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 11 - 2*i; j++) {
                nodeArray[counter++] = new Node((j + 3)/2 + 2*i, j/2 - i);
                nodeArray[counter++] = new Node(j/2 - i, (j + 3)/2 + 2*i);
            }
        }

        connectNodesToTiles();
    }

    private static void connectNodesToTiles() {
        for (Tile t : Tile.getTilesIntern()) {
            Node[] res = new Node[6];
            int counter = 0;
            for (Node n : nodeArray) {
                if (((Vector)t).isNeighbor((Vector)n)) {
                    res[counter++] = n;
                }
            }
            t.setNeighbors(res);
        }
    }

    static void connectEdge() {
        for (Edge t : Edge.getEdgesIntern()) {
            Node a = getNode(t.getX());
            Node b = getNode(t.getY());

            if (!a.neighEdges.contains(t)) {
                getNode(a).neighEdges.add(t);
            }
            else if (!b.neighEdges.contains(t)) {
                getNode(b).neighEdges.add(t);
            }
        }
    }

    static Node[] getNodesIntern() {
        return nodeArray;
    }

    public static Node getNode(Vector v) {
        for (Node n : nodeArray) {
            if (((Vector)n).equals(v)) {
                return n;
            }
        }

        return null;
    }

    public ArrayList<Node> getNeighborsNode() {
        ArrayList<Node> res = new ArrayList<Node>();
        for (Edge e : neighEdges) {
            res.add((e.getX().equals(this))?e.getY():e.getX());
        }

        return res;
    }

    public void setNode(HumanGroup group) {
        this.group = group;
    }
}
