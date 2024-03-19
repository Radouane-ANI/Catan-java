package gui;

import logic.Player;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ButtonsPanel extends JPanel {
    private static final String BASE_PATH = "/Users/juliazhula/k-catan/src/ressources/";
    private Player player;
    private static final int NUMBER_OF_BUTTONS = 3;
    private static final String[] IMAGE_PATH = {
            BASE_PATH+"exchange.png",
            BASE_PATH+"devButton.png",
            BASE_PATH+"go.png"
    };
    private ImageIcon[] buttonIcons = new ImageIcon[NUMBER_OF_BUTTONS];

    private NumberedButton[] buttons = new NumberedButton[NUMBER_OF_BUTTONS];


    public ButtonsPanel(Player player) {
        this.player = player;
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
                case 0 : button.setActionCommand("Exchange Card");
                         button.setNumber(-1);
                         break;
                case 1 : button.setActionCommand("Get DevCard");
                         button.setNumber(-1);
                         break;
                /*case 2 : button.setActionCommand("Build Road");
                         button.setNumber(14);
                         break;
                case 3 : button.setActionCommand("Build Settlement");
                         button.setNumber(4);
                         break;
                case 4 : button.setActionCommand("Build City");
                         button.setNumber(4);
                         break;*/
                case 2 : button.setActionCommand("Go");
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
            case 0 : setExchangeButton();break;
            case 1 : setGetDevButton();break;
           /* case 2 : setBuildRoadButton();break;
            case 3 : setBuildSettlementButton();break;
            case 4 : setBuildCityButton();break;*/
            case 2 : setGoButton();break;
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

            }
        });
    }

    public void setBuildSettlementButton() {
        buttons[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void setBuildCityButton() {
        buttons[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void setGoButton() {
        buttons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }



    public void update() {
        if(player.isMyTurn()) {
            if(player.isDiced()) {
                buttons[5].setEnabled(true);
                buttons[0].setEnabled(player.getMyCards().getNumberOfRes()>0);
                buttons[1].setEnabled(player.canExchangeDev(player.getMyCards(),player.getBank()));
                buttons[2].setEnabled(buttons[2].getNumber()>0 && player.canBuildRoad());
                buttons[3].setEnabled(buttons[3].getNumber()>0 && player.canBuildSettlement());
                buttons[4].setEnabled(buttons[4].getNumber()>0 && player.canBuildCity());
            }
        }
        else{
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
