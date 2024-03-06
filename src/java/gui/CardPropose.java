package src.java.gui;

import src.java.logic.Card;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CardPropose extends JPanel {
    private ImageIcon scaledIcon;
    private Map<Card, JLabel> cardsLabel;

    public CardPropose() {
        this.cardsLabel = new HashMap<>();
        setLayout(null);
        loadScaledIcon();
        initializeAllCards();
    }

    public Map<Card, JLabel> getCardsLabel() {
        return cardsLabel;
    }

    private void loadScaledIcon() {
        ImageIcon iconO = new ImageIcon("/Users/juliazhula/k-catan/src/ressources/cardTest.jpg");
        Image scaledImage = iconO.getImage().getScaledInstance((int) (iconO.getIconWidth() * 0.5), (int) (iconO.getIconHeight() * 0.5), Image.SCALE_SMOOTH);
        scaledIcon = new ImageIcon(scaledImage);
    }

    private void initializeAllCards() {
        for(int i = 0; i < 5 ;i++) {
            JLabel label = new JLabel(scaledIcon);
            label.setName(Card.values()[i].name());
            cardsLabel.put(Card.values()[i],label);
        }
        positionCards();
    }

    private void positionCards() {
        int offsetX = 5; // 初始X偏移
        int offsetY = 5;  // Y偏移
        int gap = 20;     // 组件间的间隔

        // 遍历Map中的所有JLabel，设置它们的位置
        for(JLabel label : cardsLabel.values()) {
            label.setBounds(offsetX, offsetY, label.getIcon().getIconWidth(), label.getIcon().getIconHeight());
            this.add(label); // 将标签添加到面板上
            offsetX += label.getIcon().getIconWidth() + gap; // 更新X偏移，为下一个标签计算位置
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 60);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Overlapping Cards");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CardPropose cardPropose = new CardPropose();
        frame.add(cardPropose,BorderLayout.SOUTH);
        frame.setSize(1000, 700);
        frame.setVisible(true);
    }
}
