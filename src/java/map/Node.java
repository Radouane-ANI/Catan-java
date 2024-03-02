package map;

import logic.HumanGroup;

import java.util.ArrayList;

<<<<<<< src/java/map/Node.java
class Node extends Vector {
=======
public class Node extends Vector {
>>>>>>> src/java/map/Node.java
    private HumanGroup group;
    private ArrayList<Node> neighbors;

    private static Node[] nodeArray;

    static {
        nodeArray = new Node[54];
    }

    Node(float x, int y) {
        super(x, y);
        group = null;
        neighbors = new ArrayList<>();
    }

    static void createNodes() {
        int counter = 0;

<<<<<<< src/java/map/Node.java
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 11 - 2 * i; j++) {
                nodeArray[counter++] = new Node((j + 3) / 2 + 2 * i, j / 2 - i);
                nodeArray[counter++] = new Node(j / 2 - i, (j + 3) / 2 + 2 * i);
            }
=======
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
>>>>>>> src/java/map/Node.java
        }

        connectNodesToTiles();
    }

    boolean isAdjacentToTile(Tile tile) {
<<<<<<< src/java/map/Node.java
        double distance = Math.sqrt(Math.pow(getX() - tile.getPosition().getX(), 2) + Math.pow(getY() - tile.getPosition().getY(), 2));
        return distance < 1.5; // Ajuster
    }    
=======
        double distance = Math.sqrt(
                Math.pow(getX() - tile.getPosition().getX(), 2) + Math.pow(getY() - tile.getPosition().getY(), 2));
        return distance < 1.5; // Ajuster
    }
>>>>>>> src/java/map/Node.java

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
<<<<<<< src/java/map/Node.java
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
=======
>>>>>>> src/java/map/Node.java
    }

    public static Node[] getNodesIntern() {
        return nodeArray;
    }

    void setNode(HumanGroup group) {
        this.group = group;
    }

    public HumanGroup getHumanGroup(){
        return group;
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
