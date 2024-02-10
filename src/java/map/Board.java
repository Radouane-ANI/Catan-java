package map;

public class Board {
    private static Board INSTANCE;
    //private static Vector[] tileArray;
    private static Tile[] tileArray;// TEST
    private Tile[][] tiles;// TEST


    public static void createBoard() {
        Board.INSTANCE = new Board();
    }

    public static Board getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Board();
        }
        return INSTANCE;
    }

    private Board() {
        tileArray = new Tile[19];
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
            TerrainType.MOUNTAIN, TerrainType.MOUNTAIN, TerrainType.MOUNTAIN
        });

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3 + i; j++) {
                Tile tile1 = new Tile(j , j + 5 - 2*i, diceBasket.pickRandomItem(), terrainBasket.pickRandomItem());
                Tile tile2 = new Tile(j + 5 - 2*i, j, diceBasket.pickRandomItem(), terrainBasket.pickRandomItem());
                
                if (tile1 != null) {
                    tileArray[counter++] = tile1;
                }
                if (tile2 != null) {
                    tileArray[counter++] = tile2;
                }
            }
        }

        for (int i = 1; i <= 5; i++) {
            Tile tile = new Tile(i , i, diceBasket.pickRandomItem(), terrainBasket.pickRandomItem());

            if (tile != null) {
                tileArray[counter++] = tile;
            }
        }

        // initialise le tiles array
        int width = 10; // modifier 
        int height = 10; // modifier
        tiles = new Tile[width][height];
        for (Tile tile : tileArray) {

            if (tile != null) {
                int x = tile.getX();
                int y = tile.getY();

                if (x >= 0 && x < width && y >= 0 && y < height) {
                    tiles[x][y] = tile;
                } else {
                    System.err.println("Invalid tile coordinates: " + x + ", " + y);
                }
            }
        }
    }

    public static Tile getTile(Board board, int x, int y) {
        Tile[][] tiles = board.getTiles();

        if (x >= 0 && x < tiles.length && y >= 0 && y < tiles[0].length) {
            return tiles[x][y];
        }
        return null;
    }
  
/*
    public Node getNode(int x, int y) {
        //start from the top left if is in a Tile return null;
    }

    public static Tile getTile(int x, int y) {
        for (int i = 0; i < tileArray.length; i++) {
            if (tileArray[i].getX() == x && tileArray[i].getY() == y) {
                return (Tile)tileArray[i];
            }
        }
        
        return null;
    }

    private void printBoard() {
        //for debuging purpose
    }
*/

    public Tile[][] getTiles() {
        return tiles;
    }

    public int getWidth() {
        return tiles.length;
    }

    public int getHeight() {
        return tiles[0].length;
    }
    
    /*
    public static void main(String[] args) {
        createBoard();
        System.out.println(getTile(3, 0).getTerrain());
    }
    */
}
