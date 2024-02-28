package src.java.gui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import src.java.controleur.ViewControleur;

public class GameMenu extends JPanel {

    private Image backgroundImage;

    public GameMenu() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));
        loadImage("src/ressources/menu_2.jpeg");
        makeButtons();
    }

    private void makeButtons() {
        setLayout(new BorderLayout());
    
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);
    
        JButton playButton = createButton("src/ressources/play.png", "src/ressources/play_click.png", ViewControleur::jouer);
        buttonPanel.add(playButton, BorderLayout.NORTH);
    
        JButton extraButton = createButton("src/ressources/parametres.png", "src/ressources/parametres_click.png", () -> {});
        buttonPanel.add(extraButton, BorderLayout.CENTER);
    
        JButton quitButton = createButton("src/ressources/quit.png", "src/ressources/quit_click.png", ViewControleur::quitter);
        buttonPanel.add(quitButton, BorderLayout.SOUTH);
    
        add(buttonPanel, BorderLayout.SOUTH);
    }
    

    private JButton createButton(String imagePath, String hoverImagePath, Runnable action) {
        ImageIcon normalIcon = new ImageIcon(imagePath);
        ImageIcon hoverIcon = new ImageIcon(hoverImagePath);

        JButton button = new JButton(normalIcon);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setIcon(hoverIcon);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setIcon(normalIcon);
            }
        });

        button.addActionListener(e -> action.run());

        return button;
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
}
