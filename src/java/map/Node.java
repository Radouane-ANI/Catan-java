package map;

import logic.HumanGroup;

class Node extends Vector {
    private HumanGroup group;

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
    
    static Node[] getNodesIntern() {
        return nodeArray;
    }

    void setNode(HumanGroup group) {
        this.group = group;
    }
}
