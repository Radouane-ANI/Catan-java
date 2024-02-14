package src.java.logic;

public class CardBox {
    protected int[] cardsNumbers = new int[Card.values().length];

    public CardBox() {}

    public void addCard(Card c, int number) {
        cardsNumbers[c.ordinal()] += number;
    }

    public int getNumber(Card c) {
        return cardsNumbers[c.ordinal()];
    }

    public int getNumberOfRes() {
        int numb = 0;
        for (int i = 0; i < 5; i++) {
            numb += cardsNumbers[i];
        }
        return numb;
    }

    public void clearBox() {
        for(Card card : Card.values()) {
            setZero(card);
        }
    }

    public void setZero(Card c) {
        cardsNumbers[c.ordinal()] = 0;
    }

    public boolean removeCard(Card c, int number) {
        int numberOrigin = cardsNumbers[c.ordinal()];
        int numberNew = numberOrigin - number;
        if(numberNew >= 0) {
            cardsNumbers[c.ordinal()] = numberNew;
            return true;
        }
        return false;
    }

    public boolean hasCard(Card c) {
        return cardsNumbers[c.ordinal()] > 0;
    }
}
