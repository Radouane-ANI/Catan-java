package gui;

import logic.Bank;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import controleur.Game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

public class GameView extends JPanel {

    private DiceGUI dicePanel;
    private CatanBoardView boardView;
    private BankPanel bankPanel;
    private ExchangePanel exchangePanel;
    public Game game;

    public GameView(Game game) {
        this.game = game;
        setLayout(new BorderLayout());
        dicePanel = game.getDiceGUI();
        Dimension size = getSize();
        boardView = new CatanBoardView(size);
        bankPanel = new BankPanel(new Bank());
        exchangePanel = ExchangePanel.createTestExchangePanel();
        dicePanel.setOpaque(false);
        boardView.setOpaque(false);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image backgroundImage = new ImageIcon(getClass().getResource("/src/ressources/background.jpg")).getImage();
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
    }
}
