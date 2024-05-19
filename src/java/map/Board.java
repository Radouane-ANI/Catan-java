package map;

public class Board extends MapElementsApi {

    public static void createBoard() {
        Tile.createTiles();
        Node.createNodes();
        Port.createPorts();

        Edge.createEdge();
    }

    
    public static void main(String[] args) {
        createBoard();
        /*System.out.println(Tile.getTile(3, 0).getTerrain());

        for (Vector n : Tile.getTile(1, 1).getNeighbors()) {
            System.out.println(n);
        }*/
    }
}
