package src.java.gui;


import src.java.logic.Bank;
import src.java.logic.Card;
import src.java.logic.CardBox;
import src.java.logic.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ExchangePanel extends JPanel {
    private Player player;
    private CardSuit myCards;
    private CardSuit saleList;
    private CardSuit wishList;
    private CardPropose proposeList;
    private ImageIcon scaledIcon;



    public ExchangePanel(Player player) {
        this.player = player;

        this.myCards = new CardSuit(player,1);
        this.saleList = new CardSuit(player,2);
        this.wishList = new CardSuit(player,3);
        this.proposeList = new CardPropose();

        addMouseListenerToMyCardsLabel();
        addMouseListenerToSaleListLabel();
        addMouseListenerToWishListLabel();
        addMouseListenerToProposeListLabel();

        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.add(proposeList);
        proposeList.setVisible(false);
        this.add(wishList);
        wishList.setVisible(false);
        this.add(saleList);
        saleList.setVisible(false);
        this.add(myCards);
    }


    private void addMouseListenerToMyCardsLabel() {
        JLabel firstLabel;
        for(Card card : Card.values()) {
            List<JLabel> labelList = myCards.getCardBoxLabel().get(card);
            if(!labelList.isEmpty()) {
                firstLabel = labelList.get(0);
                for (MouseListener listener : firstLabel.getMouseListeners()) {
                    firstLabel.removeMouseListener(listener);
                }

                firstLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JLabel clickedLabel = (JLabel) e.getSource();
                        Card c = Card.valueOf(clickedLabel.getName());
                        if(c.ordinal() > 4) {

                        }else{
                            proposeList.setVisible(true);
                            wishList.setVisible(true);
                            saleList.setVisible(true);
                            ExchangePanel.this.remove(clickedLabel);
                            player.addInSaleList(c);
                            List<JLabel> labelList = myCards.getCardBoxLabel().get(c);
                            labelList.remove(clickedLabel);
                            initializeMyCards();
                            initializeSaleList();
                            reNew();
                        }
                    }
                });
            }
        }
    }

    private void initializeMyCards() {
        myCards.initializeLabels();
        addMouseListenerToMyCardsLabel();
    }

    private void addMouseListenerToSaleListLabel() {
        JLabel firstLabel;
        JButton button = saleList.getButton();
        for (ActionListener listener : button.getActionListeners()) {
            button.removeActionListener(listener);
        }
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Click");
                player.revertFromSaleList();
                System.out.println("Click");
                initializeMyCards();
                initializeSaleList();
                proposeList.setVisible(false);
                wishList.setVisible(false);
                saleList.setVisible(false);
                System.out.println(button.isEnabled());
                //reNew();
            }
        });

        for(Card card : Card.values()) {
            List<JLabel> labelList = saleList.getCardBoxLabel().get(card);
            if(!labelList.isEmpty()) {
                firstLabel = labelList.get(0);

                for (MouseListener listener : firstLabel.getMouseListeners()) {
                    firstLabel.removeMouseListener(listener);
                }

                firstLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        System.out.println("saleList");
                        JLabel clickedLabel = (JLabel) e.getSource();
                        Card c = Card.valueOf(clickedLabel.getName());
                        ExchangePanel.this.remove(clickedLabel);
                        player.rmFromSaleList(c);
                        List<JLabel> labelList = saleList.getCardBoxLabel().get(c);
                        labelList.remove(clickedLabel);
                        initializeSaleList();
                        initializeMyCards();
                        reNew();
                    }
                });
            }
        }
    }

    private void initializeSaleList() {
        saleList.initializeLabels();
        addMouseListenerToSaleListLabel();
    }

    private void addMouseListenerToWishListLabel() {
        JLabel firstLabel;
        for(Card card : Card.values()) {
            List<JLabel> labelList = wishList.getCardBoxLabel().get(card);
            if(!labelList.isEmpty()) {
                firstLabel = labelList.get(0);

                for (MouseListener listener : firstLabel.getMouseListeners()) {
                    firstLabel.removeMouseListener(listener);
                }

                firstLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JLabel clickedLabel = (JLabel) e.getSource();
                        Card c = Card.valueOf(clickedLabel.getName());
                        ExchangePanel.this.remove(clickedLabel);
                        player.rmFromWishList(c);
                        List<JLabel> labelList = wishList.getCardBoxLabel().get(c);
                        labelList.remove(clickedLabel);
                        initializeWishList();
                        reNew();
                    }
                });
            }
        }
    }

    private void initializeWishList() {
        wishList.initializeLabels();
        addMouseListenerToWishListLabel();
    }

    private void addMouseListenerToProposeListLabel() {
        for(JLabel jLabel : proposeList.getCardsLabel().values()) {
            jLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JLabel clickedLabel = (JLabel) e.getSource();
                    Card c = Card.valueOf(clickedLabel.getName());
                    player.addInWishList(c);
                    initializeWishList();
                    reNew();
                }
            });
        }
    }

    private void reNew() {
        this.revalidate();
        this.repaint();
    }

    public static void main(String[] args) {
        Player player = new Player(false,"Sam",new Bank());
        CardBox cardBox = new CardBox();
        int[] x = {1,2,3,4,5};
        cardBox.setCardsNumbers(x);
        player.setMyCards(cardBox);

        JFrame frame = new JFrame("Overlapping Cards");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ExchangePanel exchangePanel = new ExchangePanel(player);
        frame.add(exchangePanel,BorderLayout.SOUTH);
        frame.setSize(1000, 700);
        frame.setVisible(true);
    }


}