package gui;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import controleur.Game;
import controleur.ViewControleur;

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
        //exchangePanel = ExchangePanel.createTestExchangePanel();
        buttonsPanel = new ButtonsPanel(game);
        boardView.setOpaque(false);
        add(panelLateral, BorderLayout.EAST);
        add(boardView, BorderLayout.CENTER);
        add(bankPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);
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
        buttonsPanel.update();
        stateGUI.update(game.getCurrentPlayer());
        bankPanel.updateNumbers();
    }
}
