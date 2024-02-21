package map;

import logic.HumanGroup;

import java.util.ArrayList;

class Node extends Vector {
    private HumanGroup group;
    private ArrayList<Node> neighbors;

    private static Node[] nodeArray;

    static {
        nodeArray = new Node[54];
    }

    Node(int x, int y) {
        super(x, y);
        group = null;
        neighbors = new ArrayList<>();
    }

    static void createNodes() {
        int counter = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 11 - 2 * i; j++) {
                nodeArray[counter++] = new Node((j + 3) / 2 + 2 * i, j / 2 - i);
                nodeArray[counter++] = new Node(j / 2 - i, (j + 3) / 2 + 2 * i);
            }
        }

        connectNodesToTiles();
    }

    boolean isAdjacentToTile(Tile tile) {
        double distance = Math.sqrt(Math.pow(getX() - tile.getPosition().getX(), 2) + Math.pow(getY() - tile.getPosition().getY(), 2));
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
    
    static Node[] getNodesIntern() {
        return nodeArray;
    }

    void setNode(HumanGroup group) {
        this.group = group;
    }

    Node[] getNeighbors() {
        return neighbors.toArray(new Node[0]);
    }

    void addNeighbor(Node neighbor) {
        neighbors.add(neighbor);
    }
}
