package map;

import logic.Thief;

public class Tile extends Vector {
    private int diceNumber = 0;
    private Thief thief = null;
    private TerrainType terrain;
    private Node[] neighbors;

    private static Tile[] tileArray;

    static {
        tileArray = new Tile[19];
    }

    Tile(int x, int y, int diceNumber, TerrainType terrain) {
        super(x, y);
        this.diceNumber = diceNumber;
        this.terrain = terrain;
    }

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

    static Tile[] getTilesIntern() {
        return tileArray;
    }

    public static Tile getTile(int x, int y) {
        for (int i = 0; i < tileArray.length; i++) {
            if (tileArray[i].getX() == x && tileArray[i].getY() == y) {
                return tileArray[i];
            }
        }
        
        return null;
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
