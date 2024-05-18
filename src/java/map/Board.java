package map;

public class Board extends MapElementsApi {

    public static void createBoard() {
        Tile.createTiles();
        Node.createNodes();
        Edge.createEdge();
    }
}
