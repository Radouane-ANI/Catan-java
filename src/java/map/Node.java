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
        if (x == tile.x && y == tile.y) {
            return true;
        }
        if (y == tile.y) {
            if (Math.abs(x - tile.x) == 0.5) {
                return true;
            }
        } else if (y - tile.y == 1) {
            if (Math.abs(x - tile.x) == 0.5 || x == tile.x) {
                return true;
            }
        }
        return false;
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

    public boolean canBuildSettlement() {
        if (group == null) {
            for (Node node : neighborsNode) {
                if (node.group != null) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static Node getNode(Vector v) {
        for (Node n : nodeArray) {
            if (((Vector)n).equals(v)) {
                return n;
            }
        }

        return null;
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
