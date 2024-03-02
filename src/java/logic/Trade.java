package logic;

import static logic.Card.*;

public interface Trade {
    
    default boolean isTradableInBank(CardBox saleList, TradePort tradePorts) {
        boolean flag = false;
        int otherCardsNumber = 0;
        while (otherCardsNumber == 0) {
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

    default void TradBank(CardBox saleList, Card wish, CardBox myCards, TradePort tradePorts, Bank bank) {
        CardBox wishList = new CardBox();
        wishList.addCard(wish, 1);
        for (Card c : Card.values()) {
            switch (saleList.getNumber(c)) {
                case 2:
                    if (tradePorts.hasPort(c) && bank.hasCard(wish)) {
                        trade(saleList, bank, wishList, myCards);
                    }
                    break;
                case 3:
                    if (tradePorts.hasPort(null) && bank.hasCard(wish)) {
                        trade(saleList, bank, wishList, myCards);
                    }
                    break;
                case 4:
                    if (bank.hasCard(wish)) {
                        trade(saleList, bank, wishList, myCards);
                    }
                    break;
            }
        }
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
            if (wishList.getNumber(c) != 0) {
                tradeObj.removeCard(c, wishList.getNumber(c));
                myCards.addCard(c, wishList.getNumber(c));
            }
            if (saleList.getNumber(c) != 0) {
                myCards.removeCard(c, saleList.getNumber(c));
                tradeObj.addCard(c, saleList.getNumber(c));
            }
        }
    }

    default void getDevCard(CardBox myCards, CardBox saleList, Bank bank) {
        if (canGetDevCard(saleList, bank)) {
            Card dev = bank.devCardGenerator();
            bank.removeCard(dev, 1);
            myCards.addCard(dev, 1);
            saleList.clearBox();
        }
    }

    default boolean canGetDevCard(CardBox saleList, Bank bank) {
        if (bank.allDevCardsNumber() > 0) {
            if (saleList.getNumberOfRes() == 3) {
                return saleList.getNumber(SHEEP) == 1 && saleList.getNumber(GRAIN) == 1
                        && saleList.getNumber(STONE) == 1;
            }
            return false;
        }
        return false;
    }
}
