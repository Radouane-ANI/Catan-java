package gui;

import controleur.ViewControleur;
import logic.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GameOverView extends JPanel {
    private Player winner;
    private List<Player> players;

    public GameOverView(Player winner, List<Player> players) {
        this.winner = winner;
        this.players = players;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        JLabel winnerLabel = new JLabel("Winner: " + winner.getName(), SwingConstants.CENTER);
        winnerLabel.setFont(new Font("Serif", Font.BOLD, 24));
        add(winnerLabel, BorderLayout.NORTH);

        // Display the rankings
        JPanel rankingPanel = new JPanel();
        rankingPanel.setLayout(new BoxLayout(rankingPanel, BoxLayout.Y_AXIS));
        for (Player player : players) {
            JLabel playerLabel = new JLabel(player.getName() + ": " + player.getPoints() + " points");
            rankingPanel.add(playerLabel);
        }
        add(rankingPanel, BorderLayout.CENTER);

        // Add buttons
        JPanel buttonPanel = new JPanel();
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        JButton menuButton = new JButton("Back to Menu");
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement logic to return to the main menu
                // For example, you might call a method in your main controller
                ViewControleur.showMainMenu();
            }
        });
        buttonPanel.add(quitButton);
        buttonPanel.add(menuButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
