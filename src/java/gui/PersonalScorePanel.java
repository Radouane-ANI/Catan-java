package gui;

import javax.swing.*;
import java.awt.*;

public class PersonalScorePanel extends JPanel {
    private static final String BASE_PATH = "/Users/juliazhula/k-catan/src/ressources/scorePanel";
    private static final int PLAYER = 1;
    private static final int GREEN_BOT = 2;
    private static final int ORANGE_BOT = 3;
    private static final int BLUE_BOT = 4;
    private String imageFile;
    private ImageIcon scaledIcon[] = new ImageIcon[3];
    private JLabel[] images = new JLabel[3];
    private JLabel[] numbers = new JLabel[3];

    public PersonalScorePanel(int type) {
        switch (type) {
            case 1 : imageFile = "player.png";break;
            case 2 : imageFile = "bot_green.png";break;
            case 3 : imageFile = "bot_orange.png";break;
            case 4 : imageFile = "bot_blue.png";break;
        }
    }

    private void loadScaledIcon(String fileName) {
        String imagFile = BASE_PATH+fileName;
        for (int i = 0; i < scaledIcon.length; i++) {
            ImageIcon icon = new ImageIcon(imagFile);
            Image scaledImage = icon.getImage().getScaledInstance((int) (icon.getIconWidth() * 0.6), (int) (icon.getIconHeight() * 0.6), Image.SCALE_SMOOTH);
            scaledIcon[i] = new ImageIcon(scaledImage);
        }
    }
}
