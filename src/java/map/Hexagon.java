package map;

import java.awt.Point;
import java.awt.Polygon;

public class Hexagon {

    private int size;

    public Hexagon(int size) {
        this.size = size;
    }

    public Polygon translate(Point p) {
        int[] xPoints = {p.x, p.x + size, p.x + (int) (1.5 * size), p.x + size, p.x, p.x - (int) (0.5 * size)};
        int[] yPoints = {p.y - size, p.y - size, p.y, p.y + size, p.y + size, p.y};
        return new Polygon(xPoints, yPoints, 6);
    }
}
