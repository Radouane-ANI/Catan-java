package gui;

import logic.Player;
import javax.imageio.ImageIO;
import javax.swing.*;

import controleur.Game;
import controleur.ViewControleur;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ButtonsPanel extends JPanel {
    private Player player;
    private static final int NUMBER_OF_BUTTONS = 6;
    private static final String[] IMAGE_PATH = {
            "src/ressources/button1.png",
            "src/ressources/button1.png",
            "src/ressources/button1.png",
            "src/ressources/button1.png",
            "src/ressources/button1.png",
            "src/ressources/button1.png"
    };
    private ImageIcon[] buttonIcons = new ImageIcon[NUMBER_OF_BUTTONS];

    private NumberedButton[] buttons = new NumberedButton[NUMBER_OF_BUTTONS];
    private Game game;

    public ButtonsPanel(Player player) {
        this.player = player;
        setLayout(new GridLayout(1, NUMBER_OF_BUTTONS, 5, 0));
        for (int i = 0; i < NUMBER_OF_BUTTONS; i++) {
            loadAndResizeImage(IMAGE_PATH[i],i);
        }
        initializeButtons();
    }

    public ButtonsPanel(Game game) {
        this.game = game;
        this.player = game.getCurrentPlayer();
        setLayout(new GridLayout(1, NUMBER_OF_BUTTONS, 5, 0));
        for (int i = 0; i < NUMBER_OF_BUTTONS; i++) {
            loadAndResizeImage(IMAGE_PATH[i],i);
        }
        initializeButtons();
    }

    private void loadAndResizeImage(String imagePath, int i) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(imagePath));
            int size = 60;
            Image resizedImage = originalImage.getScaledInstance(size, size, Image.SCALE_SMOOTH);
            buttonIcons[i] = new ImageIcon(resizedImage);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Load image failed", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initializeButtons() {
        for (int i = 0; i < NUMBER_OF_BUTTONS; i++) {
            NumberedButton button = new NumberedButton();
            button.setContentAreaFilled(false);
            switch (i) {
                case 0 : button.setActionCommand("Exchange Card");
                         button.setNumber(-1);
                         break;
                case 1 : button.setActionCommand("Get DevCard");
                         button.setNumber(-1);
                         break;
                case 2 : button.setActionCommand("Build Road");
                         button.setNumber(2);
                         break;
                case 3 : button.setActionCommand("Build Settlement");
                         button.setNumber(3);
                         break;
                case 4 : button.setActionCommand("Build City");
                         button.setNumber(4);
                         break;
                case 5 : button.setActionCommand("Go");
                         button.setNumber(5);
                         break;
            }
            button.setIcon(buttonIcons[i]);
            button.setEnabled(false);
            buttons[i] = button;
            setButtons(i);
            add(button);
        }
    }

    public void setButtons(int NUMBER_OF_BUTTON) {
        switch (NUMBER_OF_BUTTON) {
            case 0 : setExchangeButton();break;
            case 1 : setGetDevButton();break;
            case 2 : setBuildRoadButton();break;
            case 3 : setBuildSettlementButton();break;
            case 4 : setBuildCityButton();break;
            case 5 : setGoButton();break;
        }
    }

    public void setExchangeButton() {
        buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void setGetDevButton() {
        buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.getDevCard(player.getMyCards(),player.getBank());
            }
        });
    }

    public void setBuildRoadButton() {
        buttons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewControleur.getCatanControleur().buildRoad(player);
            }
        });
    }

    public void setBuildSettlementButton() {
        buttons[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewControleur.getCatanControleur().buildSettlement(player);
            }
        });
    }

    public void setBuildCityButton() {
        buttons[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewControleur.getCatanControleur().buildCity(player);
            }
        });
    }

    public void setGoButton() {
        buttons[5].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewControleur.NextTurn(false);
                update();
            }
        });
    }



    public void update() {
        this.player = game.getCurrentPlayer();
        System.out.println(player.getMyCards());
        if (!player.isBot() && player.isDiced()) {
            buttons[5].setEnabled(true);
            buttons[0].setEnabled(player.getMyCards().getNumberOfRes() > 0);
            buttons[1].setEnabled(player.canExchangeDev(player.getMyCards(), player.getBank()));
            buttons[2].setEnabled(buttons[2].getNumber() > 0 && player.canBuildRoad());
            buttons[3].setEnabled(buttons[3].getNumber() > 0 && player.canBuildSettlement());
            buttons[4].setEnabled(buttons[4].getNumber() > 0 && player.canBuildCity());
        } else {
            for (int i = 0; i < NUMBER_OF_BUTTONS; i++) {
                buttons[i].setEnabled(false);
            }
        }
    }
}
