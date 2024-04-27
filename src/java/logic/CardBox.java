package logic;

import java.util.Random;

public class CardBox {
    protected int[] cardsNumbers = new int[Card.values().length];

    public CardBox() {}

    //setCardsNumbers -> for test
    public void setCardsNumbers(int[] x) {
        for(int i = 0; i < x.length; i++) {
            cardsNumbers[i] = x[i];
        }
    }

    public void addCard(Card c, int number) {
        if (c != null) {
            cardsNumbers[c.ordinal()] += number;            
        }
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

    public Card getRandomCard() {
        int taille = getNumberOfRes();
        if (taille == 0) {
            return null;
        }
        Random random = new Random();

        int randomIndex = random.nextInt(taille);
        for (int i = 0; i < cardsNumbers.length; i++) {
            randomIndex -= cardsNumbers[i];
            if (randomIndex < 0) {
                return Card.values()[i];
            }
        }
        return null;
    }

    @Override
    public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("CardBox Contents:\n");
    for (Card card : Card.values()) {
        int num = cardsNumbers[card.ordinal()];
        if (num > 0) {
            sb.append(card.toString()).append(": ").append(num).append("\n");
        }
    }
    return sb.toString();
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

    public Card getFirst(){
        for(Card card : Card.values()) {
            if (cardsNumbers[card.ordinal()] != 0) {
                return card;
            }
        }return null;
    }

    public boolean isEmpty() {
        for(Card card : Card.values()) {
            if (cardsNumbers[card.ordinal()] > 0) {
                return false;
            }
        }
        return true;
    }

}
