package gui;

import logic.Card;
import logic.Player;

import javax.swing.*;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class DiscardPanel extends JPanel {
    private Player player;
    private CardSuit myCards;
    private CardSuit discardCard;
    private int defausse;

    public DiscardPanel(Player player) {
        this.player = player;
        this.defausse = player.getMyCards().getNumberOfRes() / 2;
        this.myCards = new CardSuit(player, 1);
        this.discardCard = new CardSuit(player, 2);
        discardCard.setImageButton("bank.png");
        initializeCard();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(discardCard);
        add(myCards);
    }

    private void initializeCard() {
        myCards.initializeLabels();
        discardCard.initializeLabels();
        addMouseListenerToMyCardsLabel();
        addMouseListenerToDiscardCardLabel();
        revalidate();
        repaint();
    }

    private void addMouseListenerToMyCardsLabel() {
        JLabel lastLabel;
        for (Card card : Card.values()) {
            List<JLabel> labelList = myCards.getCardBoxLabel().get(card);
            if (!labelList.isEmpty()) {
                lastLabel = labelList.get(labelList.size() - 1);
                for (MouseListener listener : lastLabel.getMouseListeners()) {
                    lastLabel.removeMouseListener(listener);
                }

                lastLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JLabel clickedLabel = (JLabel) e.getSource();
                        Card c = Card.valueOf(clickedLabel.getName());
                        if (c.ordinal() <= 4) {
                            discardCard.setVisible(true);
                            remove(clickedLabel);
                            player.addInSaleList(c);
                            List<JLabel> labelList = myCards.getCardBoxLabel().get(c);
                            labelList.remove(clickedLabel);
                            initializeCard();
                            reNewBankButton();
                        }
                    }
                });
            }
        }
    }

    private void addMouseListenerToDiscardCardLabel() {
        JLabel lastLabel;

        for (Card card : Card.values()) {
            List<JLabel> labelList = discardCard.getCardBoxLabel().get(card);
            if (!labelList.isEmpty()) {
                lastLabel = labelList.get(labelList.size() - 1);

                for (MouseListener listener : lastLabel.getMouseListeners()) {
                    lastLabel.removeMouseListener(listener);
                }

                lastLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JLabel clickedLabel = (JLabel) e.getSource();
                        Card c = Card.valueOf(clickedLabel.getName());
                        remove(clickedLabel);
                        player.rmFromSaleList(c);
                        List<JLabel> labelList = discardCard.getCardBoxLabel().get(c);
                        labelList.remove(clickedLabel);
                        initializeCard();
                        reNewBankButton();
                    }
                });
            }
        }
    }

    private void reNewBankButton() {
        JButton button = discardCard.getButton();
        button.setEnabled(player.getSaleList().getNumberOfRes() == defausse);
        for (ActionListener listener : button.getActionListeners()) {
            button.removeActionListener(listener);
        }
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.getWishList().clearBox();
                player.trade(player.getSaleList(), player.getBank(), player.getWishList(), player.getMyCards());
                button.setEnabled(false);
                player.setFinishedTurn(true);
                Container parent = DiscardPanel.this.getParent();
                parent.remove(DiscardPanel.this);
                parent.revalidate();
            }
        });
    }

}
