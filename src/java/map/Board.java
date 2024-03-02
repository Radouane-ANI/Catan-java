package map;

public class Board extends MapElementsApi {

    public static void createBoard() {
        Tile.createTiles();
        Node.createNodes();
        Edge.createEdge();
    }

    /* 
    public static void main(String[] args) {
        createBoard();

        Tile[] tiles = Tile.getTilesIntern();
        for (Tile tile : tiles) {
            if (tile != null) {
                System.out.println("Terrain at (" + tile.getPosition().getX() + ", " + tile.getY() + "): " + tile.getTerrain());
            }
        }
        
        for (Vector n : Tile.getTile(2, 1).getNeighbors()) {
            System.out.println(n);
        }

        JFrame frame = new JFrame("Catan Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        CatanBoardView boardView = new CatanBoardView(frame.getSize());
        frame.getContentPane().add(boardView, BorderLayout.CENTER);

        frame.setSize(600, 600);
        frame.setVisible(true);
    }
    */
    
    public static void main(String[] args) {
        createBoard();
        System.out.println(Tile.getTile(3, 0).getTerrain());

        for (Vector n : Tile.getTile(1, 1).getNeighbors()) {
            System.out.println(n);
        }
    }

    /*
     * public static void main(String[] args) {
     * createBoard();
     * 
     * Tile[] tiles = Tile.getTilesIntern();
     * for (Tile tile : tiles) {
     * if (tile != null) {
     * System.out.println("Terrain at (" + tile.getPosition().getX() + ", " +
     * tile.getY() + "): " + tile.getTerrain());
     * }
     * }
     * 
     * for (Vector n : Tile.getTile(2, 1).getNeighbors()) {
     * System.out.println(n);
     * }
     * 
     * JFrame frame = new JFrame("Catan Board");
     * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     * frame.getContentPane().setLayout(new BorderLayout());
     * 
     * CatanBoardView boardView = new CatanBoardView(frame.getSize());
     * frame.getContentPane().add(boardView, BorderLayout.CENTER);
     * 
     * frame.setSize(600, 600);
     * frame.setVisible(true);
     * }
     */

}
