package gui;

import logic.Card;
import logic.CardBox;
import logic.Player;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardSuit extends JLayeredPane {
    private static final int MY_CARD_LIST = 1;
    private static final int SALE_LIST = 2;
    private static final int WISH_LIST2 = 4;
    private static final int WISH_LIST = 3;
    private static final String BASE_PATH = "src/ressources/";

    private int cardSuitType;
    private CardBox cardBox;
    private ImageIcon scaledIcon[] = new ImageIcon[10];
    private Map<Card, List<JLabel>> cardBoxLabel;
    private JButton button;

    public CardSuit(Player player, int cardSuitType) {
        this.cardSuitType = cardSuitType;
        button = new JButton();
        switch (cardSuitType) {
            case MY_CARD_LIST : cardBox = player.getMyCards();
                                button = null;break;
            case SALE_LIST : cardBox = player.getSaleList();break;
            case WISH_LIST2 : cardBox = player.getWishList();break;
            case WISH_LIST : cardBox = player.getWishList();break;
        }
        this.cardBoxLabel = new HashMap<>();
        if(button != null) {
            loadButtonIcon();
            button.setEnabled(false);
        }
        loadScaledIcon();
        initializeLabels();
    }

    public void setCardBox(Player player) {
        switch (cardSuitType) { 
            case MY_CARD_LIST : cardBox = player.getMyCards();break;
            case SALE_LIST : cardBox = player.getSaleList();break;
            case WISH_LIST : cardBox = player.getWishList();break;
        }
    }

    public JButton getButton() {
        return button;
    }

    public Map<Card, List<JLabel>> getCardBoxLabel() {
        return cardBoxLabel;
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



    private void loadScaledIcon() {
        String imagFile = "";
        for (int i = 0; i < scaledIcon.length; i++) {
            imagFile = getImagePathByPrefix(i);
            ImageIcon icon = new ImageIcon(imagFile);
            Image scaledImage = icon.getImage().getScaledInstance((int) (icon.getIconWidth() * 0.6), (int) (icon.getIconHeight() * 0.6), Image.SCALE_SMOOTH);
            scaledIcon[i] = new ImageIcon(scaledImage);
        }
    }

    private void loadButtonIcon() {
        String imageFile = BASE_PATH;
        switch (cardSuitType) {
            case SALE_LIST -> imageFile +="no.png";
            case WISH_LIST2 -> imageFile +="yes.png";
            case WISH_LIST -> imageFile +="ppl.png";
        }
        ImageIcon icon = new ImageIcon(imageFile);
        Image scaledImage = icon.getImage().getScaledInstance((int) (icon.getIconWidth() * 0.3), (int) (icon.getIconHeight() * 0.3), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        button.setIcon(scaledIcon);
        button.setBorder(BorderFactory.createEmptyBorder());
    }

    public void setImageButton(String nameImage) {
        ImageIcon icon = new ImageIcon(BASE_PATH + nameImage);
        Image scaledImage = icon.getImage().getScaledInstance((int) (icon.getIconWidth() * 0.3),
                (int) (icon.getIconHeight() * 0.3), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        button.setIcon(scaledIcon);
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
                JLabel label = new JLabel(scaledIcon[card.ordinal()]);
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
                    if(i == labels.size() - 1) {
                        JPanel transparentPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                        ImageIcon iconO = new ImageIcon("/Users/juliazhula/k-catan/src/ressources/0_tree.png");
                        Dimension dimension = new Dimension((int)(iconO.getIconWidth() * 0.6), (int)(iconO.getIconHeight() * 0.6*0.3));
                        transparentPanel.setPreferredSize(dimension);

                        transparentPanel.setOpaque(false);

                        JLabel numberLabel = new JLabel(String.valueOf(labels.size()));
                        numberLabel.setFont(new Font("Arial", Font.BOLD, 13));
                        numberLabel.setForeground(Color.WHITE);

                        transparentPanel.add(numberLabel);

                        label.setLayout(new BorderLayout());
                        label.add(transparentPanel, BorderLayout.NORTH);

                    }
                    this.add(label,Integer.valueOf(i));
                }
            }
            if (!labels.isEmpty()) {
                offsetX += gap * labels.size() + labels.get(0).getIcon().getIconWidth();
            }
        }
        if(cardSuitType != MY_CARD_LIST) {
            button.setBounds(400, 5, 30, 30);
            this.add(button);
        }
        this.revalidate();
        this.repaint();
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 80);
    }

/*     public static void main(String[] args) {
        Player player = new Player(false,"Sam",new Bank(),Color.red);
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
*/

}
