package gui;

import logic.Bank;
import logic.Player;

import javax.swing.*;
import java.awt.*;

public class PersonalScorePanel extends JPanel {
    private static final String BASE_PATH = "/Users/juliazhula/k-catan/src/ressources/scorePanel/";
    private Player player;
    private int type;
    private String name;
    private static final int PLAYER = 1;
    private static final int GREEN_BOT = 2;
    private static final int ORANGE_BOT = 3;
    private static final int BLUE_BOT = 4;
    private String[] imageFiles = new String[3];
    private ImageIcon scaledIcon[] = new ImageIcon[3];
    private JLabel[] images = new JLabel[3];
    private JLabel[] numberLabels = new JLabel[3];
    private int[] numbers = new int[3]; //stocker trois scores: points total, number de knights utilisé, length of bridge

    public PersonalScorePanel(int type, Player player) {
        this.type = type;
        this.player = player;

        initialName();
        initialImageFile();
        loadScaledIcon();
        updateNumber();
        initialNumberLabels();
        initialImageLabels();

        JLabel nameLabel = new JLabel(this.name);
        nameLabel.setBackground(Color.blue);

        JPanel panelForScore = new JPanel();
        panelForScore.setLayout(new OverlayLayout(panelForScore));

        panelForScore.add(images[0]);
        panelForScore.add(numberLabels[0]);

        this.setLayout(new BorderLayout());
        this.add(panelForScore,BorderLayout.WEST);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        panel.add(images[1]);
        panel.add(numberLabels[1]);
        panel.add(images[2]);
        panel.add(numberLabels[2]);

        JPanel panel1 = new JPanel(new GridLayout(2,1));
        panel1.add(nameLabel);
        panel1.add(panel);

        this.add(panel1);
    }

    private void initialName() {
        switch (type) {
            case 1 : this.name = player.getName(); break;
            case 2 : this.name = "Louis"; break;
            case 3 : this.name = "Alice"; break;
            case 4 : this.name = "Bob"; break;
        }
    }

    private void initialImageFile() {
        switch (type) {
            case 1 : imageFiles[0] = "player.png";break;
            case 2 : imageFiles[0] = "bot_green.png";break;
            case 3 : imageFiles[0] = "bot_orange.png";break;
            case 4 : imageFiles[0] = "bot_blue.png";break;
        }
        imageFiles[1] = "knights.png";
        imageFiles[2] = "bridge.png";
    }

    private void loadScaledIcon() {
        double scaledSize = 0;
        for (int i = 0; i < 3; i++) {
            if(i != 0) {
                scaledSize = 0.3;
            }else{
                scaledSize = 0.6;
            }
            ImageIcon icon = new ImageIcon(BASE_PATH+imageFiles[i]);
            System.out.println(BASE_PATH+imageFiles[i]);
            Image scaledImage = icon.getImage().getScaledInstance((int) (icon.getIconWidth() * scaledSize), (int) (icon.getIconHeight() * scaledSize), Image.SCALE_SMOOTH);
            scaledIcon[i] = new ImageIcon(scaledImage);
        }
    }

    private void updateNumber() {
        numbers[0] = player.getPoints();
        numbers[1] = player.numberOfKnightsUsed;
        numbers[2] = player.roadLength;
    }

    private void initialNumberLabels() {
        for (int i = 0; i < 3; i++) {
            if(i == 0 ) {
                if(player.getExtraPoint() != 0 ) {
                    numberLabels[i] = new JLabel((String.valueOf(player.getExtraPoint()))+String.valueOf(numbers[i]));
                }else{
                    numberLabels[i] = new JLabel(String.valueOf(numbers[i]));
                }
                Dimension dimension = new Dimension(scaledIcon[0].getIconWidth(),scaledIcon[0].getIconHeight());
                numberLabels[i].setPreferredSize(dimension);
            } else
            { numberLabels[i] = new JLabel(String.valueOf(numbers[i])); }
        }
    }

    private void initialImageLabels() {
        for (int i = 0; i < 3; i++) {
            images[i] = new JLabel(scaledIcon[i]);
        }
    }

    public static void main(String[] args) {
        Player p = new Player(false,"Tom",new Bank(),Color.white);
        PersonalScorePanel panel = new PersonalScorePanel(1,p);

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jFrame.add(panel);

        jFrame.setSize(1000, 700);
        jFrame.setVisible(true);
    }
}
