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
    Player p;
    private Map<Card,List<JLabel>> cardsLabel;
    private ImageIcon scaledIcon;

    public CardPanel(Player p) {
        this.p = p;
        setLayout(null);
        loadScaledIcon();
        initializeAllCards();
    }

    private void loadScaledIcon() {
        ImageIcon iconO = new ImageIcon("/Users/juliazhula/k-catan/src/ressources/cardTest.jpg");
        Image scaledImage = iconO.getImage().getScaledInstance((int) (iconO.getIconWidth() * 0.6), (int) (iconO.getIconHeight() * 0.6), Image.SCALE_SMOOTH);
        scaledIcon = new ImageIcon(scaledImage);
    }

    private void initializeAllCards() {
        cardsLabel = new HashMap<>();
        for(Card card : Card.values()) {
            cardsLabel.put(card,new ArrayList<>());
        }
        for (Card card : Card.values()) {
            List<JLabel> labels = cardsLabel.get(card);
            for (int i = 0; i < p.getMyCards().getNumber(card); i++) {
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
                p.addInSaleList(Card.valueOf(clickedLabel.getName()));
                List<JLabel> labels = cardsLabel.get(Card.valueOf(clickedLabel.getName()));
                labels.remove(clickedLabel);
                positionCards();
                CardPanel.this.revalidate();
                CardPanel.this.repaint();
            }
        });
    }

    private void positionCards() {
        int offsetX = 0;
        int offsetY = 5;
        int gap = 8;

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
        return new Dimension(100, 70);
    }

    public static void main(String[] args) {
        Player player = new Player(false,"Sam",new Bank());
        CardBox cardBox = new CardBox();
        int[] x = {1,2,3,4,5};
        cardBox.setCardsNumbers(x);
        player.setMyCards(cardBox);
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Overlapping Cards");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JPanel panel = new JPanel(new GridLayout(1,2));
            panel.setBackground(Color.pink);
            JPanel h = new CardPanel(player);
            h.setBackground(Color.white);
            panel.add(h);
            panel.add(new CardPanel(player));
            frame.getContentPane().add(panel,BorderLayout.SOUTH);
            frame.setSize(800, 700);
            //frame.pack();
            frame.setVisible(true);
        });
    }
}
