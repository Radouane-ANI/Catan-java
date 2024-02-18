package map;

import javax.swing.JFrame;
import java.awt.*;
import gui.*;

public class Board extends MapElementsApi {

    public static void createBoard() {
        Tile.createTiles();
        Node.createNodes();
        Edge.createEdge();
    }    

    public static void main(String[] args) {
        createBoard();

        JFrame frame = new JFrame("Catan Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        CatanBoardView boardView = new CatanBoardView(frame.getSize());
        frame.getContentPane().add(boardView, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }

}
