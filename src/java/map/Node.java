package map;

import logic.HumanGroup;

import java.util.ArrayList;

public class Node extends Vector {
    private HumanGroup group;
    private ArrayList<Edge> neighborsEdge;
    private ArrayList<Node> neighborsNode;


    private static Node[] nodeArray;

    static {
        nodeArray = new Node[54];
    }

    Node(float x, int y) {
        super(x, y);
        group = null;
        neighborsEdge = new ArrayList<Edge>();
        neighborsNode = new ArrayList<Node>();
    }

    static void createNodes() {
        int counter = 0;

        float decalage = 1;
        for (int i = 0; i < 3; i++) {
            for (float j = decalage; j <= 4 + (float) i / 2; j += 0.5) {
                nodeArray[counter++] = new Node(j, i);
            }
            decalage -= 0.5;
        }
        decalage = 0;
        for (int i = 0; i < 3; i++) {
            for (float j = decalage; j <= 4 + (float) (2 - i) / 2; j += 0.5) {
                nodeArray[counter++] = new Node(j, i + 3);
            }
            decalage += 0.5;
        }

        connectNodesToTiles();
        connectToNeighbors();
    }

    private static void connectToNeighbors() {
        for (int i = 0; i < nodeArray.length; i++) {
            for (int j = 0; j < nodeArray.length; j++) {
                Node vi = nodeArray[i];
                Node vj = nodeArray[j];
                if (vi.isNeighbor(vj)) {
                    vi.neighborsNode.add(vj);
                }
            }
        }
    }

    boolean isAdjacentToTile(Tile tile) {
        double distance = Math.sqrt(
                Math.pow(getX() - tile.getPosition().getX(), 2) + Math.pow(getY() - tile.getPosition().getY(), 2));
        return distance < 1.5; // Ajuster
    }
    

    private static void connectNodesToTiles() {
        for (Tile t : Tile.getTilesIntern()) {
            ArrayList<Node> res = new ArrayList<>();
            for (Node n : nodeArray) {
                if (n.isAdjacentToTile(t)) {
                    res.add(n);
                }
            }
            t.setNeighbors(res.toArray(new Node[0]));
        }
    }
    
    public static Node[] getNodesIntern() {
        return nodeArray;
    }

    public void setNode(HumanGroup group) {
        this.group = group;
    }

    public HumanGroup getHumanGroup() {
        return group;
    }

    public Node[] getNeighbors() {
        return neighborsNode.toArray(new Node[0]);
    }

    void addNeighbor(Edge neighbor) {
        neighborsEdge.add(neighbor);
    }

    public HumanGroup getGroup() {
        return group;
    }

    public static Node canBuildSettlement(Vector v) {
        Node pos = null;
        for (int i = 0; i < nodeArray.length; i++) {
            if (nodeArray[i].equals(v)) {
                pos = nodeArray[i];
                if (nodeArray[i].group != null) {
                    return null;
                }
                for (Node node : nodeArray[i].neighborsNode) {
                    if (node.group != null) {
                        return null;
                    }
                }
            }
        }
        return pos;
    }

    public static Node getNode(Vector v) {
        for (Node n : nodeArray) {
            if (((Vector)n).equals(v)) {
                return n;
            }
        }

        return null;
    }

    static void connectEdge() {
        for (Edge t : Edge.getEdgesIntern()) {
            Node a = getNode(t.getX());
            Node b = getNode(t.getY());

            if (!a.neighborsEdge.contains(t)) {
                getNode(a).neighborsEdge.add(t);
            }
            else if (!b.neighborsEdge.contains(t)) {
                getNode(b).neighborsEdge.add(t);
            }
        }
    }

    public static Node getNode(HumanGroup group) {
        for (int i = 0; i < nodeArray.length; i++) {
            if (nodeArray[i].group == group) {
                return nodeArray[i];
            }
        }
        return null;
    }

}
