package src.java.gui;

import src.java.logic.Bank;
import src.java.logic.Card;
import src.java.logic.CardBox;
import src.java.logic.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardPanel extends JPanel{
    private static final int MY_CARD_LIST = 1;

    private static final int SALE_LIST = 2;

    private static final int WISH_LIST = 3;

    private int panelType;
    private CardBox cardBox;
    private Player p;
    private Map<Card,List<JLabel>> cardsLabel;
    private ImageIcon scaledIcon;

    private CardPanel exchangeList;

    public CardPanel(Player p, int panelType, CardPanel exchangeList) {
        this.p = p;
        this.panelType = panelType;
        this.exchangeList = exchangeList;
        switch (panelType) {
            case 1 : cardBox = p.getMyCards();break;
            case 2 : cardBox = p.getSaleList();break;
            case 3 : cardBox = p.getWishList();break;
        }
        setLayout(null);
        loadScaledIcon();
        initializeAllCards();
    }

    public CardBox getCardBox() {
        return cardBox;
    }

    private void loadScaledIcon() {
        ImageIcon iconO = new ImageIcon("/Users/juliazhula/k-catan/src/ressources/cardTest.jpg");
        Image scaledImage = iconO.getImage().getScaledInstance((int) (iconO.getIconWidth() * 0.5), (int) (iconO.getIconHeight() * 0.5), Image.SCALE_SMOOTH);
        scaledIcon = new ImageIcon(scaledImage);
    }

    private void initializeAllCards() {
        cardsLabel = new HashMap<>();
        for(Card card : Card.values()) {
            cardsLabel.put(card,new ArrayList<>());
        }
        for (Card card : Card.values()) {
            List<JLabel> labels = cardsLabel.get(card);
            for (int i = 0; i < cardBox.getNumber(card); i++) {
                JLabel label = new JLabel(scaledIcon);
                label.setName( card.name());
                if(i==0) {
                    addMouseListenerToLabel(label);
                }
                labels.add(label);
            }
        }
        positionCards();
    }

    private void addMouseListenerToLabel(JLabel label) {
        for (MouseListener listener : label.getMouseListeners()) {
            label.removeMouseListener(listener);
        }
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JLabel clickedLabel = (JLabel) e.getSource();
                CardPanel.this.remove(clickedLabel);
                switch (panelType) {
                    case 1 : p.addInSaleList(Card.valueOf(clickedLabel.getName()));

                    case 2 : p.rmFromSaleList(Card.valueOf(clickedLabel.getName()));

                    case 3 : p.rmFromWishList(Card.valueOf(clickedLabel.getName()));break;
                }
                List<JLabel> labels = cardsLabel.get(Card.valueOf(clickedLabel.getName()));
                labels.remove(clickedLabel);
                updatePanel();
            }
        });
    }


    private void positionCards() {
        int offsetX = 0;
        int offsetY = 5;
        int gap = 7;

        for (Card card : Card.values()) {
            List<JLabel> labels = cardsLabel.get(card);
            for (int i = 0; i < labels.size(); i++) {
                JLabel label = labels.get(i);
                int x = offsetX + (gap * i);
                label.setBounds(x, offsetY, label.getIcon().getIconWidth(), label.getIcon().getIconHeight());
                label.setName(card.name());
                if(i==0) {
                    addMouseListenerToLabel(label);
                }
                if (!this.isAncestorOf(label)) {
                    this.add(label);
                }
            }
            if (!labels.isEmpty()) {
                offsetX += gap * labels.size() + labels.get(0).getIcon().getIconWidth();
            }
        }
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 60);
    }


    public void updatePanel() {
        // 根据 panelType 更新 cardBox
        switch (panelType) {
            case MY_CARD_LIST:
                cardBox = p.getMyCards();
                break;
            case SALE_LIST:
                cardBox = p.getSaleList();
                break;
            case WISH_LIST:
                cardBox = p.getWishList();
                break;
        }

        // 从面板中移除所有旧的标签
        for (List<JLabel> labels : cardsLabel.values()) {
            for (JLabel label : labels) {
                this.remove(label);
            }
        }
        cardsLabel.clear(); // 清空 cardsLabel 映射

        // 重新初始化所有卡片标签
        initializeAllCards();

        // 重新定位标签
        positionCards();

        // 重新绘制面板以显示更新
        this.revalidate();
        this.repaint();
    }

    /*public void start(int framerate) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try{
                        Thread.sleep(1000/framerate);
                    } catch(InterruptedException v) {
                        v.printStackTrace();
                    }
                    updatePanel();
                }
            }
        };

        Thread thread = new Thread(runnable);

        thread.start();
    }*/

    /*public static void main(String[] args) {
        Player player = new Player(false,"Sam",new Bank());
        CardBox cardBox = new CardBox();
        int[] x = {1,2,3,4,5};
        cardBox.setCardsNumbers(x);
        player.setMyCards(cardBox);

        JFrame frame = new JFrame("Overlapping Cards");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = frame.getContentPane();

        contentPane.setLayout(new GridBagLayout());


        ExchangeDialog exchangeDialog = new ExchangeDialog(player);
        CardPanel cardPanel = new CardPanel(player,1);
        ButtonsPanel buttonsPanel = new ButtonsPanel(player);

        GridBagConstraints gbc = new GridBagConstraints();

        /*gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        gbc.gridx = 0; // 列位置
        gbc.gridy = 4; // 行位置，从0开始计数，所以第5行是4
        gbc.gridheight = 3; // 占据三行
        contentPane.add(exchangeDialog, gbc);


        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridheight = 1;
        contentPane.add(cardPanel,gbc);
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridheight = 1;
        contentPane.add(buttonsPanel,gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridheight = 1;
        contentPane.add(cardPanel,gbc);
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.gridheight = 1;
        contentPane.add(buttonsPanel,gbc);


        //frame.pack();
        frame.setSize(1000, 700);
        frame.setVisible(true);

        /*SwingUtilities.invokeLater(() -> {
            exchangeDialog.start(100);
            cardPanel.start(1);
            exchangeDialog.saleList.start(10);
            exchangeDialog.wishList.start(10);
        });
    }*/
}