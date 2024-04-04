package gui;

import logic.Player;
import javax.swing.*;

import controleur.Game;
import controleur.ViewControleur;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonsPanel extends JPanel {
    private static final String BASE_PATH = "src/ressources/";
    private Player player;
    private static final int NUMBER_OF_BUTTONS = 5;
    private static final String[] IMAGE_PATH = {
            BASE_PATH+"devButton.png",
            BASE_PATH+"road.png",
            BASE_PATH+"settlement.png",
            BASE_PATH+"city.png",
            BASE_PATH+"go.png"
    };
    private ImageIcon[] buttonIcons = new ImageIcon[NUMBER_OF_BUTTONS];

    private NumberedButton[] buttons = new NumberedButton[NUMBER_OF_BUTTONS];
    private Game game;
    private WeatherDisplay weatherDisplay;

    public ButtonsPanel(Player player) {
        this.player = player;
        setLayout(new GridLayout(1, NUMBER_OF_BUTTONS, 5, 0));
        loadScaledIcon();
        initializeButtons();
    }

    public ButtonsPanel(Game game, WeatherDisplay weatherDisplay) {
        this.game = game;
        this.weatherDisplay = weatherDisplay;
        this.player = game.getCurrentPlayer();
        setLayout(new GridLayout(1, NUMBER_OF_BUTTONS, 5, 0));
        loadScaledIcon();
        initializeButtons();
    }

    private void loadScaledIcon() {
        String imagFile = "";
        for (int i = 0; i < buttonIcons.length; i++) {
            imagFile = IMAGE_PATH[i];
            ImageIcon icon = new ImageIcon(imagFile);
            Image scaledImage = icon.getImage().getScaledInstance((int) (icon.getIconWidth() * 0.6), (int) (icon.getIconHeight() * 0.6), Image.SCALE_SMOOTH);
            buttonIcons[i] = new ImageIcon(scaledImage);
        }
    }

    private void initializeButtons() {
        for (int i = 0; i < NUMBER_OF_BUTTONS; i++) {
            NumberedButton button = new NumberedButton();
            button.setContentAreaFilled(false);
            switch (i) {
                case 0 : button.setActionCommand("Get DevCard");
                         button.setNumber(-1);
                         break;
                case 1 : button.setActionCommand("Build Road");
                         button.setNumber(-1);
                         break;
                case 2 : button.setActionCommand("Build Settlement");
                         button.setNumber(-1);
                         break;
                case 3 : button.setActionCommand("Build City");
                         button.setNumber(-1);
                         break;
                case 4 : button.setActionCommand("Go");
                         button.setNumber(-1);
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
            case 0 : setGetDevButton();break;
            case 1 : setBuildRoadButton();break;
            case 2 : setBuildSettlementButton();break;
            case 3 : setBuildCityButton();break;
            case 4 : setGoButton();break;
        }
    }

    public void setGetDevButton() {
        buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.getDevCard(player.getMyCards(),player.getBank());
            }
        });
    }

    public void setBuildRoadButton() {
        buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewControleur.getCatanControleur().buildRoad(player);
            }
        });
    }

    public void setBuildSettlementButton() {
        buttons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewControleur.getCatanControleur().buildSettlement(player);
            }
        });
    }

    public void setBuildCityButton() {
        buttons[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewControleur.getCatanControleur().buildCity(player);
            }
        });
    }

    public void setGoButton() {
        buttons[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                weatherDisplay.updateWeather();
                ViewControleur.NextTurn(false);
            }
        });
    }



    public void update() {
        this.player = game.getCurrentPlayer();
        if (!player.isBot() && player.isDiced()) {
            buttons[4].setEnabled(true);
            buttons[0].setEnabled(player.canExchangeDev(player.getMyCards(), player.getBank()));
            buttons[1].setEnabled(player.canBuildRoad());
            buttons[2].setEnabled(player.canBuildSettlement());
            buttons[3].setEnabled(player.canBuildCity());
        } else {
            for (int i = 0; i < NUMBER_OF_BUTTONS; i++) {
                buttons[i].setEnabled(false);
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 80);
    }
}
