package map;

public enum Direction {
    NORTH,
    NORTH_EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    NORTH_WEST;

    public int oppositeOrdinal() {
        return (this.ordinal() + 3)%6;
    }

    public static Direction getDirection(int n) {
        return Direction.values()[n%6];
    }
}
