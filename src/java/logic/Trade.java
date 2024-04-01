package logic;

import static logic.Card.*;

public interface Trade {
    default boolean isTradableInBank(CardBox saleList,CardBox wishList, TradePort tradePorts) {
        if(saleList.isEmpty()||wishList.isEmpty()) {
            return false;
        }
        if(wishList.getNumberOfRes()!=1) {
            return false;
        }
        for (Card c : Card.values()) {
            switch (saleList.getNumber(c)) {
                case 2:
                    if (tradePorts.hasPort(c) && saleList.getNumberOfRes()==2) {
                        return true;
                    }break;
                case 3:
                    if (tradePorts.hasPort(null) && saleList.getNumberOfRes()==3) {
                        return true;
                    }break;
                case 4:
                    return true;
            }
        }
        return false;
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
                tradeObj.addCard(c,saleList.getNumber(c));
            }
        }
        for (Card c : Card.values()) {
            if(wishList.getNumber(c) != 0) {
                tradeObj.removeCard(c, wishList.getNumber(c));
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

    default boolean canTradeWith(CardBox myCards, CardBox saleList) {
        for (Card c : Card.values()) {
            if (saleList.getNumber(c) != 0) {
                if (saleList.getNumber(c) > myCards.getNumber(c)) {
                    return false;
                }
            }
        }
        return true;
    }
}
