package map;

public class Board {
    private static Board INSTANCE;

    private static Tile topTile;

    private static Node[] nodeArray;
    private static Edge[] edgeArray;
    private static Tile[] tileArray;

    public static void createBoard() {
        Board.INSTANCE = new Board();
    }

    private Board() {
        topTile = new Tile(5, TerrainType.FOREST);
        Tile buffer = topTile;

        buffer.setTileConnection(new Tile(5, TerrainType.FOREST), Direction.SOUTH_EAST);
        buffer.getTileConnection(Direction.SOUTH_EAST)
                .setTileConnection(new Tile(5, TerrainType.FOREST), Direction.SOUTH_EAST);
        
        for (int i = 0; i < 2; i++) {
            buffer.setTileConnection(new Tile(5, TerrainType.FOREST), Direction.SOUTH_EAST);
            buffer.getTileConnection(Direction.SOUTH_EAST)
                    .setTileConnection(new Tile(5, TerrainType.FOREST), Direction.SOUTH_EAST);
        }
        
    }

    public Node getNode(int x, int y) {
        //start from the top left if is in a Tile return null;
    }

    public Tile getTile(int x, int y) {
        //start from the top left;
    }

    private void printBoard() {
        //for debuging purpose
    }
    
}
