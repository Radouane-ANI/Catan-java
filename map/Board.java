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

        for (int d = 0; d < 6; d++) {
            for (int i = 0; i < 2; i++) {
                Direction dir = Direction.getDirection(Direction.SOUTH_EAST.ordinal() + d);
                if (buffer.getTileConnection(dir) == null) {
                    buffer.setTileConnection(new Tile(5, TerrainType.FOREST), dir);
                }
                else {
                    buffer.setTileConnection(buffer.getTileConnection(dir), dir);
                }
                buffer = buffer.getTileConnection(dir);
            }
        }

        buffer.setTileConnection(new Tile(5, TerrainType.FOREST), Direction.SOUTH);
        buffer = buffer.getTileConnection(Direction.SOUTH);

        for (int d = 0; d < 6; d++) {
            int numdirection = Direction.SOUTH_EAST.ordinal() + d;
            if (buffer.getTileConnection(Direction.getDirection(numdirection)) == null) {
                buffer.setTileConnection(new Tile(5, TerrainType.FOREST), Direction.getDirection(numdirection));
                for (int i = 4; i <= 6; i++) {
                    buffer.setTileConnection(buffer.getTileConnection(Direction.getDirection(numdirection + i)), 
                        Direction.getDirection(numdirection + i));
                }
            }
            else {
                buffer.setTileConnection(buffer.getTileConnection(Direction.getDirection(numdirection)), Direction.getDirection(numdirection));
            }
            buffer = buffer.getTileConnection(Direction.getDirection(numdirection));
        }

        buffer.setTileConnection(new Tile(5, TerrainType.FOREST), Direction.SOUTH);
        buffer = buffer.getTileConnection(Direction.SOUTH);

        for (int i = 1; i < 6; i++) {
            Direction dir = Direction.getDirection(i);
            buffer.setTileConnection(buffer.getTileConnection(dir), dir);
        }
    }
/*
    public Node getNode(int x, int y) {
        //start from the top left if is in a Tile return null;
    }*/

    public static Tile getTile(int x, int y) {
        Tile buffer = topTile;
        for (int i = 0; i < x && buffer != null; i++) {
            buffer = buffer.getTileConnection(Direction.SOUTH_WEST);
            for (int j = 0; j < y && buffer != null; j++) {
                buffer = buffer.getTileConnection(Direction.SOUTH_EAST);
            }
        }
        return buffer;
    }
/* 
    private void printBoard() {
        //for debuging purpose
    }*/
    
    public static void main(String[] args) {
        createBoard();
        System.out.println(getTile(2, 1).getTerrain());
    }
}
