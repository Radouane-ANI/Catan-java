package map;

import logic.Road;
import util.Tuple;

class Edge extends Tuple<Vector> {
    private Road road;

    Edge(int firstX, int firstY, int lastX, int lastY) {
        super(new Vector(firstX, firstY), new Vector(lastX, lastY));
        road = null;
    }
}
