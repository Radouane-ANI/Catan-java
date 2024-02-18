package map;

import javax.swing.JFrame;
import java.awt.*;

public class Board extends MapElementsApi {

    private static Tile[][] board;

    public static void createBoard() {
        Tile.createTiles();
        Node.createNodes();
        Edge.createEdge();
        board = Tile.getBoard();
    }    

    public static Tile[][] getBoard() {
        return board;
    }

    public static void main(String[] args) {
        createBoard();

        JFrame frame = new JFrame("Catan Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        CatanBoardView boardView = new CatanBoardView(getBoard());
        frame.getContentPane().add(boardView, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }

}
