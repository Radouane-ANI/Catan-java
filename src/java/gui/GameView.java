package src.java.gui;

import javax.swing.*;
import java.awt.*;

public class GameView extends JPanel {

    private DiceGUI dicePanel;
    private CatanBoardView boardView;

    public GameView() {
        setLayout(new BorderLayout());
        dicePanel = new DiceGUI();
        Dimension size = getSize();
        boardView = new CatanBoardView(size);
        
        add(dicePanel, BorderLayout.NORTH);
        add(boardView, BorderLayout.CENTER);
    }

    public DiceGUI getDicePanel() {
        return dicePanel;
    }

    public CatanBoardView getBoardView() {
        return boardView;
    }
}
