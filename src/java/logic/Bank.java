package logic;

import java.util.Random;

public class Bank extends CardBox {
    private static final int NB_RES_CARD = 19;
    private static final int NB_KNIGHT_CARD = 14;
    private static final int NB_ROAD_BUILD_CARD = 2;
    private static final int NB_YEAR_PLENTY_CARD = 2;
    private static final int NB_MONOPOLY_CARD = 2;
    private static final int NB_ONE_POINT_CARD = 5;

    public Bank() {
        super();
        initialBank();
    }

    public void initialBank() {
        for (int i = 0; i < 5; i++) {
            cardsNumbers[i] = NB_RES_CARD;
        }
        cardsNumbers[5] = NB_KNIGHT_CARD;
        cardsNumbers[5] = NB_ROAD_BUILD_CARD;
        cardsNumbers[5]= NB_YEAR_PLENTY_CARD;
        cardsNumbers[5] = NB_MONOPOLY_CARD;
        cardsNumbers[5] = NB_ONE_POINT_CARD;
    }

    public Card devCardGenerator() {
        if(hasDevCards()) {
            Random r = new Random();
            int probability = r.nextInt(allDevCardsNumber()+1) + 1;
            int accu = 0;
            int index = 4;
            while (accu < probability) {
                index++;
                accu = cardsNumbers[index];
            }
            return Card.values()[index];
        }
        return null;
    }

    public boolean hasDevCards() {
        return allDevCardsNumber() > 0;
    }

    public int allDevCardsNumber() {
        int allDevCardsNumber = 0;
        for (int i = 5; i < 10; i++) {
            allDevCardsNumber += cardsNumbers[i];
        }
        return allDevCardsNumber;
    }

}

