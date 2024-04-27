package gui;

import logic.Card;
import logic.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ButtonsPanel extends JPanel {
    private static final String BASE_PATH = "/Users/juliazhula/k-catan/src/ressources/";
    private Player player;
    private ExchangePanel exchangePanel;
    private static final int NUMBER_OF_BUTTONS = 3;
    private static final String[] IMAGE_PATH = {
            BASE_PATH+"exchange.png",
            BASE_PATH+"devButton.png",
            BASE_PATH+"go.png"
    };
    private ImageIcon[] buttonIcons = new ImageIcon[NUMBER_OF_BUTTONS];

    private NumberedButton[] buttons = new NumberedButton[NUMBER_OF_BUTTONS];


    public ButtonsPanel(Player player, ExchangePanel exchangePanel) {
        this.exchangePanel = exchangePanel;
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
                case 2 : button.setActionCommand("Go");
                         button.setNumber(-1);
                         break;
            }
            button.setSize(buttonIcons[i].getIconWidth(), buttonIcons[i].getIconHeight());
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
            case 2 : setGoButton();break;
        }
    }

    public void setExchangeButton() {
        buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exchangePanel.exchangeListVisible(true);
            }
        });
    }

    public void setGetDevButton() {
        buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Card dev = player.getDevCard(player.getMyCards(),player.getBank());
                exchangePanel.initializeMyCards();

                if (dev != null && dev.ordinal() == 9) {
                    player.setExtraPoint(1);
                    System.out.println(player.getExtraPoint());
                }
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

    private void updateGo() {
        buttons[2].setEnabled(player.isMyTurn());
    }

    private void updateGetDev() {
        buttons[1].setEnabled(player.canExchangeDev(player.getMyCards(),exchangePanel.bankPanel.bank));
    }

    private void updateExchange() {
        buttons[0].setEnabled(player.isMyTurn());
    }

    protected void updateButtons() {
        updateGo();
        updateGetDev();
        updateExchange();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(50, 80);
    }
}
