package map;

import logic.HumanGroup;

class Node extends Vector {
    private HumanGroup group;

    private static Vector[] nodeArray;

    static {
        nodeArray = new Vector[54];
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
    }
    
    static Vector[] getNodes() {
        return nodeArray;
    }

    void setNode(HumanGroup group) {
        this.group = group;
    }

}
