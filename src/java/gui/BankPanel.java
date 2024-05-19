package gui;

import logic.Bank;
import logic.Card;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FilenameFilter;

public class BankPanel extends JPanel {
    //private static final String BASE_PATH = "src/ressources/";
    private static final String BASE_PATH = "src/ressources/";
    Bank bank;
    private static final int IMAGE_COUNT = 7;

    private JLabel[] labels;
    private JLabel[] numberLabel;

    private ImageIcon scaledIcon[] = new ImageIcon[IMAGE_COUNT];

    public BankPanel(Bank bank) {
        this.bank = bank;
        labels = new JLabel[IMAGE_COUNT];
        numberLabel = new JLabel[IMAGE_COUNT];
        setLayout(null);
        loadScaledIcon();
        initializeLabels();
    }

    private void loadScaledIcon() {
        String imagFile = "";
        for (int i = 1; i < scaledIcon.length - 1; i++) {
            imagFile = getImagePathByPrefix(i-1);
            ImageIcon icon = new ImageIcon(imagFile);
            Image scaledImage = icon.getImage().getScaledInstance((int) (icon.getIconWidth() * 0.5), (int) (icon.getIconHeight() * 0.5), Image.SCALE_SMOOTH);
            scaledIcon[i] = new ImageIcon(scaledImage);
        }
        ImageIcon icon0 = new ImageIcon(BASE_PATH+"bank.png");
        Image scaledImage0 = icon0.getImage().getScaledInstance((int) (icon0.getIconWidth() * 0.5), (int) (icon0.getIconHeight() * 0.5), Image.SCALE_SMOOTH);
        scaledIcon[0] = new ImageIcon(scaledImage0);
        ImageIcon icon6 = new ImageIcon(BASE_PATH+"dev.png");
        Image scaledImage6 = icon6.getImage().getScaledInstance((int) (icon6.getIconWidth() * 0.5), (int) (icon6.getIconHeight() * 0.5), Image.SCALE_SMOOTH);
        scaledIcon[6] = new ImageIcon(scaledImage6);
    }

    public String getImagePathByPrefix(int prefix) {
        File dir = new File(BASE_PATH);

        if (!dir.exists() || !dir.isDirectory()) {
            return "Directory not found";
        }

        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith(String.valueOf(prefix));
            }
        };

        String[] matchingFiles = dir.list(filter);

        if (matchingFiles != null && matchingFiles.length > 0) {
            return BASE_PATH + matchingFiles[0];
        } else {
            return "No matching files found";
        }
    }


    private void initializeLabels() {
        this.removeAll();
        for(int i = 0; i < IMAGE_COUNT ;i++) {
            JLabel label = new JLabel(scaledIcon[i]);
            labels[i] = label;
        }
        positionLabels();
        updateNumbers();
    }

    private void positionLabels() {
        int offsetX = 5;
        int offsetY = 5;
        int gap = 20;

        for(int i =0; i < IMAGE_COUNT; i++) {
            JLabel label = labels[i];
            label.setBounds(offsetX, offsetY, label.getIcon().getIconWidth(), label.getIcon().getIconHeight());
            this.add(label);
            offsetX += label.getIcon().getIconWidth() + gap;
            ImageIcon iconO = new ImageIcon("src/ressources/0_tree.png");
            Dimension dimension = new Dimension((int)(iconO.getIconWidth() * 0.3), (int)(iconO.getIconHeight()* 0.1*0.3));
            JPanel transparentPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            transparentPanel.setPreferredSize(dimension);
            transparentPanel.setOpaque(false);
            transparentPanel.setBackground(Color.blue);

            if ( i > 0) {
                numberLabel[i] = new JLabel(String.valueOf(Card.values()[i-1])+"");
                numberLabel[i].setFont(new Font("Arial", Font.BOLD, 7));
                numberLabel[i].setForeground(Color.WHITE);

                transparentPanel.add(numberLabel[i]);
            }
            label.setLayout(new GridLayout(4,1));
            label.add(transparentPanel);
        }
        this.revalidate();
        this.repaint();
    }

    public void updateNumbers() {
        for (int i = 1; i < 6; i++) {
           numberLabel[i].setText(bank.getNumber(Card.values()[i-1])+"");
        }
        numberLabel[6].setText(bank.allDevCardsNumber()+"");
    }


    public void update() {
       updateNumbers();
       initializeLabels();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 60);
    }



    /*public static void main(String[] args) {
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
    }*/
}


