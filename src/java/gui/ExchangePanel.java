package gui;

import logic.*;

import javax.swing.*;

import static logic.Card.ROAD_BUILD;

import java.awt.*;
import controleur.ViewControleur;
import java.awt.event.*;
import java.util.List;

public class ExchangePanel extends JPanel {
    private Player player;
    private Bank bank;
    protected CardSuit myCards;
    protected CardSuit saleList;
    protected CardSuit wishList;
    protected CardPropose proposeList;
    protected CardPropose proposeForMono;
    protected CardPropose proposeForPlenty;
    private JPanel overLapPanel1, overLapPanel2;

    public ExchangePanel(Player player) {
        this.player = player;
        this.bank = player.getBank();
        this.myCards = new CardSuit(player,1);
        this.saleList = new CardSuit(player,2);
        this.wishList = new CardSuit(player,3);
        this.proposeList = new CardPropose(1,false);
        this.proposeForMono = new CardPropose(2,true);
        this.proposeForPlenty = new CardPropose(2,false);

        addMouseListenerToMyCardsLabel();
        addMouseListenerToSaleListLabel();
        addMouseListenerToWishListLabel();
        addMouseListenerToProposeListLabel();

        this.setLayout(new GridLayout(1, 2));
        JPanel cardsExchangePanel = new JPanel();
        cardsExchangePanel.setLayout(new BoxLayout(cardsExchangePanel,BoxLayout.Y_AXIS));
        overLapPanel1 = new JPanel();
        overLapPanel1.setLayout(new OverlayLayout(overLapPanel1));
        overLapPanel1.add(proposeList);
        overLapPanel1.add(proposeForMono);
        overLapPanel1.add(proposeForPlenty);
        cardsExchangePanel.add(overLapPanel1);
        overLapPanel2 = new JPanel();
        overLapPanel2.setLayout(new OverlayLayout(overLapPanel2));
        overLapPanel2.add(wishList);
        cardsExchangePanel.add(overLapPanel2);
        cardsExchangePanel.add(saleList);
        cardsExchangePanel.add(myCards);

        this.add(cardsExchangePanel);
        proposeForMono.setVisible(false);
        proposeForPlenty.setVisible(false);

        exchangeListVisible(false);
    }

    protected void exchangeListVisible(Boolean b) {
        this.proposeList.setVisible(b);
        this.wishList.setVisible(b);
        this.saleList.setVisible(b);
        this.saleList.getButton().setEnabled(b);
    }

