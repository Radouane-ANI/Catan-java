package map;

import logic.Thief;

public class Tile extends Vector {
    private int diceNumber = 0;
    private Thief thief = null;
    private TerrainType terrain;
    private Node[] neighbors;

    private static Tile[] tileArray;
    private Position position; // positions simplifiées à utiliser
    
    static {
        tileArray = new Tile[19];
    }

    Tile(double x, int y, int diceNumber, TerrainType terrain) {
        super(x, y);
        this.position = new Position((int)x, y);
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
    
    /*
    static void createTiles() {
        int counter = 0;

        Basket<Integer> diceBasket = new Basket<Integer>(new Integer[] {
            1,
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

        Basket<TerrainType> terrainBasket = new Basket<TerrainType>(new TerrainType[] {
            TerrainType.FOREST, TerrainType.FOREST, TerrainType.FOREST, TerrainType.FOREST,
            TerrainType.FIELD, TerrainType.FIELD, TerrainType.FIELD, TerrainType.FIELD,
            TerrainType.PASTURE, TerrainType.PASTURE, TerrainType.PASTURE, TerrainType.PASTURE,
            TerrainType.BRICK, TerrainType.BRICK, TerrainType.BRICK,
            TerrainType.MOUNTAIN, TerrainType.MOUNTAIN, TerrainType.MOUNTAIN,
            TerrainType.DESERT
        });
        
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3 + i; j++) {
            tileArray[counter++] = new Tile(j , j + 5 - 2*i, diceBasket.pickRandomItem(), terrainBasket.pickRandomItem());
            tileArray[counter++] = new Tile(j + 5 - 2*i, j, diceBasket.pickRandomItem(), terrainBasket.pickRandomItem());

            }
        }
        for (int i = 1; i <= 5; i++) {
            tileArray[counter++] = new Tile(i , i, diceBasket.pickRandomItem(), terrainBasket.pickRandomItem());
        }
    }
    */

    static void createTiles() {
        tileArray = new Tile[TILE_COORDINATES.length];
    
        Basket<Integer> diceBasket = new Basket<Integer>(new Integer[] {
            1, 
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
    
        Basket<TerrainType> terrainBasket = new Basket<TerrainType>(new TerrainType[] {
            TerrainType.FOREST, TerrainType.FOREST, TerrainType.FOREST, TerrainType.FOREST,
            TerrainType.FIELD, TerrainType.FIELD, TerrainType.FIELD, TerrainType.FIELD,
            TerrainType.PASTURE, TerrainType.PASTURE, TerrainType.PASTURE, TerrainType.PASTURE,
            TerrainType.BRICK, TerrainType.BRICK, TerrainType.BRICK,
            TerrainType.MOUNTAIN, TerrainType.MOUNTAIN, TerrainType.MOUNTAIN,
            TerrainType.DESERT
        });
    
        for (int i = 0; i < TILE_COORDINATES.length; i++) {
            Vector coordinates = TILE_COORDINATES[i];
            int diceNumber = diceBasket.pickRandomItem();
            TerrainType terrain = terrainBasket.pickRandomItem();
            tileArray[i] = new Tile(coordinates.x, coordinates.y, diceNumber, terrain);
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
