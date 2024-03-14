package src.java.gui;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.AffineTransform;

class Hexagon {

    private int size;
    private Shape hex;

    Hexagon(int s) {
        size = s;
        hex = createHexagon();
    }

    private Shape createHexagon() {
        Path2D.Double hexagon = new Path2D.Double();

        double x = 0;
        double y = 0;

        hexagon.moveTo(x, y);

        for (int i = 0; i < 6; i++) {
            double angleRadians = Math.toRadians(60 * i + 30);
            x += size * Math.cos(angleRadians);
            y += size * Math.sin(angleRadians);
            hexagon.lineTo(x, y);
        }

        hexagon.closePath();
        return hexagon;
    }

    Shape getShape() {
        return hex;
    }

    Shape translate(Point p) {
        AffineTransform tr = new AffineTransform();
        tr.translate((int)p.getX(), (int)p.getY());

        return tr.createTransformedShape(hex);
    }
}
