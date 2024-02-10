package map;

import logic.HumanGroup;

class Node extends Vector {
    private HumanGroup group;

    Node(int x, int y) {
        super(x, y);
        group = null;
    }
}
