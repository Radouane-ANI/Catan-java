package gui;

import logic.Card;
import logic.CardBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.util.LinkedHashMap;
import java.util.Map;

public class CardPropose extends JPanel {
   // private static final String BASE_PATH = "src/ressources/";

    private int buttonType;//1--Bank 2--Yes

    private boolean isForMono;

    private String buttonName;

    private static final String BASE_PATH = "src/ressources/";
    private JButton button;
    private ImageIcon scaledIcon[] = new ImageIcon[5];
    private Map<Card, JLabel> cardsLabel;

    public CardPropose(int buttonType, boolean isForMono) {
        this.buttonType = buttonType;
        this.isForMono = isForMono;
        switch (buttonType) {
            case 1 -> buttonName = "bank.png";
            case 2 -> buttonName = "yes.png";
        }
        button = new JButton();
        loadButtonIcon();
        button.setEnabled(false);
        this.cardsLabel = new LinkedHashMap<>();
        setLayout(null);
        loadScaledIcon();
        if (buttonType == 1) {
            initializeAllCards();
        } else {
            initializeForDev();
        }
    }

    public JButton getButton() {
        return button;
    }

    public Map<Card, JLabel> getCardsLabel() {
        return cardsLabel;
    }

    private void loadButtonIcon() {
        String imageFile = BASE_PATH+buttonName;
        ImageIcon icon = new ImageIcon(imageFile);
        Image scaledImage = icon.getImage().getScaledInstance((int) (icon.getIconWidth() * 0.3), (int) (icon.getIconHeight() * 0.3), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        button.setIcon(scaledIcon);

        button.setBorder(BorderFactory.createEmptyBorder());

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
            return BASE_PATH +"proposeCard/"+ matchingFiles[0];
        } else {
            return "No matching files found";
        }
    }

    private void loadScaledIcon() {
        String imagFile = "";
        for (int i = 0; i < scaledIcon.length; i++) {
            imagFile = getImagePathByPrefix(i);
            ImageIcon icon = new ImageIcon(imagFile);
            Image scaledImage = icon.getImage().getScaledInstance((int) (icon.getIconWidth() * 0.6), (int) (icon.getIconHeight() * 0.6), Image.SCALE_SMOOTH);
            scaledIcon[i] = new ImageIcon(scaledImage);
        }
    }

    private void initializeAllCards() {
        for(int i = 0; i < 5 ;i++) {
            JLabel label = new JLabel(scaledIcon[i]);
            label.setName(Card.values()[i].name());
            cardsLabel.put(Card.values()[i],label);
        }
        positionCards();
    }

    public void initializeForDev() {
        for(int i = 0; i < 5; i++) {
            JLabel label = new JLabel(scaledIcon[i]);
            label.setName(Card.values()[i].name());
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(label.getBorder() == null) {
                        label.setBorder(BorderFactory.createLineBorder(Color.MAGENTA,3));
                    } else {
                        label.setBorder(null);
                    }
                    if (isForMono) {
                        if (getSelectedCards().getNbOfKind() == 1) {
                            button.setEnabled(true);
                        }else{
                            button.setEnabled(false);
                        }
                    }else{
                        if (getSelectedCards().getNbOfKind() == 2) {
                            button.setEnabled(true);
                        }else{
                            button.setEnabled(false);
                        }
                    }
                }
            });
            cardsLabel.put(Card.values()[i],label);
        }
        positionCards();
    }

    private void positionCards() {
        int offsetX = 5;
        int offsetY = 5;
        int gap = 20;

        for(JLabel label : cardsLabel.values()) {
            label.setBounds(offsetX, offsetY, label.getIcon().getIconWidth(), label.getIcon().getIconHeight());
            this.add(label);
            offsetX += label.getIcon().getIconWidth() + gap;
        }


        button.setBounds(400, 5, 30, 30);
        this.add(button);
        this.revalidate();
        this.repaint();
    }

    protected CardBox getSelectedCards() {
        CardBox selected = new CardBox();
        for (Map.Entry<Card, JLabel> entry : cardsLabel.entrySet()) {
            JLabel label = entry.getValue();
            if (label.getBorder() != null) {
                selected.addCard(Card.valueOf(label.getName()),1);
            }
        }
        return selected;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 80);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Overlapping Cards");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CardPropose cardPropose = new CardPropose(1,false);
        frame.add(cardPropose,BorderLayout.SOUTH);
        frame.setSize(1000, 700);
        frame.setVisible(true);
    }
}
