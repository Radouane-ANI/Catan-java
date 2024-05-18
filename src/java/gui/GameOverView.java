package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import logic.Player;

public class GameOverView extends JPanel {

    public GameOverView(Player winner, List<Player> players) {
        setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Game Over", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        add(titleLabel, BorderLayout.NORTH);

        JLabel winnerLabel = new JLabel("Winner: " + winner.getName(), JLabel.CENTER);
        winnerLabel.setFont(new Font("Serif", Font.BOLD, 24));
        add(winnerLabel, BorderLayout.CENTER);

        JPanel rankingPanel = new JPanel();
        rankingPanel.setLayout(new BoxLayout(rankingPanel, BoxLayout.Y_AXIS));

        players.sort((p1, p2) -> Integer.compare(p2.getPoints(), p1.getPoints()));
        for (Player player : players) {
            JLabel playerLabel = new JLabel(player.getName() + " - " + player.getPoints() + " points");
            rankingPanel.add(playerLabel);
        }

        add(rankingPanel, BorderLayout.SOUTH);
    }
}