    public void update(Player player) {
        this.player = player;
        if (player.isBot()
                || (player.getMyCards().getNumberOfRes() == 0 && player.getWishList().getNumberOfRes() == 0)) {
            setVisible(false);
            return;
        }setVisible(true);        
        overLapPanel1.setVisible(false);
        overLapPanel2.setVisible(false);
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
                        overLapPanel1.setVisible(true);
                        overLapPanel2.setVisible(true);                
                        JLabel clickedLabel = (JLabel) e.getSource();
                        Card c = Card.valueOf(clickedLabel.getName());
                        if(c.ordinal() > 4) {
                            addListenerToDevCard(c);
                            System.out.println(c.name()+"haha");
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


    private void addListenerToDevCard(Card card) {
        switch (card) {
            case KNIGHT: addListenerToKnight();break;
            case MONOPOLY: addListenerToMono();break;
            case YEAR_PLENTY: addListenerToPlenty();break;
            case ONE_POINT: addListenerToOnePointPlus();break;
            case ROAD_BUILD:addListenerToRoadBuild(); break;
        }
    }

    public void addListenerToMono() {
        if (proposeForMono.isVisible()) {
            proposeForMono.initializeForDev();
            proposeForMono.setVisible(false);
        } else {
            proposeForMono.setVisible(true);
            addListerToProposeListForDev(true);
        }
    }

    public void addListenerToPlenty() {
        if (proposeForPlenty.isVisible()) {
            proposeForPlenty.initializeForDev();
            proposeForPlenty.setVisible(false);
        } else {
            proposeForPlenty.setVisible(true);
            addListerToProposeListForDev(false);
        }
    }

    public void addListenerToRoadBuild() {
        ViewControleur.getCatanControleur().buildRoad(player, true, 2);
        player.getMyCards().removeCard(ROAD_BUILD,1);
        initializeMyCards();
        reNew();
    }

    public void addListenerToOnePointPlus() {
        player.onePointPLus();
        player.getMyCards().removeCard(Card.ONE_POINT,1);
        initializeMyCards();
        reNew();
    }

    public void addListenerToKnight() {
        ViewControleur.getCatanControleur().moveThief(player);
        player.getMyCards().removeCard(Card.KNIGHT,1);
        initializeMyCards();
        reNew();
    }




    private void initializeExchangePanel() {
        initializeMyCards();
        initializeSaleList();
        initializeWishList();
    }

    private void renewButtons() {
        reNewExchangePPlButton();
        reNewCloseButton();
        reNewBankButton();
    }

    private void renewPanel() {
        reNew();
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
                        JLabel clickedLabel = (JLabel) e.getSource();
                        Card c = Card.valueOf(clickedLabel.getName());
                        ExchangePanel.this.remove(clickedLabel);
                        player.rmFromSaleList(c);
                        List<JLabel> labelList = saleList.getCardBoxLabel().get(c);
                        labelList.remove(clickedLabel);
                        initializeExchangePanel();
                        renewButtons();
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
                overLapPanel1.setVisible(false);
                overLapPanel2.setVisible(false);        
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
                        renewButtons();
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
                ViewControleur.getGame().initierEchange();
                overLapPanel1.setVisible(false);
                overLapPanel2.setVisible(false);        
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

    private void addListerToProposeListForDev(boolean isMono) {
        JButton button;

        if (isMono) {
            button = proposeForMono.getButton();
        } else {
            button = proposeForPlenty.getButton();
        }
        for (ActionListener listener : button.getActionListeners()) {
            button.removeActionListener(listener);
        }
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardBox selected;
                if (isMono) {
                    selected = proposeForMono.getSelectedCards();
                    System.out.println("get on card from everyone");
                    for(Card card : Card.values()) {
                        if (selected.getNumber(card) > 0) {
                            int amount = ViewControleur.getGame().mono_getCardFromEveryone(card);
                            player.getMyCards().addCard(card,amount);
                        }
                    }
                } else{
                    selected = proposeForPlenty.getSelectedCards();
                    for(Card card : Card.values()) {
                        if (selected.getNumber(card) > 0) {
                            player.getMyCards().addCard(card,1);
                            bank.removeCard(card,1);
                        }
                    }
                }
                if (isMono) {
                    player.getMyCards().removeCard(Card.MONOPOLY,1);
                    proposeForMono.initializeForDev();
                    proposeForMono.setVisible(false);
                }else {
                    player.getMyCards().removeCard(Card.YEAR_PLENTY,1);
                    proposeForPlenty.initializeForDev();
                    proposeForPlenty.setVisible(false);
                }
                initializeMyCards();
                initializeSaleList();
                initializeWishList();
                reNewCloseButton();
                reNewBankButton();
                reNew();
            }
        });
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
                    renewButtons();
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
                overLapPanel1.setVisible(false);
                overLapPanel2.setVisible(false);        
                player.trade(player.getSaleList(),player.getBank(),player.getWishList(),player.getMyCards());
                initializeWishList();
                initializeMyCards();
                initializeSaleList();
                button.setEnabled(false);
                renewButtons();
            }
        });
    }

    private void reNew() {
        this.revalidate();
        this.repaint();
    }

    /*public void start(int framerate) {

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

    public void update() {
        bankPanel.update();
        buttonsPanel.updateButtons();
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

        ExchangePanel exchangePanel = new ExchangePanel(player,bank);

        frame.add(exchangePanel);

        frame.getContentPane().setBackground(Color.pink);

        frame.setSize(1000, 700);
        frame.setVisible(true);

        exchangePanel.start(1);
    }
*/
}