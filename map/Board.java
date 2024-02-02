package map;

public class Board {
    private static Board INSTANCE;

    public static void createBoard() {
        Board.INSTANCE = new Board();
    }

    private Board() {

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
