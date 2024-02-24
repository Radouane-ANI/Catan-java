package map;

import logic.City;
import logic.HumanGroup;

import java.util.ArrayList;

public class Node extends Vector {
    private HumanGroup group;
    private ArrayList<Node> neighbors;

    private static Node[] nodeArray;

    static {
        nodeArray = new Node[54];
    }

    Node(float x, int y) {
        super(x, y);
        group = new City(null);
        neighbors = new ArrayList<>();
    }

    static void createNodes() {
        int counter = 0;

        float decalage = 1;
        for (int i = 0; i < 3; i++) {
            for (float j = decalage; j <= 4 + (float) i / 2; j += 0.5) {
                nodeArray[counter++] = new Node(j, i);
                System.out.println("node " + (counter - 1) + nodeArray[counter - 1]);

            }
            decalage -= 0.5;
        }
        decalage = 0;
        for (int i = 0; i < 3; i++) {
            for (float j = decalage; j <= 4 + (float) (2 - i) / 2; j += 0.5) {
                nodeArray[counter++] = new Node(j, i + 3);
                System.out.println("node " + (counter - 1) + nodeArray[counter - 1]);

            }
            decalage += 0.5;
        }

        connectNodesToTiles();
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

    void setNode(HumanGroup group) {
        this.group = group;
    }

    public Node[] getNeighbors() {
        return neighbors.toArray(new Node[0]);
    }

    void addNeighbor(Node neighbor) {
        neighbors.add(neighbor);
    }

    public HumanGroup getGroup() {
        return group;
    }

}
