package map;

public class Vector {
    private int x;
    private int y;

    Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void add(int x, int y) {
        this.x += x;
        this.y += y;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    public String toString() {
        return "("+x+", "+y+")";
    }

    boolean isNeighbor(Vector v) {
        int deltaX =  v.x - x;
        int deltaY = v.y - y;

        return abs(deltaX) == 1 && (deltaY == -deltaX || deltaY == 0) || 
                abs(deltaY) == 1 && (deltaX == -deltaY || deltaX == 0);
    }

    private int abs(int x) {
        return (x > 0)? x:-x;
    }
}
