package gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import logic.Player;

public class StateGUI extends JPanel {
    private Image backgroundImage;
    private JLabel labelPlayer;
    private JLabel labelPoint;

    public StateGUI() {
        backgroundImage = new ImageIcon("src/ressources/planche.png").getImage();
        setOpaque(false);
        setLayout(null);
        labelPlayer = new JLabel("Player");
        labelPlayer.setForeground(Color.WHITE);
        labelPlayer.setFont(new Font("Arial", Font.BOLD, 24));
        labelPlayer.setBounds(120, 60, 300, 50);

        labelPoint = new JLabel("Point");
        labelPoint.setForeground(Color.WHITE);
        labelPoint.setFont(new Font("Arial", Font.BOLD, 20));
        labelPoint.setBounds(120, 90, 300, 50);
        add(labelPlayer);
        add(labelPoint);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        labelPlayer.setBounds(120, (int) (getHeight() / 3), 300, 50);
        labelPoint.setBounds(120, (int) (getHeight() / 2.2), 300, 50);
    }

    public void update(Player currentPlayer) {
        labelPlayer.setText(currentPlayer.getName());
        labelPlayer.setForeground(currentPlayer.getColor());
        labelPoint.setText("Point : " + currentPlayer.getPoints());
    }

}
