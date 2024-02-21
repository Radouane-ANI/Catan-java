package map;

public class Vector {
    protected double x;
    protected int y;

    public Vector(double x, int y) {
        this.x = x;
        this.y = y;
    }

    void add(double x, int y) {
        this.x += x;
        this.y += y;
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

    boolean isNeighbor(Vector v) {
        int deltaX = (int) (v.x - x);
        int deltaY = v.y - y;

        return Math.abs(deltaX) == 1 && (deltaY == -deltaX || deltaY == 0) ||
               Math.abs(deltaY) == 1 && (deltaX == -deltaY || deltaX == 0);
    }
}
