package gui;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import controleur.Game;
import controleur.ViewControleur;
import logic.Card;
import logic.Player;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

public class GameView extends JPanel {
    private StateGUI stateGUI;
    private DiceGUI dicePanel;
    private CatanBoardView boardView;
    private BankPanel bankPanel;
    private ExchangePanel exchangePanel;
    private Game game;
    private ButtonsPanel buttonsPanel;

    public GameView(Game game) {
        this.game = game;
        setLayout(new BorderLayout());
        dicePanel = game.getDiceGUI();
        stateGUI = new StateGUI();
        JPanel panelLateral = new JPanel(new GridLayout(2, 1, 0, -180));
        panelLateral.add(dicePanel);
        panelLateral.add(stateGUI);
        panelLateral.setOpaque(false);

        Dimension size = getSize();
        boardView = new CatanBoardView(size);
        bankPanel = new BankPanel(ViewControleur.getBank());

        exchangePanel = new ExchangePanel(game.getCurrentPlayer());
        buttonsPanel = new ButtonsPanel(game);
        boardView.setOpaque(false);
        JPanel panelSuperieur = new JPanel(new GridLayout(1,2));
        panelSuperieur.add(bankPanel);
        panelSuperieur.add(buttonsPanel);
        panelSuperieur.setOpaque(false);

        add(panelLateral, BorderLayout.EAST);
        add(boardView, BorderLayout.CENTER);
        add(panelSuperieur, BorderLayout.NORTH);
        add(exchangePanel, BorderLayout.SOUTH);
        game.setGameView(this);
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

    public void update() {
        Player player = game.getCurrentPlayer();
        buttonsPanel.update();
        stateGUI.update(player);
        bankPanel.updateNumbers();
        exchangePanel.update(player);
    }
}
