package src.java.map;

public class Position {
    private double x;
    private int y;

    public Position(double x, int y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
