package gui;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.BoxLayout;

import controleur.Game;
import controleur.ViewControleur;
import logic.Player;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.List;

public class GameView extends JPanel {
    private StateGUI stateGUI;
    private CatanBoardView boardView;
    private BankPanel bankPanel;
    private ExchangePanel exchangePanel;
    private Game game;
    private ButtonsPanel buttonsPanel;
    private JPanel panelTempo;

    public GameView(Game game) {
        this.game = game;
        setLayout(new BorderLayout());
        DiceGUI dicePanel = game.getDiceGUI();
        stateGUI = new StateGUI();
        JPanel panelLateral = new JPanel(new GridLayout(2, 1));
        panelLateral.add(dicePanel);
        panelLateral.add(stateGUI);
        panelLateral.setOpaque(false);

        Dimension size = getSize();
        boardView = new CatanBoardView(size);
        bankPanel = new BankPanel(ViewControleur.getBank());

        exchangePanel = new ExchangePanel(game.getCurrentPlayer());
        buttonsPanel = new ButtonsPanel(game);
        boardView.setOpaque(false);
        JPanel panelSuperieur = new JPanel(new GridLayout(1, 2));
        panelSuperieur.add(bankPanel);
        panelSuperieur.add(buttonsPanel);
        panelSuperieur.setOpaque(false);
        JPanel panelInferieur = new JPanel(new BorderLayout());
        panelInferieur.add(exchangePanel, BorderLayout.WEST);
        panelTempo = new JPanel();
        panelInferieur.add(panelTempo, BorderLayout.EAST);
        panelTempo.setLayout(new BoxLayout(panelTempo, BoxLayout.Y_AXIS));
        panelTempo.setOpaque(false);
        panelInferieur.setOpaque(false);

        add(panelLateral, BorderLayout.EAST);
        add(boardView, BorderLayout.CENTER);
        add(panelSuperieur, BorderLayout.NORTH);
        add(panelInferieur, BorderLayout.SOUTH);
        game.setGameView(this);
    }

    public CatanBoardView getBoardView() {
        return boardView;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image backgroundImage = new ImageIcon("src/ressources/background.jpg").getImage();
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
    }

    public void proposeEchange(Player currentPlayer, List<Player> accepter, Player p) {
        ProposeEchange prop = new ProposeEchange(currentPlayer, accepter, p, game);
        panelTempo.add(prop);
    }

    public void update() {
        Player player = game.getCurrentPlayer();
        buttonsPanel.update();
        stateGUI.update(player);
        bankPanel.updateNumbers();
        exchangePanel.update(player);
    }

    public void updateStolen(Player player) {
        stateGUI.update(player);
        bankPanel.updateNumbers();
        panelTempo.add(new DiscardPanel(player));
        exchangePanel.setVisible(false);
        panelTempo.revalidate();
        panelTempo.repaint();
    }
}
