package gui;

import logic.Bank;

import javax.swing.*;
import java.awt.*;

public class GameView extends JPanel {

    private DiceGUI dicePanel;
    private CatanBoardView boardView;
    private BankPanel bankPanel;
    private ExchangePanel exchangePanel;

    public GameView() {
        setLayout(new BorderLayout());
        dicePanel = new DiceGUI();
        Dimension size = getSize();
        boardView = new CatanBoardView(size);
        bankPanel = new BankPanel(new Bank());
        exchangePanel = ExchangePanel.createTestExchangePanel();

        add(dicePanel, BorderLayout.EAST);
        add(boardView, BorderLayout.CENTER);
        add(bankPanel, BorderLayout.NORTH); 
        add(exchangePanel, BorderLayout.SOUTH);
    }

    public DiceGUI getDicePanel() {
        return dicePanel;
    }

    public CatanBoardView getBoardView() {
        return boardView;
    }
}
