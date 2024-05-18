package gui;

import controleur.ViewControleur;
import logic.Player;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameOverView extends JPanel {
    private Player winner;
    private List<Player> players;
    private Image backgroundImage;

    public GameOverView(Player winner, List<Player> players) {
        this.winner = winner;
        this.players = players;
        loadImage("src/ressources/endGame.png");
        initializeUI();
    }

    private void loadImage(String path) {
        backgroundImage = new ImageIcon(path).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        JLabel winnerLabel = new JLabel("Winner: " + winner.getName(), SwingConstants.CENTER);
        winnerLabel.setFont(new Font("Serif", Font.BOLD, 24));
        winnerLabel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
        add(winnerLabel, BorderLayout.NORTH);

        JPanel rankingPanel = new JPanel();
        rankingPanel.setLayout(new BoxLayout(rankingPanel, BoxLayout.Y_AXIS));
        rankingPanel.setOpaque(false);
        for (Player player : players) {
            JLabel playerLabel = new JLabel(player.getName() + ": " + player.getPoints() + " points");
            playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            playerLabel.setForeground(Color.WHITE);
            playerLabel.setFont(new Font("Serif", Font.BOLD, 16));
            rankingPanel.add(playerLabel);
        }

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(rankingPanel);
        add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));

        JButton menuButton = new JButton("Back to Menu");
        menuButton.addActionListener(e -> ViewControleur.showMainMenu());

        buttonPanel.add(quitButton);
        buttonPanel.add(menuButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
