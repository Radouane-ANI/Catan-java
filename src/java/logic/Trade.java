package src.java.logic;

import src.java.logic.Bank;
import src.java.logic.Card;
import src.java.logic.CardBox;
import src.java.logic.TradePort;

import static src.java.logic.Card.*;

public interface Trade {
    default boolean isTradableInBank(CardBox saleList, TradePort tradePorts) {
        boolean flag = false;
        int otherCardsNumber = 0;
        while (otherCardsNumber == 0) {
            for(Card c : Card.values()) {
                switch (saleList.getNumber(c)) {
                    case 2 : if(tradePorts.hasPort(c)) {
                        return true;
                    }
                    case 3 : if(tradePorts.hasPort(null)) {
                        return true;
                    }
                    case 4 : return true;
                    default: otherCardsNumber += saleList.getNumber(c);
                }
            }
        }
        return flag;
    }

    default void trade(CardBox saleList, CardBox tradeObj, CardBox wishList, CardBox myCards) {
        for(Card c : Card.values()) {
            if(wishList.getNumber(c)!=0) {
                tradeObj.removeCard(c, wishList.getNumber(c));
                myCards.addCard(c, wishList.getNumber(c));
            }
        }
        wishList.clearBox();
        saleList.clearBox();
    }

    default void getDevCard(CardBox myCards, CardBox saleList, Bank bank) {
        if(canGetDevCard(saleList,bank)) {
            Card dev = bank.devCardGenerator();
            bank.removeCard(dev,1);
            myCards.addCard(dev,1);
            saleList.clearBox();
        }
    }

    default boolean canGetDevCard(CardBox saleList, Bank bank) {
        if(bank.allDevCardsNumber() > 0 ) {
            if(saleList.getNumberOfRes()==3) {
                return saleList.getNumber(SHEEP)==1&&saleList.getNumber(GRAIN)==1&&saleList.getNumber(STONE)==1;
            }
            return false;
        }
        return false;
    }
}

