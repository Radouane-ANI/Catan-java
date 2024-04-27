package gui;


import logic.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ExchangePanel extends JPanel {
    private Player player;
    private Bank bank;
    protected CardSuit myCards;
    protected CardSuit saleList;
    protected CardSuit wishList;
    protected CardPropose proposeList;
    protected BankPanel bankPanel;
    protected ButtonsPanel buttonsPanel;

    public ExchangePanel(Player player, Bank bank) {
        this.player = player;
        this.bank = bank;
        this.myCards = new CardSuit(player,1);
        this.saleList = new CardSuit(player,2);
        this.wishList = new CardSuit(player,3);
        this.proposeList = new CardPropose();
        this.buttonsPanel = new ButtonsPanel(player,this);
        this.bankPanel = new BankPanel(bank);

        addMouseListenerToMyCardsLabel();
        addMouseListenerToSaleListLabel();
        addMouseListenerToWishListLabel();
        addMouseListenerToProposeListLabel();

        this.setLayout(new GridLayout(1, 2));
        JPanel cardsExchangePanel = new JPanel();
        cardsExchangePanel.setLayout(new BoxLayout(cardsExchangePanel,BoxLayout.Y_AXIS));
        cardsExchangePanel.add(proposeList);
        cardsExchangePanel.add(wishList);
        cardsExchangePanel.add(saleList);
        cardsExchangePanel.add(myCards);

        JPanel bankAndExchange = new JPanel(new BorderLayout());
        bankAndExchange.add(bankPanel,BorderLayout.NORTH);
        bankAndExchange.add(cardsExchangePanel,BorderLayout.SOUTH);

        JPanel buttonsBigPanel = new JPanel(new BorderLayout());
        buttonsBigPanel.add(buttonsPanel,BorderLayout.SOUTH);

        this.add(bankAndExchange);
        this.add(buttonsBigPanel);
        exchangeListVisible(false);
    }

    protected void exchangeListVisible(Boolean b) {
        this.proposeList.setVisible(b);
        this.wishList.setVisible(b);
        this.saleList.setVisible(b);
        this.saleList.getButton().setEnabled(b);
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
                            initializeWishList();
                            reNewCloseButton();
                            reNewBankButton();
                            reNew();
                        }
                    }
                });
            }
        }
    }

    protected void initializeMyCards() {
        myCards.initializeLabels();
        addMouseListenerToMyCardsLabel();
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
                        initializeSaleList();
                        initializeMyCards();
                        initializeWishList();
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
                initializeWishList();
                initializeMyCards();
                initializeSaleList();
                proposeList.setVisible(false);
                wishList.setVisible(false);
                saleList.setVisible(false);
                //proposeList.getButton().setEnabled(false);
            }
        });
    }

    private void initializeSaleList() {
        saleList.initializeLabels();
        addMouseListenerToSaleListLabel();
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

                initializeWishList();
                initializeMyCards();
                initializeSaleList();
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
                initializeWishList();
                initializeMyCards();
                initializeSaleList();
                button.setEnabled(false);
            }
        });
    }

    private void reNew() {
        this.revalidate();
        this.repaint();
    }

    public void start(int framerate) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try{
                        Thread.sleep(1000/framerate);
                    } catch(InterruptedException v) {
                        v.printStackTrace();
                    }
                    bankPanel.update();
                    buttonsPanel.updateButtons();
                }
            }
        };

        Thread thread = new Thread(runnable);

        thread.start();
    }

    public static void main(String[] args)  {
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

        ExchangePanel exchangePanel = new ExchangePanel(player,bank);

        frame.add(exchangePanel);

        frame.getContentPane().setBackground(Color.pink);

        frame.setSize(1000, 700);
        frame.setVisible(true);

        exchangePanel.start(1);
    }

}