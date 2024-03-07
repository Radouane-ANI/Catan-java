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
    private int[] numbers;

    private JLabel[] labels;

    //private ImageIcon scaledIcon;

    private ImageIcon scaledIcon;

    public BankPanel(Bank bank) {
        this.bank = bank;
        labels = new JLabel[IMAGE_COUNT];
        numbers = new int[IMAGE_COUNT];
        updateNumbers();
        setLayout(null);
        loadScaledIcon();
        initializeLabels();
    }

    private void loadScaledIcon() {
        ImageIcon iconO = new ImageIcon("/Users/juliazhula/k-catan/src/ressources/cardTest.jpg");
        Image scaledImage = iconO.getImage().getScaledInstance((int) (iconO.getIconWidth() * 0.3), (int) (iconO.getIconHeight() * 0.3), Image.SCALE_SMOOTH);
        scaledIcon = new ImageIcon(scaledImage);
    }

    private void initializeLabels() {
        for(int i = 0; i < IMAGE_COUNT ;i++) {
            JLabel label = new JLabel(scaledIcon);
            labels[i] = label;
        }
        positionLabels();
    }

    private void positionLabels() {
        int offsetX = 5; // 初始X偏移
        int offsetY = 5;  // Y偏移
        int gap = 20;     // 组件间的间隔

        for(int i =0; i < IMAGE_COUNT; i++) {
            JLabel label = labels[i];
            label.setBounds(offsetX, offsetY, label.getIcon().getIconWidth(), label.getIcon().getIconHeight());
            this.add(label); // 将标签添加到面板上
            offsetX += label.getIcon().getIconWidth() + gap; // 更新X偏移，为下一个标签计算位置
            // 创建一个透明的JPanel来放置数字标签
            JPanel transparentPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            transparentPanel.setOpaque(false); // 使面板透明

            if ( i > 0) { // 第一张图片除外
                JLabel numberLabel = new JLabel(String.valueOf(numbers[i]));
                numberLabel.setForeground(Color.RED);
                numberLabel.setOpaque(true);
                numberLabel.setBackground(Color.BLUE);
                transparentPanel.add(numberLabel); // 在透明面板上添加数字标签
            }

            // 在图片标签上添加透明面板
            label.setLayout(new BorderLayout());
            label.add(transparentPanel, BorderLayout.NORTH);

        }
        this.revalidate(); // 确保面板更新
        this.repaint();
    }

    public void updateNumbers() {
        for (int i = 0; i < 5; i++) {
            numbers[i+1] = bank.getNumber(Card.values()[i]);
        }
        numbers[6] = bank.allDevCardsNumber();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 50);
    }

    public static void main(String[] args) {
        Bank bank1 = new Bank();
        JFrame frame = new JFrame("Image Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1,2));
        JPanel jPanel1 = new JPanel(new BorderLayout());
        jPanel1.add(new BankPanel(bank1),BorderLayout.NORTH);
        frame.add(jPanel1);
        frame.setSize(1000, 700);

        frame.pack();
        frame.setVisible(true);
    }
}


