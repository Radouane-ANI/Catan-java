package src.java.gui;

import src.java.logic.Bank;
import src.java.logic.Card;

import javax.swing.*;
import java.awt.*;

public class BankPanel extends JPanel {
    Bank bank;
    private static final int IMAGE_COUNT = 7; // 总共7张图片
    // 假定图片路径和数字已经正确设置
    private String[] imagePaths = {
            "/Users/juliazhula/k-catan/src/ressources/cardTest.jpg",
            "/Users/juliazhula/k-catan/src/ressources/cardTest.jpg",
            "/Users/juliazhula/k-catan/src/ressources/cardTest.jpg",
            "/Users/juliazhula/k-catan/src/ressources/cardTest.jpg",
            "/Users/juliazhula/k-catan/src/ressources/cardTest.jpg",
            "/Users/juliazhula/k-catan/src/ressources/cardTest.jpg",
            "/Users/juliazhula/k-catan/src/ressources/cardTest.jpg"
    };
    private int[] numbers = new int[IMAGE_COUNT];

    private ImageIcon scaledIcon;

    public BankPanel(Bank bank) {
        this.bank = bank;
        setLayout(new GridLayout(IMAGE_COUNT, 1)); // 7行1列的布局

        updateNumbers();

        for (int i = 0; i < IMAGE_COUNT; i++) {
            // 创建包含图片的JLabel
            ImageIcon icon = new ImageIcon(imagePaths[i]);
            JLabel imageLabel = new JLabel(icon);

            // 创建一个透明的JPanel来放置数字标签
            JPanel transparentPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            transparentPanel.setOpaque(false); // 使面板透明

            if (i > 0) { // 第一张图片除外
                JLabel numberLabel = new JLabel(String.valueOf(numbers[i]));
                numberLabel.setForeground(Color.RED);
                transparentPanel.add(numberLabel); // 在透明面板上添加数字标签
            }

            // 在图片标签上添加透明面板
            imageLabel.setLayout(new BorderLayout());
            imageLabel.add(transparentPanel, BorderLayout.NORTH);

            add(imageLabel);
        }
    }

    private void loadScaledIcon() {
        ImageIcon iconO = new ImageIcon("/Users/juliazhula/k-catan/src/ressources/cardTest.jpg");
        Image scaledImage = iconO.getImage().getScaledInstance((int) (iconO.getIconWidth() * 0.5), (int) (iconO.getIconHeight() * 0.5), Image.SCALE_SMOOTH);
        scaledIcon = new ImageIcon(scaledImage);
    }

    public void updateNumbers() {
        for (int i = 0; i < 5; i++) {
            numbers[i+1] = bank.getNumber(Card.values()[i]);
        }
        numbers[6] = bank.allDevCardsNumber();
    }

    public static void main(String[] args) {
        Bank bank1 = new Bank();
        JFrame frame = new JFrame("Image Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new BankPanel(bank1));
        frame.pack();
        frame.setVisible(true);
    }
}


