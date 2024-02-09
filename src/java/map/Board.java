package map;

public class Board {
    private static Board INSTANCE;

    private static Vector[] tileArray;

    public static void createBoard() {
        Board.INSTANCE = new Board();
    }

    private Board() {
        tileArray = new Vector[19];
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
            tileArray[counter++] = new Tile(j , j + 5 - 2*i, diceBasket.pickRandomItem(), terrainBasket.pickRandomItem());
            tileArray[counter++] = new Tile(j + 5 - 2*i, j, diceBasket.pickRandomItem(), terrainBasket.pickRandomItem());

            }
        }
        for (int i = 1; i <= 5; i++) {
            tileArray[counter++] = new Tile(i , i, diceBasket.pickRandomItem(), terrainBasket.pickRandomItem());
        }
    }
/*
    public Node getNode(int x, int y) {
        //start from the top left if is in a Tile return null;
    }*/

    public static Tile getTile(int x, int y) {
        for (int i = 0; i < tileArray.length; i++) {
            if (tileArray[i].getX() == x && tileArray[i].getY() == y) {
                return (Tile)tileArray[i];
            }
        }
        
        return null;
    }
/* 
    private void printBoard() {
        //for debuging purpose
    }*/
    
    public static void main(String[] args) {
        createBoard();
        System.out.println(getTile(3, 0).getTerrain());
    }
}
