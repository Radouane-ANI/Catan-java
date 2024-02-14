package src.java.map;

class Vector {
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
}
