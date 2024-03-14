package src.java.gui;

import javax.swing.*;
import java.awt.*;

public class GameView extends JPanel {

    private DiceGUI dicePanel;
    private CatanBoardView boardView;

    public GameView() {
        setLayout(new BorderLayout());

        // Create the dice panel
        dicePanel = new DiceGUI();

        // Create the Catan board view
        // Get the size of the GameView panel
        Dimension size = getSize();
        boardView = new CatanBoardView(size);

        // Add components to the game view
        add(dicePanel, BorderLayout.NORTH);
        add(boardView, BorderLayout.CENTER);
    }

    // You can add methods to access the dice panel or board view if needed
    public DiceGUI getDicePanel() {
        return dicePanel;
    }

    public CatanBoardView getBoardView() {
        return boardView;
    }
}
