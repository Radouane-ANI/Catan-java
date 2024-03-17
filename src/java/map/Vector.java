package src.java.map;

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
        if (x == v.x && y == v.y) {
            return false;
        }
        if (y == v.y) {
            if (Math.abs(x - v.x) == 0.5) {
                return true;
            }
        } else if (Math.abs(y - v.y) == 1) {
            if (Math.min(y, v.y) % 2 == 0) {
                if (x == v.x && (x - (int) x) == 0) {
                    return true;
                }
            } else {
                if (x == v.x && (x - (int) x) == 0.5) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean equals(Vector v) {
        if (v == null) {
            return false;
        }
        return Double.compare(x, v.x) == 0 && y == v.y;
    }
}
