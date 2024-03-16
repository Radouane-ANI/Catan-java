package gui;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class GameView extends JPanel {

    public GameView() {
        setBackground(new Color(0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image backgroundImage = new ImageIcon(getClass().getResource("/src/ressources/background.jpg")).getImage();
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
    }
}