package util;

public class Tuple<T> {
    
    private T x;
    private T y;

    public Tuple(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public T getX() {
        return x;
    }

    public T getY() {
        return y;
    }
}
