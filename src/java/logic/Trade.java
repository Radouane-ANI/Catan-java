package src.java.logic;

import static src.java.logic.Card.*;

public interface Trade {
    default boolean isTradableInBank(CardBox saleList,CardBox wishList, TradePort tradePorts) {
        boolean flag = false;
        int otherCardsNumber = 0;
        if(saleList.isEmpty()||wishList.isEmpty()) {
            return flag;
        }
        while (otherCardsNumber == 0 && wishList.getNumberOfRes() == 1) {
            for (Card c : Card.values()) {
                switch (saleList.getNumber(c)) {
                    case 2:
                        if (tradePorts.hasPort(c)) {
                            return true;
                        }
                    case 3:
                        if (tradePorts.hasPort(null)) {
                            return true;
                        }
                    case 4:
                        return true;
                    default:
                        otherCardsNumber += saleList.getNumber(c);
                }
            }
        }
        return flag;
    }

    default boolean isTradeInteresting(CardBox saleList, CardBox wishList, CardBox wishList1, CardBox saleList1) {
        for (Card c : Card.values()) {
            if (wishList.getNumber(c) != 0) {
                if (saleList1.getNumber(c) == 0) {
                    return false;
                }
            }
            if (saleList.getNumber(c) != 0) {
                if (wishList1.getNumber(c) == 0) {
                    return false;
                }
            }
        }

        return true;
    }

    default void trade(CardBox saleList, CardBox tradeObj, CardBox wishList, CardBox myCards) {
        for (Card c : Card.values()) {
            if(saleList.getNumber(c) != 0) {
                myCards.removeCard(c,saleList.getNumber(c));
            }
        }
        for (Card c : Card.values()) {
            if(wishList.getNumber(c) != 0) {
                myCards.addCard(c,wishList.getNumber(c));
            }
        }
        wishList.clearBox();
        saleList.clearBox();
    }

    default void getDevCard(CardBox myCards, Bank bank) {
        if (canExchangeDev(myCards, bank)) {
            Card dev = bank.devCardGenerator();
            bank.removeCard(dev, 1);
            myCards.addCard(dev, 1);
        }
    }

    default boolean canExchangeDev(CardBox myCards, Bank bank) {
        if (bank.allDevCardsNumber() > 0) {
            if (myCards.getNumberOfRes() >= 3) {
                return myCards.getNumber(SHEEP) >= 1 && myCards.getNumber(GRAIN) >= 1
                        && myCards.getNumber(STONE) >= 1;
            }
            return false;
        }
        return false;
    }
}
