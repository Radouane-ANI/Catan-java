package gui;

import logic.Bank;
import logic.Player;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

public class GameView extends JPanel {

    private DiceGUI dicePanel;
    private CatanBoardView boardView;
    private ExchangePanel exchangePanel;

    public GameView(Player player, Bank bank) {
        setLayout(new BorderLayout());
        dicePanel = new DiceGUI();
        Dimension size = getSize();
        boardView = new CatanBoardView(size);

        exchangePanel = new ExchangePanel(player,bank);
        dicePanel.setOpaque(false);
        boardView.setOpaque(false);
        add(dicePanel, BorderLayout.EAST);
        add(boardView, BorderLayout.CENTER);
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
