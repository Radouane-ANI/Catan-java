package gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import logic.Player;

public class StateGUI extends JPanel {
    private Image backgroundImage;
    private JLabel labelPlayer;
    private JLabel labelPoint;

    public StateGUI() {
        backgroundImage = new ImageIcon(getClass().getResource("/src/ressources/planche.png")).getImage();
        setOpaque(false);
        setLayout(null);
        setPreferredSize(new Dimension(backgroundImage.getWidth(null), backgroundImage.getHeight(null)));
        labelPlayer = new JLabel("Player");
        labelPlayer.setForeground(Color.WHITE);
        labelPlayer.setFont(new Font("Arial", Font.BOLD, 24));
        labelPlayer.setBounds(150, 100, 300, 50);

        labelPoint = new JLabel("Point");
        labelPoint.setForeground(Color.WHITE);
        labelPoint.setFont(new Font("Arial", Font.BOLD, 20));
        labelPoint.setBounds(155, 150, 300, 50);
        add(labelPlayer);
        add(labelPoint);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
    }

    public void update(Player currentPlayer) {
        labelPlayer.setText(currentPlayer.getName());
        labelPlayer.setForeground(currentPlayer.getColor());
        labelPoint.setText("Point : " + currentPlayer.getPoints());
    }

}
