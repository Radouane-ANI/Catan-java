package map;

import logic.Thief;
import util.TerrainType;

public class Tile extends Vector {
    private int diceNumber = 0;
    private Thief thief = null;
<<<<<<< src/java/map/Tile.java
    private TerrainType terrain;
    private Node[] neighbors;

=======
    private TerrainType terrain = TerrainType.DESERT;
    private Node[] neighbors;

    static {
        tileArray = new Tile[19];
    }

>>>>>>> src/java/map/Tile.java
    private static Tile[] tileArray;
    private Position position; // positions simplifiées à utiliser

    static {
        tileArray = new Tile[19];
    }

    Tile(double x, int y, int diceNumber, TerrainType terrain) {
        super(x, y);
        this.position = new Position((int) x, y);
        this.diceNumber = diceNumber;
        this.terrain = terrain;
    }

    private static final Vector[] TILE_COORDINATES = {
        new Vector(1.5, 0), new Vector(2.5, 0), new Vector(3.5, 0),
        new Vector(1, 1), new Vector(2, 1), new Vector(3, 1), new Vector(4, 1),
        new Vector(0.5, 2), new Vector(1.5, 2), new Vector(2.5, 2), new Vector(3.5, 2), new Vector(4.5, 2),
        new Vector(1, 3), new Vector(2, 3), new Vector(3, 3), new Vector(4, 3),
        new Vector(1.5, 4), new Vector(2.5, 4), new Vector(3.5, 4)
    };

    static void createTiles() {
        tileArray = new Tile[TILE_COORDINATES.length];

        Basket<Integer> diceBasket = new Basket<Integer>(new Integer[]{
            2,
            3, 3,
            4, 4,
            5, 5,
            6, 6,
            8, 8,
            9, 9,
            10, 10,
            11, 11,
            12
        });

        Basket<TerrainType> terrainBasket = new Basket<TerrainType>(new TerrainType[]{
            TerrainType.FOREST, TerrainType.FOREST, TerrainType.FOREST, TerrainType.FOREST,
            TerrainType.FIELD, TerrainType.FIELD, TerrainType.FIELD, TerrainType.FIELD,
            TerrainType.PASTURE, TerrainType.PASTURE, TerrainType.PASTURE, TerrainType.PASTURE,
            TerrainType.BRICK, TerrainType.BRICK, TerrainType.BRICK,
            TerrainType.MOUNTAIN, TerrainType.MOUNTAIN, TerrainType.MOUNTAIN,
            TerrainType.DESERT
        });

        for (int i = 0; i < TILE_COORDINATES.length; i++) {
            Vector coordinates = TILE_COORDINATES[i];
            TerrainType terrain = terrainBasket.pickRandomItem();
            int diceNumber = (terrain == TerrainType.DESERT) ? 0 : diceBasket.pickRandomItem();
            tileArray[i] = new Tile(coordinates.getX(), coordinates.getY(), diceNumber, terrain);
        }
    }

    public static Tile[] getTilesIntern() {
        return tileArray;
    }

    public static Tile getTile(double x, int y) {
        for (int i = 0; i < tileArray.length; i++) {
            Tile tile = tileArray[i];
            if (tile != null && tile.getPosition().getX() == x && tile.getPosition().getY() == y) {
                return tile;
            }
        }
        return null;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getDiceNumber() {
        return diceNumber;
    }

    public TerrainType getTerrain() {
        return terrain;
    }

    public Thief getThief() {
        return thief;
    }

    public Node[] getNeighbors() {
        return neighbors;
    }

    void setNeighbors(Node[] neighbors) {
        this.neighbors = neighbors;
    }
}
