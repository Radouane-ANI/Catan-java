package map;

import java.util.ArrayList;

import logic.Card;
import logic.Player;

public class Port {
    Player owner = null;
    final Card ressource;
    final int ratio;

    Port(Card ressource, int ratio) {
        this.ressource = ressource;
        this.ratio = ratio;
    }

    static void createPorts() {
        Basket<Integer> distance = new Basket<Integer>(new Integer[] {
            2,2,2,2,2,3,3,3
        });

        Basket<Port> port = new Basket<Port>(new Port[] {
            new Port(null, 3), new Port(null, 3), new Port(null, 3), new Port(null, 3),
            new Port(Card.TREE, 2), new Port(Card.GRAIN, 2), new Port(Card.SHEEP, 2), new Port(Card.SHEEP, 2), new Port(Card.STONE, 2)
        });

        ArrayList<Node> coast = getCoast();

        int index = distance.pickRandomItem();
        while (!distance.isEmpty()) {
            Port buf = port.pickRandomItem();
            coast.get(index++).setPort(buf);
            coast.get(index).setPort(buf);
            index += distance.pickRandomItem();
        }
    }

    private static ArrayList<Node> getCoast() {
        ArrayList<Node> res = new ArrayList<Node>();
        res.add(Node.getNode(new Vector(1, 0)));

        for (int i = 1; i < 30; i++) {
            res.get(i-1).getNeighbors();
            for (Node n : res.get(i-1).getNeighbors()) {
                if (n != res.get(i-1) && n.isCoastalNode()) {
                    res.add(n);
                    break;
                }
            }
        }

        return res;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public int getRatio() {
        return ratio;
    }

    public Card getRessource() {
        return ressource;
    }
}
