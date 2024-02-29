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
        int deltaX = (int) Math.abs(v.getX() - x);
        int deltaY = Math.abs(v.getY() - y);
    
        if ((deltaX == 1 && deltaY == 1) || // Diagonal 
            (deltaX == 1 && deltaY == 0) || // Horizontal
            (deltaX == 0 && deltaY == 1)) {  // Vertical
            return true;
        }
        return false;
    }
    
}
