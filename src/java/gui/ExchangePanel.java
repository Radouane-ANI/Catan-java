package gui;

import logic.*;

import javax.swing.*;

import controleur.Game;
import controleur.ViewControleur;

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

    public void update(Player player) {
        this.player = player;
        if (player.isBot() || player.getMyCards().getNumberOfRes()==0) {
            setVisible(false);
            return;
        }setVisible(true);
        myCards.setCardBox(player);
        wishList.setCardBox(player);
        saleList.setCardBox(player);
        iniinitializeCard();
    }

    private void iniinitializeCard(){
        myCards.initializeLabels();
        addMouseListenerToMyCardsLabel();
        saleList.initializeLabels();
        addMouseListenerToSaleListLabel();
        initializeWishList();
    }

    private void addMouseListenerToMyCardsLabel() {
        JLabel lastLabel;
        for(Card card : Card.values()) {
            List<JLabel> labelList = myCards.getCardBoxLabel().get(card);
            if(!labelList.isEmpty()) {
                lastLabel = labelList.get(labelList.size()-1);
                for (MouseListener listener : lastLabel.getMouseListeners()) {
                    lastLabel.removeMouseListener(listener);
                }

                lastLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JLabel clickedLabel = (JLabel) e.getSource();
                        Card c = Card.valueOf(clickedLabel.getName());
                        if(c.ordinal() <= 4) {
                            proposeList.setVisible(true);
                            wishList.setVisible(true);
                            saleList.setVisible(true);
                            ExchangePanel.this.remove(clickedLabel);
                            player.addInSaleList(c);
                            List<JLabel> labelList = myCards.getCardBoxLabel().get(c);
                            labelList.remove(clickedLabel);
                            iniinitializeCard();
                            reNewCloseButton();
                            reNewBankButton();
                            reNew();    
                        }
                    }
                });
            }
        }
    }

    private void addMouseListenerToSaleListLabel() {
        JLabel lastLabel;

        for(Card card : Card.values()) {
            List<JLabel> labelList = saleList.getCardBoxLabel().get(card);
            if(!labelList.isEmpty()) {
                lastLabel = labelList.get(labelList.size()-1);

                for (MouseListener listener : lastLabel.getMouseListeners()) {
                    lastLabel.removeMouseListener(listener);
                }

                lastLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        System.out.println("saleList");
                        JLabel clickedLabel = (JLabel) e.getSource();
                        Card c = Card.valueOf(clickedLabel.getName());
                        ExchangePanel.this.remove(clickedLabel);
                        player.rmFromSaleList(c);
                        List<JLabel> labelList = saleList.getCardBoxLabel().get(c);
                        labelList.remove(clickedLabel);
                        iniinitializeCard();
                        reNewExchangePPlButton();
                        reNewCloseButton();
                        reNewBankButton();
                        reNew();
                    }
                });
            }
        }
    }

    private void reNewCloseButton() {
        JButton button = saleList.getButton();
        button.setEnabled(true);
        for (ActionListener listener : button.getActionListeners()) {
            button.removeActionListener(listener);
        }
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.revertFromSaleList();
                player.getWishList().clearBox();
                iniinitializeCard();                
                proposeList.setVisible(false);
                wishList.setVisible(false);
                saleList.setVisible(false);
                //proposeList.getButton().setEnabled(false);
            }
        });
    }

    private void addMouseListenerToWishListLabel() {
        JLabel lastLabel;

        for(Card card : Card.values()) {
            List<JLabel> labelList = wishList.getCardBoxLabel().get(card);
            if(!labelList.isEmpty()) {
                lastLabel = labelList.get(labelList.size()-1);

                for (MouseListener listener : lastLabel.getMouseListeners()) {
                    lastLabel.removeMouseListener(listener);
                }

                lastLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JLabel clickedLabel = (JLabel) e.getSource();
                        Card c = Card.valueOf(clickedLabel.getName());
                        ExchangePanel.this.remove(clickedLabel);
                        player.rmFromWishList(c);
                        List<JLabel> labelList = wishList.getCardBoxLabel().get(c);
                        labelList.remove(clickedLabel);
                        initializeWishList();
                        reNewExchangePPlButton();
                        reNewBankButton();
                        reNewExchangePPlButton();
                        reNew();
                    }
                });
            }
        }
    }

    private void reNewExchangePPlButton() {
        JButton button = wishList.getButton();
        if(!player.getWishList().isEmpty() && !player.getSaleList().isEmpty()) {
            button.setEnabled(true);
        }else{
            button.setEnabled(false);
        }
        for (ActionListener listener : button.getActionListeners()) {
            button.removeActionListener(listener);
        }
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewControleur.getGame().initerEchange();
                iniinitializeCard();                
                addMouseListenerToProposeListLabel();
                proposeList.setVisible(false);
                wishList.setVisible(false);
                saleList.setVisible(false);
            }
        });
    }

    private void initializeWishList() {
        wishList.initializeLabels();
        addMouseListenerToWishListLabel();
    }

    private void addMouseListenerToProposeListLabel() {
        for(JLabel jLabel : proposeList.getCardsLabel().values()) {
            for (MouseListener listener : jLabel.getMouseListeners()) {
                jLabel.removeMouseListener(listener);
            }
            jLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JLabel clickedLabel = (JLabel) e.getSource();
                    Card c = Card.valueOf(clickedLabel.getName());
                    player.addInWishList(c);
                    initializeWishList();
                    reNewBankButton();
                    reNewExchangePPlButton();
                    reNew();
                }
            });
        }
    }

    private void reNewBankButton() {
        JButton button = proposeList.getButton();
        if(player.isTradableInBank(player.getSaleList(), player.getWishList(), player.getTradePorts())) {
            button.setEnabled(true);
        }else{
            button.setEnabled(false);
        }
        for (ActionListener listener : button.getActionListeners()) {
            button.removeActionListener(listener);
        }
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.trade(player.getSaleList(),player.getBank(),player.getWishList(),player.getMyCards());
                iniinitializeCard();
                button.setEnabled(false);
            }
        });
    }

    private void reNew() {
        this.revalidate();
        this.repaint();
    }

    /*public static void main(String[] args)  {
        Bank bank = new Bank();

        Player player = new Player(false, "Sam", bank, Color.red);
        CardBox cardBox = new CardBox();
        TradePort tradePort = new TradePort();

        player.setTradePorts(tradePort);
        player.getTradePorts().addPort(Card.SHEEP);
        int[] x = {1, 2, 3, 4, 5};
        cardBox.setCardsNumbers(x);
        player.setMyCards(cardBox);

        JFrame frame = new JFrame("Overlapping Cards");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ExchangePanel exchangePanel = new ExchangePanel(player);
        ButtonsPanel buttonsPanel = new ButtonsPanel(player);

        frame.setLayout(new GridLayout(1, 2));


        JPanel jPanel1 = new JPanel(new BorderLayout());
        jPanel1.add(exchangePanel, BorderLayout.SOUTH);
        BankPanel bankPanel = new BankPanel(bank);
        jPanel1.add(bankPanel, BorderLayout.NORTH);
        frame.add(jPanel1);

        JPanel jPanel2 = new JPanel(new BorderLayout());
        jPanel2.add(buttonsPanel, BorderLayout.SOUTH);
        frame.add(jPanel2);

        frame.getContentPane().setBackground(Color.pink);

        frame.setSize(1000, 700);
        frame.setVisible(true);

        bankPanel.start(1);
    }*/

}