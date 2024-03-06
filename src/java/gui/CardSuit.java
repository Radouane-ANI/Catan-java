package src.java.gui;

import src.java.logic.Bank;
import src.java.logic.Card;
import src.java.logic.CardBox;
import src.java.logic.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardSuit extends JPanel {
    private static final int MY_CARD_LIST = 1;
    private static final int SALE_LIST = 2;
    private static final int WISH_LIST = 3;
    private Player player;
    private int cardSuitType;
    private CardBox cardBox;
    private ImageIcon scaledIcon;
    private Map<Card, List<JLabel>> cardBoxLabel;
    private JButton button;

    public CardSuit(Player player, int cardSuitType) {
        this.player = player;
        this.cardSuitType = cardSuitType;
        switch (cardSuitType) {
            case MY_CARD_LIST : cardBox = player.getMyCards();break;
            case SALE_LIST : cardBox = player.getSaleList();break;
            case WISH_LIST : cardBox = player.getWishList();break;
        }
        this.cardBoxLabel = new HashMap<>();
        button = new JButton("BUTTON");
        loadScaledIcon();
        initializeLabels();
    }

    public CardBox getCardBox() {
        return cardBox;
    }

    public JButton getButton() {
        return button;
    }

    public Map<Card, List<JLabel>> getCardBoxLabel() {
        return cardBoxLabel;
    }


    private void loadScaledIcon() {
        ImageIcon iconO = new ImageIcon("/Users/juliazhula/k-catan/src/ressources/cardTest.jpg");
        Image scaledImage = iconO.getImage().getScaledInstance((int) (iconO.getIconWidth() * 0.5), (int) (iconO.getIconHeight() * 0.5), Image.SCALE_SMOOTH);
        scaledIcon = new ImageIcon(scaledImage);
    }


    public void initializeLabels() {
        this.removeAll();
        this.setLayout(null);
        for(Card card : Card.values()) {
            cardBoxLabel.put(card,new ArrayList<>());
        }
        for(Card card : Card.values()) {
            List<JLabel> labelList = cardBoxLabel.get(card);
            for (int i = 0; i < cardBox.getNumber(card); i++) {
                JLabel label = new JLabel(scaledIcon);
                label.setName( card.name());
                labelList.add(label);
            }
        }
        positionCards();
    }

    private void positionCards() {
        int offsetX = 5;
        int offsetY = 5;
        int gap = 7;

        for (Card card : Card.values()) {
            List<JLabel> labels = cardBoxLabel.get(card);
            for (int i = 0; i < labels.size(); i++) {
                JLabel label = labels.get(i);
                int x = offsetX + (gap * i);
                label.setBounds(x, offsetY, label.getIcon().getIconWidth(), label.getIcon().getIconHeight());
                if (!this.isAncestorOf(label)) {
                    this.add(label);
                }
            }
            if (!labels.isEmpty()) {
                offsetX += gap * labels.size() + labels.get(0).getIcon().getIconWidth();
            }
        }
        if(cardSuitType != MY_CARD_LIST) {
            // 设置按钮的大小和位置，这里给了一个更合理的尺寸
            button.setBounds(400, 5, 30, 30); // 注意调整按钮位置和尺寸
            this.add(button); // 确保按钮被添加到面板上
        }
        this.revalidate(); // 确保面板更新
        this.repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 60);
    }

    public static void main(String[] args) {
        Player player = new Player(false,"Sam",new Bank());
        CardBox cardBox = new CardBox();
        int[] x = {1,2,3,4,5};
        cardBox.setCardsNumbers(x);
        player.setMyCards(cardBox);

        JFrame frame = new JFrame("Overlapping Cards");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CardSuit cardSuit = new CardSuit(player,2);


        frame.add(cardSuit,BorderLayout.SOUTH);

        frame.pack();
        frame.setSize(1000, 700);
        frame.setVisible(true);
    }
}
